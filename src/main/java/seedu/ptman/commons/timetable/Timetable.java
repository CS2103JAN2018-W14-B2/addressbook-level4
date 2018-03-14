package seedu.ptman.commons.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import seedu.ptman.commons.exceptions.InvalidTimeException;
import seedu.ptman.commons.exceptions.TimetableOutOfBoundsException;
import seedu.ptman.model.outletinformation.Shift;

/**
 * Stores the data required to display the schedule
 */
public class Timetable {
    private static final int NUM_DAYS_IN_WEEK = 7;
    private static final int INDEX_SHIFT = 1;
    private ArrayList<TimetableRow> timetableRows;
    private int weekNum;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Timetable(LocalDate startDate, LocalTime startTime, LocalTime endTime, List<Shift> shiftList)
            throws InvalidTimeException, TimetableOutOfBoundsException {
        if (startTime.isAfter(endTime)) {
            throw new InvalidTimeException();
        }
        this.timetableRows = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekNum = getWeekFromDate(startDate);
        this.startDate = getDateFromWeekOfYear(weekNum, startDate.getYear());
        initTimetableRows();
        insertShifts(shiftList);
    }

    /**
     * Inserts shifts into the timetable
     * @param shiftList
     * @throws TimetableOutOfBoundsException
     */
    private void insertShifts(List<Shift> shiftList) throws TimetableOutOfBoundsException {
        for (Shift shift : shiftList) {
            TimetableCell cell = new TimetableCell(shift);
            setCellAtDateTime(shift.getStartDateTime(), cell);
        }
    }

    private void initTimetableRows() {
        for (int i = 0; i < NUM_DAYS_IN_WEEK; i++) {
            timetableRows.add(new TimetableRow(startDate.plusDays(i), startTime, endTime));
        }
    }

    public TimetableCell getCellFromTime(LocalDateTime cellDateTime) throws TimetableOutOfBoundsException {
        checkOutOfBounds(cellDateTime);
        return getRowFromDateTime(cellDateTime).getCellFromDateTime(cellDateTime);
    }

    /**
     * Checks if the LocalDateTime provided is out of bounds of the timetable
     * @param cellDateTime
     * @throws TimetableOutOfBoundsException
     */
    private void checkOutOfBounds(LocalDateTime cellDateTime) throws TimetableOutOfBoundsException {
        if (cellDateTime.isAfter(LocalDateTime.of(startDate.plusDays(7), endTime))
                || cellDateTime.isBefore(LocalDateTime.of(startDate, startTime))) {
            throw new TimetableOutOfBoundsException();
        }
    }

    private void setCellAtDateTime(LocalDateTime cellDateTime, TimetableCell cell)
            throws TimetableOutOfBoundsException {
        checkOutOfBounds(cellDateTime);
        getRowFromDateTime(cellDateTime).setCellAtTime(cellDateTime, cell);
    }

    private TimetableRow getRowFromDateTime(LocalDateTime cellTime) {
        return timetableRows.get(cellTime.getDayOfWeek().getValue() - INDEX_SHIFT);
    }

    public static int getWeekFromDate(LocalDate date) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return date.get(woy);
    }

    public static LocalDate getDateFromWeekOfYear(int week, int year) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);
    }
}
