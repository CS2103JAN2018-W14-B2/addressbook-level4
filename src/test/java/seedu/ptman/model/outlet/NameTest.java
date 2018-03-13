package seedu.ptman.model.outlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ptman.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new seedu.ptman.model.outlet.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new seedu.ptman.model.outlet.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> seedu.ptman.model.outlet.Name.isValidName(null));

        // invalid name
        assertFalse(seedu.ptman.model.employee.Name.isValidName("")); // empty string
        assertFalse(seedu.ptman.model.employee.Name.isValidName(" ")); // spaces only
        assertFalse(seedu.ptman.model.employee.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.ptman.model.employee.Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.ptman.model.employee.Name.isValidName("peter jack")); // alphabets only
        assertTrue(seedu.ptman.model.employee.Name.isValidName("12345")); // numbers only
        assertTrue(seedu.ptman.model.employee.Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(seedu.ptman.model.employee.Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
