package seedu.ptman.model.outlet;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.UniqueEmployeeList;

/**
 * Represents a particular shift of a day in an outlet.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Shift {

    protected static final String MONDAY_FULL_STRING = "Monday";
    protected static final String TUESDAY_FULL_STRING = "Tuesday";
    protected static final String WEDNESDAY_FULL_STRING = "Wednesday";
    protected static final String THURSDAY_FULL_STRING = "Thursday";
    protected static final String FRIDAY_FULL_STRING = "Friday";
    protected static final String SATURDAY_FULL_STRING = "Saturday";
    protected static final String SUNDAY_FULL_STRING = "Sunday";
    protected static final String MONDAY_SHORT_FORM = "Mon";
    protected static final String TUESDAY_SHORT_FORM = "Tue";
    protected static final String WEDNESDAY_SHORT_FORM = "Wed";
    protected static final String THURSDAY_SHORT_FORM = "Thu";
    protected static final String FRIDAY_SHORT_FORM = "Fri";
    protected static final String SATURDAY_SHORT_FORM = "Sat";
    protected static final String SUNDAY_SHORT_FORM = "Sun";

    private static final String MESSAGE_DAY_CONSTRAINTS = "Day must be either in full or short form. "
            + "For example, Monday or Mon, and they are case-sensitive.";

    private String day;
    private UniqueEmployeeList employeesOfTheDay;

    /**
     * Constructor for a shift with employees on that day.
     */
    public Shift(String day, UniqueEmployeeList employeesOfTheDay) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_DAY_CONSTRAINTS);
        this.day = day;
        this.employeesOfTheDay = employeesOfTheDay;
    }

    /**
     * Constructor for a default shift with no employee.
     */
    public Shift(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_DAY_CONSTRAINTS);
        this.day = day;
        this.employeesOfTheDay = new UniqueEmployeeList();
    }

    public String getDay() {
        return day;
    }

    public UniqueEmployeeList getEmployeesOfTheDay() {
        return employeesOfTheDay;
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        return test.equals(MONDAY_FULL_STRING)
                || test.equals(TUESDAY_FULL_STRING)
                || test.equals(WEDNESDAY_FULL_STRING)
                || test.equals(THURSDAY_FULL_STRING)
                || test.equals(FRIDAY_FULL_STRING)
                || test.equals(SATURDAY_FULL_STRING)
                || test.equals(SUNDAY_FULL_STRING)
                || test.equals(MONDAY_SHORT_FORM)
                || test.equals(TUESDAY_SHORT_FORM)
                || test.equals(WEDNESDAY_SHORT_FORM)
                || test.equals(THURSDAY_SHORT_FORM)
                || test.equals(FRIDAY_SHORT_FORM)
                || test.equals(SATURDAY_SHORT_FORM)
                || test.equals(SUNDAY_SHORT_FORM);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append(" Employees: ");
        for(Employee e : getEmployeesOfTheDay()) {
            builder.append(e.getName());
        };
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Shift // instanceof handles nulls
                && this.day.equals(((Shift) other).day))
                && this.getEmployeesOfTheDay().equals(((Shift) other).getEmployeesOfTheDay()); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, employeesOfTheDay);
    }
}
