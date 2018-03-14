package seedu.ptman.commons.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Stores the timetable cells in a row
 */
public class TimetableRow {
    private static final int PLACEHOLDER_AVAIL_SLOTS = 3;
    private static final int INDEX_SHIFT = 1;
    private ArrayList<TimetableCell> rowCellList;
    private LocalDate rowDate;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Creates a timetable row
     * @param startDateTime
     */
    public TimetableRow(LocalDate rowDate, LocalTime startTime, LocalTime endTime) {
        this.rowDate = rowDate;
        this.startTime = startTime;
        this.endTime = endTime;
        rowCellList = new ArrayList<>();
        initTimetableRow();
    }

    /**
     * Populates the rowCellList with TimetableCells
     */
    private void initTimetableRow() {
        int numCells = getNumberOfCells();
        for (int i = 0; i < numCells; i++) {
            rowCellList.add(new TimetableCell(startTime.plusHours(i), PLACEHOLDER_AVAIL_SLOTS));
        }
    }

    protected TimetableCell getCellFromDateTime(LocalDateTime cellTime) {
        return rowCellList.get(getCellIndexFromTime(cellTime));
    }

    private int getCellIndexFromTime(LocalDateTime cellTime) {
        return (int) ChronoUnit.HOURS.between(startTime, cellTime);
    }

    protected int getNumberOfCells() {
        return (int) ChronoUnit.HOURS.between(startTime, endTime) + INDEX_SHIFT;
    }

    void setCellAtTime(LocalDateTime cellDateTime, TimetableCell cell) {
        rowCellList.set(getCellIndexFromTime(cellDateTime), cell);
    }

}
