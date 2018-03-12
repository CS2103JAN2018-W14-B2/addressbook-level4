package seedu.ptman.model.outlet;

import seedu.ptman.model.Password;

import java.util.Objects;

public class OutletInformation {

    private Name name;
    private Password masterPassword;
    private OperatingHours operatingHours;
    private Shifts shifts;
    //and there should be a default timetable

    public OutletInformation() {
        this.name = new Name("Outlet Name");
        this.masterPassword = new Password();
        this.operatingHours = new OperatingHours("09:00-22:00");
        this.shifts = new Shifts();
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

    public Shifts getShifts() {
        return shifts;
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
                && otherOutletInformation.getShifts().equals(this.getShifts());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, masterPassword, operatingHours, shifts);
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
