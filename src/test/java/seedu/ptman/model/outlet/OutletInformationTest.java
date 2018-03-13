package seedu.ptman.model.outlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ptman.model.Password;
import seedu.ptman.testutil.Assert;

public class OutletInformationTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        Name name = new Name("outlet");
        Password masterPassword = new Password();
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(null,
                masterPassword, operatingHours));
    }

    @Test
    public void constructor_nullMasterPassword_throwsNullPointerException() {
        Name name = new Name("outlet");
        Password masterPassword = new Password();
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                null, operatingHours));
    }

    @Test
    public void constructor_nullOperatingHours_throwsNullPointerException() {
        Name name = new Name("outlet");
        Password masterPassword = new Password();
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                masterPassword, null));
    }
}
