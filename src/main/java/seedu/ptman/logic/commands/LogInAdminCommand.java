package seedu.ptman.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ptman.commons.core.EventsCenter;
import seedu.ptman.commons.events.model.UserModeChangedEvent;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.logic.commands.exceptions.InvalidPasswordException;
import seedu.ptman.model.Password;

//@@author koo1993
/**
 * Login command for admin mode access.
 */
public class LogInAdminCommand extends Command {

    public static final String COMMAND_WORD = "login";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Log in to access admin command.\n"
            + "Example: " + COMMAND_WORD + " pw/AdminPassword";

    public static final String MESSAGE_LOGGEDIN = "You are already logged in";

    public static final String MESSAGE_SUCCESS = "You are now in admin mode. \n"
            + "please remember to log out after amending all the data.";


    private final Password toCheck;

    public LogInAdminCommand(Password password) {
        requireNonNull(password);
        toCheck = password;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (model.isAdminMode()) {
            return new CommandResult(MESSAGE_LOGGEDIN);
        }

        if (!model.setTrueAdminMode(toCheck)) {
            throw new InvalidPasswordException();
        }

        EventsCenter.getInstance().post(new UserModeChangedEvent(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogInAdminCommand // instanceof handles nulls
                && toCheck.equals(((LogInAdminCommand) other).toCheck));
    }
}
