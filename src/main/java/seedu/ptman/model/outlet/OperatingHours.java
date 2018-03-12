package seedu.ptman.model.outlet;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.util.AppUtil.checkArgument;

public class OperatingHours {

    public static final String MESSAGE_OPERATING_HOUR_CONSTRAINTS =
            "Operating hours must be in the format of START-END where START and END must be in "
                    + "the format of hh:mm and in terms of 24 hours. For example, 09:00-22:00";
    public static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String OPERATING_HOUR_VALIDATION_REGEX = TIME24HOURS_PATTERN + "-"
            + TIME24HOURS_PATTERN;
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param operatingHours A valid phone number.
     */
    public OperatingHours(String operatingHours) {
        requireNonNull(operatingHours);
        checkArgument(isValidOperatingHours(operatingHours), MESSAGE_OPERATING_HOUR_CONSTRAINTS);
        this.value = operatingHours;
    }

    /**
     * Returns true if a given string is a valid operating hours of an outlet.
     */
    public static boolean isValidOperatingHours(String test) {
        return test.matches(OPERATING_HOUR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OperatingHours // instanceof handles nulls
                && this.value.equals(((OperatingHours) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
