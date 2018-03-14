package seedu.ptman.commons.timetable;

import static org.junit.Assert.assertEquals;
import static seedu.ptman.testutil.TypicalTimes.TEN_MAR_ONE_PM;
import static seedu.ptman.testutil.TypicalTimes.TWELVE_MAR_ELEVEN_PM;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.ptman.commons.exceptions.InvalidTimeException;
import seedu.ptman.commons.exceptions.TimetableOutOfBoundsException;
import seedu.ptman.model.outletinformation.Shift;
import seedu.ptman.testutil.Assert;

public class TimetableTest {
    private static List<Shift> emptyList = new ArrayList<>();

    @Test
    public void constructor_startTimeAfterEndTime_throwsInvalidTimeException() {
        Assert.assertThrows(InvalidTimeException.class, () ->
                new Timetable(LocalDate.of(2018, 3, 10), LocalTime.of(8, 0), LocalTime.of(7, 0), emptyList));
    }

    @Test
    public void getCellFromTimeTest() throws InvalidTimeException, TimetableOutOfBoundsException {
        Timetable timetable =
                new Timetable(LocalDate.of(2018, 3, 10), LocalTime.of(8, 0), LocalTime.of(23, 0), emptyList);
        TimetableCell expectedCell = new TimetableCell(LocalTime.of(13, 0), 3);
        assertEquals(expectedCell, timetable.getCellFromTime(TEN_MAR_ONE_PM));
    }

    @Test
    public void getCellFromTime_timeOutOfBounds_throwsTimetableOutOfBoundsException()
            throws InvalidTimeException, TimetableOutOfBoundsException {
        Timetable timetable =
                new Timetable(LocalDate.of(2018, 3, 10), LocalTime.of(8, 0), LocalTime.of(23, 0), emptyList);
        Assert.assertThrows(TimetableOutOfBoundsException.class, () ->
                timetable.getCellFromTime(TWELVE_MAR_ELEVEN_PM));
    }

}
