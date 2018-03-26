package seedu.ptman.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ptman.logic.commands.ExportCommand.MESSAGE_EMAIL_SUCCESS;
import static seedu.ptman.logic.commands.ExportCommand.MESSAGE_SAVE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.ptman.commons.events.ui.ExportTimetableAsImageAndEmailRequestEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageRequestEvent;
import seedu.ptman.model.employee.Email;
import seedu.ptman.ui.testutil.EventsCollectorRule;

//@@author hzxcaryn
public class ExportCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exportAndSave_success() {
        CommandResult result = new ExportCommand().execute();
        assertEquals(MESSAGE_SAVE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExportTimetableAsImageRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_exportAndEmail_success() {
        CommandResult result = new ExportCommand(new Email("email@example.com")).execute();
        assertEquals(MESSAGE_EMAIL_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent()
                instanceof ExportTimetableAsImageAndEmailRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
