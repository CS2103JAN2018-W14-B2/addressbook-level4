package seedu.ptman.logic.parser;

import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OPERATING_HOURS_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OUTLET_CONTACT_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OUTLET_NAME_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OPERATING_HOURS_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OUTLET_CONTACT_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OUTLET_NAME_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OPERATING_HOURS;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OUTLET_CONTACT;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OUTLET_NAME;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_CONTACT;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_NAME;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.ptman.logic.commands.EditOutletCommand;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletName;

public class EditOutletCommandParserTest {

    private EditOutletCommandParser parser = new EditOutletCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        String commandInvalidName = EditOutletCommand.COMMAND_WORD + INVALID_OUTLET_NAME_DESC + " "
                + PREFIX_OPERATING_HOURS + VALID_OPERATING_HOURS + " "
                + PREFIX_OUTLET_CONTACT + VALID_OUTLET_CONTACT;
        assertParseFailure(parser, commandInvalidName, OutletName.MESSAGE_NAME_CONSTRAINTS);

        String commandInvalidOperatingHours = EditOutletCommand.COMMAND_WORD + " "
                + PREFIX_OUTLET_NAME + VALID_OUTLET_NAME + INVALID_OPERATING_HOURS_DESC + " "
                + PREFIX_OUTLET_CONTACT + VALID_OUTLET_CONTACT;
        assertParseFailure(parser, commandInvalidOperatingHours,
                OperatingHours.MESSAGE_OPERATING_HOUR_CONSTRAINTS);

        String commandInvalidOutletContact = EditOutletCommand.COMMAND_WORD + " "
                + PREFIX_OUTLET_NAME + VALID_OUTLET_NAME + " "
                + PREFIX_OPERATING_HOURS + VALID_OPERATING_HOURS
                + INVALID_OUTLET_CONTACT_DESC;
        assertParseFailure(parser, commandInvalidOutletContact, OutletContact.MESSAGE_OUTLET_CONTACT_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsValid_success() {
        String userInput = EditOutletCommand.COMMAND_WORD
                + OUTLET_NAME_DESC + OPERATING_HOURS_DESC + OUTLET_CONTACT_DESC;
        OutletName outletName = new OutletName(VALID_OUTLET_NAME);
        OperatingHours operatingHours = new OperatingHours(VALID_OPERATING_HOURS);
        OutletContact outletContact = new OutletContact(VALID_OUTLET_CONTACT);
        EditOutletCommand expectedCommand = new EditOutletCommand(outletName, operatingHours, outletContact);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
