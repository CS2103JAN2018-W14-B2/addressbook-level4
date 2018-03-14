package seedu.ptman.commons.exceptions;

/**
 * Signals that the Timetable's start time is after the end time
 */
public class InvalidTimeException extends Exception {
    public InvalidTimeException() {
        super("Start time is after the end time");
    }
}
