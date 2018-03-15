package seedu.ptman.model.outlet;

import static seedu.ptman.commons.util.CollectionUtil.requireAllNonNull;

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
    private Timetable timetable;

    /**
     * Constructs an {@code OutletInformation}.
     *
     * @param name a valid outlet name
     * @param masterPassword a valid master password
     * @param operatingHours a valid operating hours
     */
    public OutletInformation(Name name, Password masterPassword, OperatingHours operatingHours, Timetable timetable) {
        requireAllNonNull(name, masterPassword, operatingHours, timetable);
        this.name = name;
        this.masterPassword = masterPassword;
        this.operatingHours = operatingHours;
        this.timetable = timetable;
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

    public Timetable getTimetable() {
        return timetable;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof OutletInformation
                && ((OutletInformation) other).getName().equals(this.getName())
                && ((OutletInformation) other).getMasterPassword().equals(this.getMasterPassword())
                && ((OutletInformation) other).getOperatingHours().equals(this.getOperatingHours())
                && ((OutletInformation) other).getTimetable().equals(this.getTimetable()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, masterPassword, operatingHours, timetable);
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
