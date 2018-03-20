package seedu.ptman.logic.commands;

import static seedu.ptman.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_CONTACT;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_NAME;

import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletName;
import seedu.ptman.model.outlet.exceptions.NoOutletInformationFieldChangeException;

/**
 * Edits the details of outlet in the ptman.
 */
public class EditOutletCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editoutlet";
    public static final String COMMAND_ALIAS = "eo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the outlet "
            + "verified by the master password. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_OUTLET_NAME + "OUTLETNAME] "
            + "[" + PREFIX_OPERATING_HOURS + "OPERATINGHOURS] "
            + "[" + PREFIX_OUTLET_CONTACT + "CONTACT] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OUTLET_NAME + "AwesomeOutlet "
            + PREFIX_OPERATING_HOURS + "09:00-22:00"
            + PREFIX_OUTLET_CONTACT + "91234567";

    public static final String MESSAGE_EDIT_OUTLET_SUCCESS = "Outlet Information Edited.";
    public static final String MESSAGE_EDIT_OUTLET_FAILURE = "At least one field must be specified";

    private final OutletName name;
    private final OperatingHours operatingHours;
    private final OutletContact outletContact;

    /**
     * Constructor of EditOutletCommand
     */
    public EditOutletCommand(OutletName name, OperatingHours operatingHours, OutletContact outletContact) {
        this.name = name;
        this.operatingHours = operatingHours;
        this.outletContact = outletContact;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (!model.isAdminMode()) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }
        try {
            model.updateOutlet(name, operatingHours, outletContact);
        } catch (NoOutletInformationFieldChangeException e) {
            throw new CommandException(MESSAGE_EDIT_OUTLET_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_OUTLET_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOutletCommand)) {
            return false;
        }

        // state check
        EditOutletCommand e = (EditOutletCommand) other;
        return outletContact.equals(e.outletContact)
                && name.equals(e.name)
                && operatingHours.equals(e.operatingHours);
    }
}
