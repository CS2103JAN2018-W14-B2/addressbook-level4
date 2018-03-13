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
    //shift needed
    //and there should be a default timetable

    /**
     * Constructs an {@code OutletInformation}.
     *
     * @param name a valid outlet name
     * @param masterPassword a valid master password
     * @param operatingHours a valid operating hours
     */
    public OutletInformation(Name name, Password masterPassword, OperatingHours operatingHours) {
        requireAllNonNull(name, masterPassword, operatingHours);
        this.name = name;
        this.masterPassword = masterPassword;
        this.operatingHours = operatingHours;
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
                && otherOutletInformation.getOperatingHours().equals(this.getOperatingHours());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, masterPassword, operatingHours);
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
