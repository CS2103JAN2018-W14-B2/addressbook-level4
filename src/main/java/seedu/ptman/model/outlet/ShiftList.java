package seedu.ptman.model.outlet;

import static seedu.ptman.commons.util.AppUtil.checkArgument;
import static seedu.ptman.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents shifts in an outlet from Monday to Sunday.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ShiftList {

    private static final String DEFAULT_MONDAY_VALUE = "Monday";
    private static final String DEFAULT_TUESDAY_VALUE = "Tuesday";
    private static final String DEFAULT_WEDNESDAY_VALUE = "Wednesday";
    private static final String DEFAULT_THURSDAY_VALUE = "Thursday";
    private static final String DEFAULT_FRIDAY_VALUE = "Friday";
    private static final String DEFAULT_SATURDAY_VALUE = "Saturday";
    private static final String DEFAULT_SUNDAY_VALUE = "Sunday";
    private static final String MESSAGE_SHIFT_LIST_CONSTRAINTS = "Shifts must be put in order: "
            + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.";
    private static final String SP = " ";

    private Shift mondayShift;
    private Shift tuesdayShift;
    private Shift wednesdayShift;
    private Shift thursdayShift;
    private Shift fridayShift;
    private Shift saturdayShift;
    private Shift sundayShift;

    /**
     * Constructor for a shiftList contains shifts from Monday to Sunday.
     */
    public ShiftList(Shift mondayShift, Shift tuesdayShift, Shift wednesdayShift, Shift thursdayShift,
                     Shift fridayShift, Shift saturdayShift, Shift sundayShift) {
        requireAllNonNull(mondayShift, tuesdayShift, wednesdayShift, thursdayShift,
                fridayShift, saturdayShift, sundayShift);
        checkArgument(isValidShiftList(mondayShift, tuesdayShift, wednesdayShift, thursdayShift,
                fridayShift, saturdayShift, sundayShift), MESSAGE_SHIFT_LIST_CONSTRAINTS);
        this.mondayShift = mondayShift;
        this.tuesdayShift = tuesdayShift;
        this.wednesdayShift = wednesdayShift;
        this.thursdayShift = thursdayShift;
        this.fridayShift = fridayShift;
        this.saturdayShift = saturdayShift;
        this.sundayShift = sundayShift;
    }

    /**
     * Constructor for a default shiftList with no employee.
     */
    public ShiftList() {
        requireAllNonNull(mondayShift, tuesdayShift, wednesdayShift, thursdayShift,
                fridayShift, saturdayShift, sundayShift);
        checkArgument(isValidShiftList(mondayShift, tuesdayShift, wednesdayShift, thursdayShift,
                fridayShift, saturdayShift, sundayShift), MESSAGE_SHIFT_LIST_CONSTRAINTS);
        this.mondayShift = new Shift(DEFAULT_MONDAY_VALUE);
        this.tuesdayShift = new Shift(DEFAULT_TUESDAY_VALUE);
        this.wednesdayShift = new Shift(DEFAULT_WEDNESDAY_VALUE);
        this.thursdayShift = new Shift(DEFAULT_THURSDAY_VALUE);
        this.fridayShift = new Shift(DEFAULT_FRIDAY_VALUE);
        this.saturdayShift = new Shift(DEFAULT_SATURDAY_VALUE);
        this.sundayShift = new Shift(DEFAULT_SUNDAY_VALUE);
    }

    public Shift getMondayShift() {
        return mondayShift;
    }

    public Shift getTuesdayShift() {
        return tuesdayShift;
    }

    public Shift getWednesdayShift() {
        return wednesdayShift;
    }

    public Shift getThursdayShift() {
        return thursdayShift;
    }

    public Shift getFridayShift() {
        return fridayShift;
    }

    public Shift getSaturdayShift() {
        return saturdayShift;
    }

    public Shift getSundayShift() {
        return sundayShift;
    }

    /**
     * Returns true if a given string is a valid shift list.
     */
    public static boolean isValidShiftList(Shift mondayShift, Shift tuesdayShift, Shift wednesdayShift,
                                           Shift thursdayShift, Shift fridayShift, Shift saturdayShift,
                                           Shift sundayShift) {
        boolean isMondayShift = mondayShift.getDay().equals(Shift.MONDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.MONDAY_SHORT_FORM);
        boolean isTuesdayShift = tuesdayShift.getDay().equals(Shift.TUESDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.TUESDAY_SHORT_FORM);
        boolean isWednesdayShift = wednesdayShift.getDay().equals(Shift.WEDNESDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.WEDNESDAY_SHORT_FORM);
        boolean isThursdayShift = thursdayShift.getDay().equals(Shift.THURSDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.THURSDAY_SHORT_FORM);
        boolean isFridayShift = fridayShift.getDay().equals(Shift.FRIDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.FRIDAY_SHORT_FORM);
        boolean isSaturdayShift = saturdayShift.getDay().equals(Shift.SATURDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.SATURDAY_SHORT_FORM);
        boolean isSundayShift = sundayShift.getDay().equals(Shift.SUNDAY_FULL_STRING)
                || mondayShift.getDay().equals(Shift.SUNDAY_SHORT_FORM);
        return isMondayShift && isTuesdayShift && isWednesdayShift && isThursdayShift && isFridayShift
                && isSaturdayShift && isSundayShift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mondayShift, tuesdayShift, wednesdayShift, thursdayShift, fridayShift,
                saturdayShift, sundayShift);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShiftList // instanceof handles nulls
                && this.mondayShift.equals(((ShiftList) other).mondayShift)
                && this.tuesdayShift.equals(((ShiftList) other).tuesdayShift)
                && this.wednesdayShift.equals(((ShiftList) other).wednesdayShift)
                && this.thursdayShift.equals(((ShiftList) other).thursdayShift)
                && this.fridayShift.equals(((ShiftList) other).fridayShift)
                && this.saturdayShift.equals(((ShiftList) other).saturdayShift)
                && this.sundayShift.equals(((ShiftList) other).sundayShift)); // state check
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Shifts: ")
                .append(getMondayShift())
                .append(SP)
                .append(getTuesdayShift())
                .append(SP)
                .append(getWednesdayShift())
                .append(SP)
                .append(getThursdayShift())
                .append(SP)
                .append(getFridayShift())
                .append(SP)
                .append(getSaturdayShift())
                .append(SP)
                .append(getSundayShift());
        return builder.toString();
    }
}
