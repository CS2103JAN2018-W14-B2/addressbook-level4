package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.testutil.TypicalEmployees.getTypicalPartTimeManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalPartTimeManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        ModelManager expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);
        showNoEmployee(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Index outOfBoundIndex = INDEX_SECOND_EMPLOYEE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPartTimeManager().getEmployeeList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs());

        // delete -> first employee deleted
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts parttimemanager back to previous state and filtered employee list to show all employees
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first employee deleted again
        expectedModel.deleteEmployee(employeeToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Employee} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted employee in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the employee object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEmployeeDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs());

        showEmployeeAtIndex(model, INDEX_SECOND_EMPLOYEE);
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        // delete -> deletes second employee in unfiltered employee list / first employee in filtered employee list
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts parttimemanager back to previous state and filtered employee list to show all employees
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteEmployee(employeeToDelete);
        assertNotEquals(employeeToDelete, model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased()));
        // redo -> deletes same second employee in unfiltered employee list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        DeleteCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_EMPLOYEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEmployee(Model model) {
        model.updateFilteredEmployeeList(p -> false);

        assertTrue(model.getFilteredEmployeeList().isEmpty());
    }
}
