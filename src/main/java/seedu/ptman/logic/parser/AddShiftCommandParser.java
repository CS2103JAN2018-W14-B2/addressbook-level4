package seedu.ptman.logic.parser;

import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_TIME_START;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.logic.commands.AddShiftCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.outlet.exceptions.IllegalTimeException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddShiftCommandParser implements Parser<AddShiftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddShiftCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_TIME_START, PREFIX_TIME_END, PREFIX_CAPACITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_TIME_START, PREFIX_TIME_END, PREFIX_CAPACITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddShiftCommand.MESSAGE_USAGE));
        }

        try {
            DayOfWeek dayOfWeek = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_START)).get();
            LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_END)).get();
            int capacity = ParserUtil.parseCapacity(argMultimap.getValue(PREFIX_CAPACITY)).get();

            Shift shift = new Shift(dayOfWeek, startTime, endTime, capacity);

            return new AddShiftCommand(shift);
        } catch (IllegalValueException | IllegalTimeException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
