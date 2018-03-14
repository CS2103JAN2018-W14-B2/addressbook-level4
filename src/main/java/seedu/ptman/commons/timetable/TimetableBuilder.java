package seedu.ptman.commons.timetable;

import static seedu.ptman.commons.timetable.Timetable.getDateFromWeekOfYear;
import static seedu.ptman.commons.timetable.Timetable.getWeekFromDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import seedu.ptman.commons.exceptions.InvalidTimeException;
import seedu.ptman.commons.exceptions.TimetableOutOfBoundsException;
import seedu.ptman.model.outletinformation.Shift;

/**
 * Util class to for building a Timetable
 */
public class TimetableBuilder {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Shift> shiftList;
    private int week;

    /**
     * Sets the week of the Timetable
     * @param date
     * @return
     */
    public TimetableBuilder week(LocalDate date) {
        this.week = getWeekFromDate(date);
        this.date = date;
        return this;
    }

    /**
     * Sets the starting time of the Timetable
     * @param time
     * @return
     */
    public TimetableBuilder startTime(LocalTime time) {
        this.startTime = time;
        return this;
    }

    /**
     * Sets the ending time of the Timetable
     * @param time
     * @return
     */
    public TimetableBuilder endTime(LocalTime time) {
        this.endTime = time;
        return this;
    }

    /**
     * Sets the available shifts in the Timetable
     * @param shiftList
     * @return
     */
    public TimetableBuilder addShifts(List<Shift> shiftList) {
        this.shiftList = shiftList;
        return this;
    }

    public Timetable build() throws TimetableOutOfBoundsException, InvalidTimeException {
        return new Timetable(getDateFromWeekOfYear(week, 2018), startTime, endTime, shiftList);
    }
}
