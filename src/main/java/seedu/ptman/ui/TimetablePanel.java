package seedu.ptman.ui;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.DetailedWeekView;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.scene.transform.Transform;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.commons.events.model.PartTimeManagerChangedEvent;
import seedu.ptman.commons.events.ui.EmployeePanelSelectionChangedEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageAndEmailRequestEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageRequestEvent;
import seedu.ptman.model.employee.Email;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.UniqueEmployeeList;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.outlet.Timetable;

//@@author hzxcaryn
/**
 * Displays the GUI Timetable.
 */
public class TimetablePanel extends UiPart<Region> {

    public static final String TIMETABLE_IMAGE_FILE_NAME_DEFAULT = "MyTimetable";
    public static final String TIMETABLE_IMAGE_FILE_FORMAT = "png";

    private static final int TIMETABLE_IMAGE_PIXEL_SCALE = 2;
    private static final String FXML = "TimetableView.fxml";
    private static final int MAX_SLOTS_LEFT_RUNNING_OUT = 3;

    private static final Style ENTRY_GREEN_STYLE = Style.STYLE1;
    private static final Style ENTRY_BLUE_STYLE = Style.STYLE2;
    private static final Style ENTRY_YELLOW_STYLE = Style.STYLE3;
    private static final Style ENTRY_RED_STYLE = Style.STYLE5;
    private static final Style ENTRY_BROWN_STYLE = Style.STYLE7;

    public static Calendar TIMETABLE_AVAIL;
    public static Calendar TIMETABLE_RUNNING_OUT;
    public static Calendar TIMETABLE_FULL;
    public static Calendar TIMETABLE_EMPLOYEE;
    public static Calendar TIMETABLE_OTHERS;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private CalendarView timetableView;

    private Timetable timetable;
    private ObservableList<Shift> shiftObservableList;
    private OutletInformation outletInformation;


    private Employee currentEmployee = null;

    protected TimetablePanel(ObservableList<Shift> shiftObservableList, OutletInformation outletInformation) {
        super(FXML);

        timetable = new Timetable(LocalDate.now());
        this.shiftObservableList = shiftObservableList;
        this.outletInformation = outletInformation;

        timetableView = new CalendarView();
        showRelevantViewsOnly();
        // disable clicks on timetable view
        timetableView.getWeekPage().setMouseTransparent(true);

        updateTimetableView();

        registerAsAnEventHandler(this);
    }

    public CalendarView getRoot() {
        return this.timetableView;
    }

    /**
     * Only show the parts of CalendarFX that we need.
     */
    private void showRelevantViewsOnly() {
        timetableView.showWeekPage();

        timetableView.getWeekPage().setShowNavigation(false);
        timetableView.getWeekPage().setShowDate(false);
        timetableView.weekFieldsProperty().setValue(WeekFields.of(Locale.FRANCE)); // Start week from Monday
        timetableView.setShowToday(true);
        timetableView.setShowPrintButton(true);
        timetableView.setShowAddCalendarButton(false);
        timetableView.setShowSearchField(false);
        timetableView.setShowToolBar(false);
        timetableView.setShowPageSwitcher(false);
        timetableView.setShowPageToolBarControls(false);
        timetableView.setShowSearchResultsTray(false);
        timetableView.setShowSourceTray(false);
        timetableView.setShowSourceTrayButton(false);
        timetableView.getWeekPage().getDetailedWeekView().setShowAllDayView(false);
    }

