package seedu.ptman.logic.commands;

import seedu.ptman.commons.core.EventsCenter;
import seedu.ptman.commons.core.index.Index;
import seedu.ptman.commons.events.ui.JumpToListRequestEvent;


/**
 * Returns back to main timetable view (of current week) in PTMan
 */
public class MainCommand extends Command {

    public static final String COMMAND_WORD = "main";

    public static final String MESSAGE_SUCCESS = "Showing main timetable view.";

    @Override
    public CommandResult execute() {
        EventsCenter eventsCenter = EventsCenter.getInstance();
        eventsCenter.post(new JumpToListRequestEvent(Index.fromZeroBased(0), false));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
