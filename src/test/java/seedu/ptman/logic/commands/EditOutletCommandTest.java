package seedu.ptman.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ptman.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ptman.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.ptman.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.ptman.testutil.TypicalEmployees.getTypicalPartTimeManager;

import org.junit.Test;

import seedu.ptman.logic.CommandHistory;
import seedu.ptman.logic.UndoRedoStack;
import seedu.ptman.model.Model;
import seedu.ptman.model.ModelManager;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.Password;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletName;
import seedu.ptman.model.outlet.exceptions.NoOutletInformationFieldChangeException;


public class EditOutletCommandTest {

    private Model model = new ModelManager(getTypicalPartTimeManager(), new UserPrefs());

    @Test
    public void execute_nonAdminMode_failure() {
        OutletName outletName = new OutletName("EditedOutlet");
        OperatingHours operatingHours = new OperatingHours("10:00-18:00");
        OutletContact outletContact = new OutletContact("912345678");
        EditOutletCommand command = prepareCommand(outletName, operatingHours, outletContact);
        assertCommandFailure(command, model, MESSAGE_ACCESS_DENIED);
    }

    @Test
    public void execute_adminModeAllFieldsValid_success() {
        model.setTrueAdminMode(new Password());
        OutletName outletName = new OutletName("EditedOutlet");
        OperatingHours operatingHours = new OperatingHours("10:00-18:00");
        OutletContact outletContact = new OutletContact("912345678");
        EditOutletCommand command = prepareCommand(outletName, operatingHours, outletContact);
        String expectedMessage = EditOutletCommand.MESSAGE_EDIT_OUTLET_SUCCESS;
        Model expectedModel = new ModelManager(new PartTimeManager(model.getPartTimeManager()), new UserPrefs());
        try {
            expectedModel.updateOutlet(outletName, operatingHours, outletContact);
        } catch (NoOutletInformationFieldChangeException e) {
            fail("This should not fail because all outlet information fields are specified.");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_adminModeAllFieldsValid_success() throws Exception {
        model.setTrueAdminMode(new Password());
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        OutletName outletName = new OutletName("EditedOutlet");
        OperatingHours operatingHours = new OperatingHours("10:00-18:00");
        OutletContact outletContact = new OutletContact("912345678");
        EditOutletCommand command = prepareCommand(outletName, operatingHours, outletContact);
        Model expectedModel = new ModelManager(new PartTimeManager(model.getPartTimeManager()), new UserPrefs());

        // edit -> outlet edited
        command.execute();
        undoRedoStack.push(command);

        // undo -> reverts parttimemanager back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first employee edited again
        expectedModel.updateOutlet(outletName, operatingHours, outletContact);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        OutletName outletName = new OutletName("EditedOutlet");
        OperatingHours operatingHours = new OperatingHours("10:00-18:00");
        OutletContact outletContact = new OutletContact("912345678");
        final EditOutletCommand standardCommand = prepareCommand(outletName, operatingHours, outletContact);

        // same values -> returns true
        OutletName sameName = new OutletName("EditedOutlet");
        OperatingHours sameOperatingHours = new OperatingHours("10:00-18:00");
        OutletContact sameOutletContact = new OutletContact("912345678");
        EditOutletCommand commandWithSameValues = prepareCommand(sameName, sameOperatingHours, sameOutletContact);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        OutletName differentName = new OutletName("different");
        OperatingHours differentOperatingHours = new OperatingHours("09:00-10:00");
        OutletContact differentOutletContact = new OutletContact("123456789");

        // different outlet name -> returns false
        assertFalse(standardCommand.equals(new EditOutletCommand(differentName, operatingHours, outletContact)));

        // different operating hours -> returns false
        assertFalse(standardCommand.equals(new EditOutletCommand(outletName, differentOperatingHours, outletContact)));

        // different outlet contact -> returns false
        assertFalse(standardCommand.equals(new EditOutletCommand(outletName, operatingHours, differentOutletContact)));
    }

    /**
     * Returns an {@code EditOutletCommand}
     */
    private EditOutletCommand prepareCommand(OutletName outletName, OperatingHours operatingHours,
                                             OutletContact outletContact) {
        EditOutletCommand editOutletCommand = new EditOutletCommand(outletName, operatingHours, outletContact);
        editOutletCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editOutletCommand;
    }
}
