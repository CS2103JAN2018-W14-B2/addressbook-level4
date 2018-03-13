package seedu.ptman.model.outlet;

import java.util.Objects;

import seedu.ptman.model.Password;

/**
 * Represents an outlet in PTMan.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OutletInformation {

    private Name name;
    private Password masterPassword;
    private OperatingHours operatingHours;
    private ShiftList shiftList;
    //and there should be a default timetable

    public OutletInformation() {
        this.name = new Name("Outlet Name");
        this.masterPassword = new Password();
        this.operatingHours = new OperatingHours("09:00-22:00");
        this.shiftList = new ShiftList();
    }

    public Name getName() {
        return name;
    }

    public Password getMasterPassword() {
        return masterPassword;
    }

    public OperatingHours getOperatingHours() {
        return operatingHours;
    }

    public ShiftList getShiftList() {
        return shiftList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OutletInformation)) {
            return false;
        }

        OutletInformation otherOutletInformation = (OutletInformation) other;

        return otherOutletInformation.getName().equals(this.getName())
                && otherOutletInformation.getMasterPassword().equals(this.getMasterPassword())
                && otherOutletInformation.getOperatingHours().equals(this.getOperatingHours())
                && otherOutletInformation.getShiftList().equals(this.getShiftList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, masterPassword, operatingHours, shiftList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Operating Hour: ")
                .append(getOperatingHours());
        return builder.toString();
    }
}
