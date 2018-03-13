package seedu.ptman.model.outlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ptman.testutil.Assert;

public class OperatingHoursTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OperatingHours(null));
    }

    @Test
    public void constructor_invalidOperatingHours_throwsIllegalArgumentException() {
        String invalidOperatingHours = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OperatingHours(invalidOperatingHours));
    }

    @Test
    public void isValidOperatingHours() {
        // null operatingHours
        Assert.assertThrows(NullPointerException.class, () -> OperatingHours.isValidOperatingHours(null));

        // blank operatingHours
        assertFalse(OperatingHours.isValidOperatingHours("")); // empty string
        assertFalse(OperatingHours.isValidOperatingHours(" ")); // spaces only

        // missing parts
        assertFalse(OperatingHours.isValidOperatingHours("-22:00")); // missing start time
        assertFalse(OperatingHours.isValidOperatingHours("09:00-")); // missing end time
        assertFalse(OperatingHours.isValidOperatingHours("-")); // missing both start and end time

        // invalid parts
        assertFalse(OperatingHours.isValidOperatingHours("25:00-22:00")); // invalid hour in start time
        assertFalse(OperatingHours.isValidOperatingHours("090:00-22:00")); // more than two numbers of hour
        assertFalse(OperatingHours.isValidOperatingHours("09:99-22:00")); // invalid minute in start time
        assertFalse(OperatingHours.isValidOperatingHours("09:000-22:00")); // more than two numbers of minute
        assertFalse(OperatingHours.isValidOperatingHours("09:00-25:00")); // invalid hour in end time
        assertFalse(OperatingHours.isValidOperatingHours("09:00-220:00")); // more than two numbers of hour
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22:99")); // invalid minute in end time
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22:000")); // more than two numbers of minute
        assertFalse(OperatingHours.isValidOperatingHours("09.00-22:00")); // invalid '.' symbol used in start
        assertFalse(OperatingHours.isValidOperatingHours("09/00-22:00")); // invalid '/' symbol used in start
        assertFalse(OperatingHours.isValidOperatingHours("09@00-22:00")); // invalid '@' symbol used in start
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22.00")); // invalid '.' symbol used in end
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22/00")); // invalid '/' symbol used in end
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22@00")); // invalid '@' symbol used in end
        assertFalse(OperatingHours.isValidOperatingHours(" 09:00-22:00")); // leading space
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22:00 ")); // trailing space
        assertFalse(OperatingHours.isValidOperatingHours("09:00--22:00")); // double '-' symbol
        assertFalse(OperatingHours.isValidOperatingHours("09::00-22:00")); // double ':' symbol in start time
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22::00")); // double ':' symbol in end time
        assertFalse(OperatingHours.isValidOperatingHours("09:00/22:00")); // invalid '/' symbol used to connect
        assertFalse(OperatingHours.isValidOperatingHours("09:00.22:00")); // invalid '.' symbol used to connect
        assertFalse(OperatingHours.isValidOperatingHours("09:00@22:00")); // invalid '@' symbol used to connect


        // valid operatingHours
        assertTrue(OperatingHours.isValidOperatingHours("09:00-22:00"));
        assertTrue(OperatingHours.isValidOperatingHours("10:00-21:00"));
        assertTrue(OperatingHours.isValidOperatingHours("08:00-18:00"));
    }
}
