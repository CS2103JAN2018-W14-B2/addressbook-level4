package seedu.ptman.model.outlet;

import static seedu.ptman.commons.util.AppUtil.checkArgument;
import static seedu.ptman.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.UniqueEmployeeList;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;
import seedu.ptman.model.employee.exceptions.EmployeeNotFoundException;

/**
 * Represents a shift that employees can work in.
 */
public class Shift {
    public static final String MESSAGE_SHIFT_CONSTRAINTS = "Start time should be after the end time.";
    private Time startTime;
    private Time endTime;
    private Day day;
    private UniqueEmployeeList uniqueEmployeeList;
    private Capacity capacity;

    public Shift(Day day, Time startTime, Time endTime, Capacity capacity) {
        requireAllNonNull(startTime, endTime, capacity);
        checkArgument(endTime.isAfter(startTime), MESSAGE_SHIFT_CONSTRAINTS);
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.day = day;
        this.uniqueEmployeeList = new UniqueEmployeeList();
    }

    protected boolean contains(Employee employee) {
        return uniqueEmployeeList.contains(employee);
    }

    /**
     * Adds an employee that is working in this shift.
     * @param employee
     * @throws DuplicateEmployeeException
     */
    protected void addEmployee(Employee employee) throws DuplicateEmployeeException {
        uniqueEmployeeList.add(employee);
    }

    /**
     * Removes an employee who is no longer working in this shift.
     * @param employee
     * @throws EmployeeNotFoundException
     */
    protected void removeEmployee(Employee employee) throws EmployeeNotFoundException {
        uniqueEmployeeList.remove(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shift shift = (Shift) o;
        return Objects.equals(startTime, shift.startTime)
                && Objects.equals(endTime, shift.endTime)
                && Objects.equals(day, shift.day)
                && Objects.equals(uniqueEmployeeList, shift.uniqueEmployeeList)
                && Objects.equals(capacity, shift.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, day, uniqueEmployeeList, capacity);
    }

    public ObservableList<Employee> getEmployeeList() {
        return uniqueEmployeeList.asObservableList();
    }

    public Day getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public int compareTo(Shift other) {
        if (day.equals(other.getDay())) {
            return startTime.compareTo(other.getStartTime());
        } else if (day.compareTo(other.getDay()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
