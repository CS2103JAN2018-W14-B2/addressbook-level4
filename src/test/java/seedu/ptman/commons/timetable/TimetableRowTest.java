package seedu.ptman.commons.timetable;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

public class TimetableRowTest {
    private static final LocalDateTime START_TIME =
            LocalDateTime.of(2018, Month.MARCH, 10, 8, 0);
    private static final LocalDateTime END_TIME =
            LocalDateTime.of(2018, Month.MARCH, 10, 23, 0);
    private final TimetableRow typicalTimetableRow =
            new TimetableRow(START_TIME.toLocalDate(), START_TIME.toLocalTime(), END_TIME.toLocalTime());

    @Test
    public void getNumberOfCells_eightToTwelve_sixteenCells() {
        assertEquals(16, typicalTimetableRow.getNumberOfCells());
    }
}
