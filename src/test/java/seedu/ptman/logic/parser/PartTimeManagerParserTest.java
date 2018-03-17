package seedu.ptman.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ptman.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ptman.logic.commands.CommandTestUtil.DEFAULT_DESC_ADMINPASSWORD;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ptman.logic.commands.AddCommand;
import seedu.ptman.logic.commands.ClearCommand;
import seedu.ptman.logic.commands.DeleteCommand;
import seedu.ptman.logic.commands.EditCommand;
import seedu.ptman.logic.commands.EditCommand.EditEmployeeDescriptor;
import seedu.ptman.logic.commands.ExitCommand;
import seedu.ptman.logic.commands.FindCommand;
import seedu.ptman.logic.commands.HelpCommand;
import seedu.ptman.logic.commands.HistoryCommand;
import seedu.ptman.logic.commands.ListCommand;
import seedu.ptman.logic.commands.RedoCommand;
import seedu.ptman.logic.commands.SelectCommand;
import seedu.ptman.logic.commands.UndoCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;
import seedu.ptman.model.Password;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.NameContainsKeywordsPredicate;
import seedu.ptman.testutil.EditEmployeeDescriptorBuilder;
import seedu.ptman.testutil.EmployeeBuilder;
import seedu.ptman.testutil.EmployeeUtil;

public class PartTimeManagerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final PartTimeManagerParser parser = new PartTimeManagerParser();

    private Password defaultPassword = new Password();

    @Test
    public void parseCommand_add() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EmployeeUtil.getAddCommand(employee));
        assertEquals(new AddCommand(employee, defaultPassword), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EmployeeUtil.getAliasedAddCommand(employee));
        assertEquals(new AddCommand(employee, defaultPassword), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD
                + DEFAULT_DESC_ADMINPASSWORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD
                + " 3" + DEFAULT_DESC_ADMINPASSWORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS
                + DEFAULT_DESC_ADMINPASSWORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3"
                + DEFAULT_DESC_ADMINPASSWORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_EMPLOYEE.getOneBased() + DEFAULT_DESC_ADMINPASSWORD);
        assertEquals(new DeleteCommand(INDEX_FIRST_EMPLOYEE, defaultPassword), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_EMPLOYEE.getOneBased() + DEFAULT_DESC_ADMINPASSWORD);
        assertEquals(new DeleteCommand(INDEX_FIRST_EMPLOYEE, defaultPassword), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(employee).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + EmployeeUtil.getEmployeeDetails(employee));
        assertEquals(new EditCommand(INDEX_FIRST_EMPLOYEE, descriptor, defaultPassword), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(employee).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + EmployeeUtil.getEmployeeDetails(employee));
        assertEquals(new EditCommand(INDEX_FIRST_EMPLOYEE, descriptor, defaultPassword), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_EMPLOYEE), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_EMPLOYEE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + DEFAULT_DESC_ADMINPASSWORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1" + DEFAULT_DESC_ADMINPASSWORD) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + DEFAULT_DESC_ADMINPASSWORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("r 1" + DEFAULT_DESC_ADMINPASSWORD) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + DEFAULT_DESC_ADMINPASSWORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3" + DEFAULT_DESC_ADMINPASSWORD) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + DEFAULT_DESC_ADMINPASSWORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("u 3" + DEFAULT_DESC_ADMINPASSWORD) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
