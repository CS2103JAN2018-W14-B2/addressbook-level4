package seedu.ptman.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;
import java.util.Objects;

import seedu.ptman.commons.core.Messages;
import seedu.ptman.commons.core.index.Index;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.model.Password;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.EmployeeNotFoundException;
import seedu.ptman.model.employee.exceptions.InvalidPasswordException;

/**
 * Deletes a employee identified using it's last displayed index from PTMan.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the employee identified by the index number used in the last employee listing.\n"
            + "Parameters: INDEX (must be a positive integer) pw/AdminPassword \n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PASSWORD + "AdminPassword";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Deleted Employee: %1$s";

    private final Index targetIndex;

    private Employee employeeToDelete;
    private Password toCheck;

    public DeleteCommand(Index targetIndex, Password password) {
        this.targetIndex = targetIndex;
        isAdminCommand = true;
        toCheck = password;
    }


    @Override
    public CommandResult executeUndoableCommand() throws InvalidPasswordException{
        requireNonNull(employeeToDelete);
        requireNonNull(toCheck);

        if (!model.isAdminPassword(toCheck)) {
            throw new InvalidPasswordException();
        }

        try {
            model.deleteEmployee(employeeToDelete);
        } catch (EmployeeNotFoundException pnfe) {
            throw new AssertionError("The target employee cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        employeeToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.employeeToDelete, ((DeleteCommand) other).employeeToDelete));
    }
}
