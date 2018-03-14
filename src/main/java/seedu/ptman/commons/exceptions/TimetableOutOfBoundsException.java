package seedu.ptman.commons.exceptions;

/**
 * Signals an error caused by accessing a time slot
 * that is not within the Timetable
 */
public class TimetableOutOfBoundsException extends Exception {
    public TimetableOutOfBoundsException() {
        super("The specified time is not within the timetable.");
    }
}
