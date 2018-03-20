package seedu.ptman.model.outlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import org.junit.Test;

import seedu.ptman.model.Password;
import seedu.ptman.testutil.Assert;

public class OutletInformationTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(null, operatingHours,
                outletContact, timetable));
    }

    @Test
    public void constructor_nullOperatingHours_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletContact outletContact = new OutletContact("91234567");
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                null, outletContact, timetable));
    }

    @Test
    public void constructor_nullOutletContact_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name,
                operatingHours, null, timetable));
    }

    @Test
    public void constructor_nullTimetable_throwsNullPointerException() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Assert.assertThrows(NullPointerException.class, () -> new OutletInformation(name, operatingHours,
                outletContact, null));
    }

    @Test
    public void getName_validInput_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, timetable);
        assertEquals(outlet.getName(), name);
    }

    @Test
    public void getOperatingHours_validInput_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, timetable);
        assertEquals(outlet.getOperatingHours(), operatingHours);
    }

    @Test
    public void equals_sameOutletInformation_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, timetable);
        OutletInformation other = new OutletInformation(name, operatingHours, outletContact, timetable);
        assertTrue(outlet.equals(other));
    }

    @Test
    public void hashCode_sameObject_returnsTrue() {
        Password masterPassword = new Password();
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, timetable);
        assertEquals(outlet.hashCode(), Objects.hash(name, masterPassword, operatingHours, timetable));
    }

    @Test
    public void toString_validInput_returnsTrue() {
        OutletName name = new OutletName("outlet");
        OperatingHours operatingHours = new OperatingHours("09:00-22:00");
        OutletContact outletContact = new OutletContact("91234567");
        Timetable timetable = new Timetable(LocalDate.of(2018, Month.MARCH, 10));
        OutletInformation outlet = new OutletInformation(name, operatingHours, outletContact, timetable);
        String expected = "Outlet Name: outlet Operating Hour: 09:00-22:00 Contact: 91234567";
        assertEquals(outlet.toString(), expected);
    }
}
