package seedu.ptman.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.commons.events.model.PartTimeManagerChangedEvent;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.outlet.Timetable;


/**
 * Displays the GUI Timetable.
 */
public class TimetablePanel extends UiPart<Region> {

    private static final String FXML = "TimetableView.fxml";
    private static final int MAX_SLOTS_LEFT_RUNNING_OUT = 3;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private CalendarView timetableView;

    private Timetable timetable;
    private ObservableList<Shift> shiftObservableList;

    private Calendar timetableAvail;
    private Calendar timetableRunningOut;
    private Calendar timetableFull;

    public TimetablePanel(ObservableList<Shift> shiftObservableList) {
        super(FXML);

        timetable = new Timetable(LocalDate.now());

        this.shiftObservableList = shiftObservableList;

        timetableView = new CalendarView();

        showRelevantViewsOnly();

        updateTimetableView();

        registerAsAnEventHandler(this);
    }

    public CalendarView getRoot() {
        return this.timetableView;
    }

    /**
     * Only show the parts of CalendarFX that we need
     */
    private void showRelevantViewsOnly() {
        timetableView.showWeekPage();

        timetableView.weekFieldsProperty().setValue(WeekFields.of(Locale.FRANCE)); // Start week from Monday
        timetableView.disableProperty();
        timetableView.setShowToday(false);
        timetableView.setShowPrintButton(true);
        timetableView.setShowAddCalendarButton(false);
        timetableView.setShowSearchField(false);
        timetableView.setShowToolBar(false);
        timetableView.setShowPageSwitcher(false);
        timetableView.setShowPageToolBarControls(false);
        timetableView.setShowSearchResultsTray(false);
        timetableView.setShowSourceTray(false);
        timetableView.setShowSourceTrayButton(false);
    }

    private void setTime() {
        timetableView.setToday(LocalDate.now());
        timetableView.setTime(LocalTime.now());
    }

    /**
     * Takes default outlet shifts and set timetable entries based on these shifts
     */
    private void setShifts() {
        for (Shift shift: shiftObservableList) {
            // TODO: Add Time Slot index to each Shift
            LocalDate date = getDateOfShift(shift.getDay().toDayOfWeek());
            Interval timeInterval = new Interval(date, shift.getStartTime().getLocalTime(),
                    date, shift.getEndTime().getLocalTime());
            Entry<String> shiftEntry = new Entry<>("Slots left: " + shift.getSlotsLeft(), timeInterval);
            Calendar entryType = getEntryType(shift);
            entryType.addEntry(shiftEntry);
        }
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
     * @return the entryType (a Calendar object) for the shift, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryType(Shift shift) {
        int ratio = shift.getSlotsLeft() / shift.getCapacity().getCapacity();
        if (ratio <= 0) {
            return timetableFull;
        } else if (ratio <= 0.5 || shift.getCapacity().getCapacity() < MAX_SLOTS_LEFT_RUNNING_OUT) {
            return timetableRunningOut;
        } else {
            return timetableAvail;
        }
    }

    /**
     * Replaces timetableView with a new timetable with updated shift information
     */
    private void updateTimetableView() {
        setTime();
        timetableView.getCalendarSources().clear();
        CalendarSource calendarSource = new CalendarSource("Shifts");
        addCalendars(calendarSource);

        setShifts();
        timetableView.getCalendarSources().add(calendarSource);
    }

    /**
     * Adds all relevant Calendars (entryTypes) to its source
     */
    private void addCalendars(CalendarSource calendarSource) {
        // TODO: Improve code quality of this method
        timetableAvail = new Calendar("Available");
        timetableRunningOut = new Calendar("Running Out");
        timetableFull = new Calendar("Full");

        timetableAvail.setStyle(Calendar.Style.STYLE1); // Green
        timetableRunningOut.setStyle(Calendar.Style.STYLE3); // Yellow
        timetableFull.setStyle(Calendar.Style.STYLE5); // Red

        calendarSource.getCalendars().addAll(timetableAvail, timetableRunningOut, timetableFull);
    }

    @Subscribe
    private void handleShiftChangedEvent(PartTimeManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Updating timetable view....");
        Platform.runLater(() -> updateTimetableView());
    }

}
