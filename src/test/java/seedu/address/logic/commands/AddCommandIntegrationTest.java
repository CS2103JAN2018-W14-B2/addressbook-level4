package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.getTypicalPartTimeManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPartTimeManager(), new UserPrefs());
    }

    @Test
    public void execute_newEmployee_success() throws Exception {
        Employee validEmployee = new EmployeeBuilder().build();

        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs());
        expectedModel.addEmployee(validEmployee);

        assertCommandSuccess(prepareCommand(validEmployee, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validEmployee), expectedModel);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() {
        Employee employeeInList = model.getPartTimeManager().getEmployeeList().get(0);
        assertCommandFailure(prepareCommand(employeeInList, model), model, AddCommand.MESSAGE_DUPLICATE_EMPLOYEE);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code employee} into the {@code model}.
     */
    private AddCommand prepareCommand(Employee employee, Model model) {
        AddCommand command = new AddCommand(employee);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
