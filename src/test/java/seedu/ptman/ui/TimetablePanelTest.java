package seedu.ptman.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.ptman.testutil.EventsUtil.postNow;
import static seedu.ptman.testutil.TypicalEmployees.ALICE;
import static seedu.ptman.testutil.TypicalShifts.getTypicalPartTimeManagerWithShifts;
import static seedu.ptman.ui.testutil.GuiTestAssert.assertEntryDisplaysShift;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_IMAGE_FILE_FORMAT;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_AVAIL;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_RUNNING_OUT;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_FULL;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_EMPLOYEE;
import static seedu.ptman.ui.TimetablePanel.TIMETABLE_OTHERS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.ptman.commons.events.ui.EmployeePanelSelectionChangedEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageAndEmailRequestEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageRequestEvent;
import seedu.ptman.model.employee.Email;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.outlet.Shift;


//@@author hzxcaryn
public class TimetablePanelTest extends GuiUnitTest {

    private static final ObservableList<Shift> TYPICAL_SHIFTS =
            getTypicalPartTimeManagerWithShifts().getShiftList();
    private static final OutletInformation TYPICAL_OUTLET =
            getTypicalPartTimeManagerWithShifts().getOutletInformation();

    private static final String TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST = "Testing1";
    private static final String TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST = "Testing2";
    private static final Email TIMETABLE_IMAGE_EMAIL_TEST = new Email("example@gmail.com");

    private EmployeePanelSelectionChangedEvent employeePanelSelectionChangedEventStub;
    private ExportTimetableAsImageRequestEvent exportTimetableAsImageRequestEventStub;
    private ExportTimetableAsImageAndEmailRequestEvent exportTimetableAsImageAndEmailRequestEventStub;

    private TimetablePanel timetablePanel;

    private Path testFilePathFirst;
    private Path testFilePathSecond;

    @Before
    public void setUp() {
        employeePanelSelectionChangedEventStub = new EmployeePanelSelectionChangedEvent(new EmployeeCard(ALICE, 0));
        exportTimetableAsImageRequestEventStub =
                new ExportTimetableAsImageRequestEvent(TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST);
        exportTimetableAsImageAndEmailRequestEventStub = new ExportTimetableAsImageAndEmailRequestEvent(
                TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST, TIMETABLE_IMAGE_EMAIL_TEST);

        testFilePathFirst = Paths.get("." + File.separator + TIMETABLE_IMAGE_FILE_NAME_FIRST_TEST + "."
                + TIMETABLE_IMAGE_FILE_FORMAT);
        testFilePathSecond = Paths.get("." + File.separator + TIMETABLE_IMAGE_FILE_NAME_SECOND_TEST + "."
                + TIMETABLE_IMAGE_FILE_FORMAT);

        timetablePanel = new TimetablePanel(TYPICAL_SHIFTS, TYPICAL_OUTLET);

        uiPartRule.setUiPart(timetablePanel);
    }

    @Test
    public void display() {
        // Default timetable view: Displays week view
        assertNotNull(timetablePanel.getRoot());
        assertEquals(timetablePanel.getRoot().getSelectedPage(), timetablePanel.getRoot().getWeekPage());

        // Default timetable view: Displays all shifts
        List<Entry> defaultEntries = getTimetableEntries();
        for (int i = 0; i < TYPICAL_SHIFTS.size(); i++) {
            Shift expectedShift = TYPICAL_SHIFTS.get(i);
            Entry actualEntry = defaultEntries.get(i);
            assertEntryDisplaysShift(expectedShift, actualEntry, i+1);
        }

        // Snapshot taken when export command called
        postNow(exportTimetableAsImageRequestEventStub);
        assertTrue(Files.exists(testFilePathFirst) && Files.isRegularFile(testFilePathFirst));

        // Snapshot taken when export and email command called
        postNow(exportTimetableAsImageAndEmailRequestEventStub);
        assertTrue(Files.exists(testFilePathSecond) && Files.isRegularFile(testFilePathSecond));

        // Associated shifts of employee highlighted
        postNow(employeePanelSelectionChangedEventStub);
        List<Entry> entries = getTimetableEntries();
        for (int i = 0; i < TYPICAL_SHIFTS.size(); i++) {
            Shift expectedShift = TYPICAL_SHIFTS.get(i);
            Entry actualEntry = entries.get(i);
            assertEntryDisplaysShift(expectedShift, actualEntry, i);
        }
    }

    @After
    public void tearDown() {
        try {
            //Files.deleteIfExists(testFilePathFirst);
            Files.deleteIfExists(testFilePathSecond);
        } catch (IOException e) {
            throw new AssertionError("Error deleting test files.");
        }
    }

    private List<Entry<?>> getEntriesForEntryType(Calendar entryType) {
        Map<LocalDate, List<Entry<?>>> entryMap = entryType.findEntries(
                LocalDate.now(), LocalDate.now().plusDays(10), ZoneId.systemDefault());
        List<Entry<?>> entryList = entryMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return entryList;
    }

    private List<Entry> getTimetableEntries() {
        List<Entry<?>> availEntries = getEntriesForEntryType(TIMETABLE_AVAIL);
        List<Entry<?>> runningOutEntries = getEntriesForEntryType(TIMETABLE_RUNNING_OUT);
        List<Entry<?>> fullEntries = getEntriesForEntryType(TIMETABLE_FULL);
        List<Entry<?>> employeeEntries = getEntriesForEntryType(TIMETABLE_EMPLOYEE);
        List<Entry<?>> othersEntries = getEntriesForEntryType(TIMETABLE_OTHERS);

        List<Entry> entries = Stream.of(availEntries, runningOutEntries, fullEntries, employeeEntries, othersEntries)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Collections.sort(entries, (Entry o1, Entry o2) -> o1.getStartDate().compareTo(o2.getStartDate()));
        return entries;
    }

}
