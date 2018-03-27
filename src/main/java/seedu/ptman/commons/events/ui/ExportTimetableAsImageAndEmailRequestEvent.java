package seedu.ptman.commons.events.ui;

import seedu.ptman.commons.events.BaseEvent;
import seedu.ptman.model.employee.Email;

/**
 * An event requesting to export the timetable view as an image and email it to the given email
 */
public class ExportTimetableAsImageAndEmailRequestEvent extends BaseEvent {

    public final Email email;

    public ExportTimetableAsImageAndEmailRequestEvent(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