    /**
     * This ensures that the range of the times shown by the timetable view is constrained to the
     * operating hours of the outlet.
     * Also ensures that no scrolling is required to view the entire timetable.
     */
    private void setTimetableRange() {
        LocalTime startTime = outletInformation.getOperatingHours().getStartTime();
        LocalTime endTime = outletInformation.getOperatingHours().getEndTime();
        timetableView.setStartTime(startTime);
        timetableView.setEndTime(endTime);

        DetailedWeekView detailedWeekView = timetableView.getWeekPage().getDetailedWeekView();
        detailedWeekView.setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        detailedWeekView.setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT);
        detailedWeekView.setVisibleHours((int) ChronoUnit.HOURS.between(startTime, endTime));
        detailedWeekView.setShowScrollBar(false);
        detailedWeekView.setEnableCurrentTimeMarker(false);
    }

    private void setCurrentTime() {
        timetableView.setToday(LocalDate.now());
    }

    /**
     * Takes default outlet shifts and set timetable entries based on these shifts.
     */
    private void setShifts() {
        int index = 1;
        for (Shift shift: shiftObservableList) {
            LocalDate date = getDateOfShift(shift.getDay().toDayOfWeek());
            Interval timeInterval = new Interval(date, shift.getStartTime().getLocalTime(),
                    date, shift.getEndTime().getLocalTime());
            Entry<String> shiftEntry = new Entry<>("SHIFT " + index + "\nSlots left: " + shift.getSlotsLeft(),
                    timeInterval);
            setEntryType(shift, shiftEntry);
            index++;
        }
    }

    /**
     * Sets the entry type (aka the color) of the shift in the timetable
     * @param shift
     * @param shiftEntry
     */
    private void setEntryType(Shift shift, Entry<String> shiftEntry) {
        Calendar entryType;
        if (currentEmployee != null) {
            entryType = getEntryTypeEmployee(shift);
        } else {
            entryType = getEntryTypeMain(shift);
        }
        entryType.addEntry(shiftEntry);
    }

    /**
     * Checks if currentEmployee is in input shift
     * @param shift
     * @return true if currentEmployee is in input shift, false if not.
     */
    private boolean isCurrentEmployeeInShift(Shift shift) {
        UniqueEmployeeList employees = shift.getUniqueEmployeeList();
        for (Employee employee : employees) {
            if (employee.equals(currentEmployee)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts DayOfWeek into LocalDate for the timetable
     */
    private LocalDate getDateOfShift(DayOfWeek dayOfWeek) {
        LocalDate date = timetable.getMondayDate();
        date = date.plusDays(dayOfWeek.getValue() - 1);
        return date;
    }

    /**
     * @return the entryType (a Calendar object) for the shift in the main timetable view, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryTypeMain(Shift shift) {
        int ratio = shift.getSlotsLeft() / shift.getCapacity().getCapacity();
        if (ratio <= 0) {
            return TIMETABLE_FULL;
        } else if (ratio <= 0.5 || shift.getCapacity().getCapacity() < MAX_SLOTS_LEFT_RUNNING_OUT) {
            return TIMETABLE_RUNNING_OUT;
        } else {
            return TIMETABLE_AVAIL;
        }
    }

    /**
     * @return the entryType (a Calendar object) for the shift in the employee timetable view, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryTypeEmployee(Shift shift) {
        if (isCurrentEmployeeInShift(shift)) {
            return TIMETABLE_EMPLOYEE;
        } else {
            return TIMETABLE_OTHERS;
        }
    }

    /**
     * Replaces the timetable view with a new timetable, with shifts taken by the employee being highlighted
     * @param employee
     */
    private void loadEmployeeTimetable(Employee employee) {
        currentEmployee = employee;
        updateTimetableView();
    }

    /**
     * Replaces timetableView with a new timetable with updated shift and outlet information
     */
    private void updateTimetableView() {
        setCurrentTime();
        timetableView.getCalendarSources().clear();
        CalendarSource calendarSource = new CalendarSource("Shifts");
        addCalendars(calendarSource);

        setShifts();
        timetableView.getCalendarSources().add(calendarSource);

        setTimetableRange();
    }

    /**
     * Initialises all the Calendar objects
     */
    private void initialiseEntries() {
        TIMETABLE_AVAIL = new Calendar("Available");
        TIMETABLE_RUNNING_OUT = new Calendar("Running Out");
        TIMETABLE_FULL = new Calendar("Full");
        TIMETABLE_EMPLOYEE = new Calendar("Employee's shift");
        TIMETABLE_OTHERS = new Calendar("Other shifts");
    }

    /**
     * Sets the color styles of the entries
     */
    private void setEntryStyles() {
        TIMETABLE_AVAIL.setStyle(ENTRY_GREEN_STYLE);
        TIMETABLE_RUNNING_OUT.setStyle(ENTRY_YELLOW_STYLE);
        TIMETABLE_FULL.setStyle(ENTRY_RED_STYLE);
        TIMETABLE_EMPLOYEE.setStyle(ENTRY_BLUE_STYLE);
        TIMETABLE_OTHERS.setStyle(ENTRY_BROWN_STYLE);
    }

    /**
     * Adds all relevant Calendars (entryTypes) to its source
     */
    private void addCalendars(CalendarSource calendarSource) {
        initialiseEntries();
        setEntryStyles();
        calendarSource.getCalendars().addAll(TIMETABLE_AVAIL, TIMETABLE_RUNNING_OUT, TIMETABLE_FULL,
                TIMETABLE_EMPLOYEE, TIMETABLE_OTHERS);
    }

    /**
     * Takes a snapshot of the timetable view
     */
    private WritableImage takeSnapshot() {
        WritableImage timetableWritableImage = new WritableImage(
                (int) (TIMETABLE_IMAGE_PIXEL_SCALE * timetableView.getWidth()),
                (int) (TIMETABLE_IMAGE_PIXEL_SCALE * timetableView.getHeight()));
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(TIMETABLE_IMAGE_PIXEL_SCALE, TIMETABLE_IMAGE_PIXEL_SCALE));
        WritableImage snapshot = timetableView.snapshot(spa, timetableWritableImage);
        return snapshot;
    }

    /**
     * Exports timetable as image and save it locally
     */
    private void exportTimetableAsImage(String filename) {
        File imageFile = new File("." + File.separator + filename + "." + TIMETABLE_IMAGE_FILE_FORMAT);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(takeSnapshot(), null), TIMETABLE_IMAGE_FILE_FORMAT, imageFile);
        } catch (IOException e) {
            logger.warning("Error taking snapshot of timetable.");
        }
    }

    /**
     * Exports timetable as image and email it
     * @param email
     */
    private void exportTimetableAsImageAndEmail(String filename, Email email) {
        //TODO: This method should send image as email when emailing service is up.
        File imageFile = new File("." + File.separator + filename + "." + TIMETABLE_IMAGE_FILE_FORMAT);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(takeSnapshot(), null), TIMETABLE_IMAGE_FILE_FORMAT, imageFile);
        } catch (IOException e) {
            logger.warning("Error taking snapshot of timetable.");
        }
    }

    @Subscribe
    private void handleShiftChangedEvent(PartTimeManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Updating timetable view....");
        Platform.runLater(() -> updateTimetableView());
    }

    @Subscribe
    private void handleEmployeePanelSelectionChangedEvent(EmployeePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> loadEmployeeTimetable(event.getNewSelection().employee));
    }

    @Subscribe
    private void handleExportTimetableAsImageRequestEvent(ExportTimetableAsImageRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Exporting timetable as image....");
        Platform.runLater(() -> exportTimetableAsImage(event.filename));
    }

    @Subscribe
    private void handleExportTimetableAsImageAndEmailRequestEvent(ExportTimetableAsImageAndEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event)
                + ": Exporting timetable as image to send email....");
        Platform.runLater(() -> exportTimetableAsImageAndEmail(event.filename, event.email));
    }

}
