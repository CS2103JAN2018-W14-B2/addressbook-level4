package seedu.ptman.commons.events.ui;

import seedu.ptman.commons.events.BaseEvent;
import seedu.ptman.model.employee.Email;

/**
 * An event requesting to export the timetable view as an image and email it to the given email
 */
public class ExportTimetableAsImageAndEmailRequestEvent extends BaseEvent {

    private final Email email;

    public ExportTimetableAsImageAndEmailRequestEvent(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
