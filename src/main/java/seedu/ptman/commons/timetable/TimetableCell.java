package seedu.ptman.commons.timetable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outletinformation.Shift;

/**
 * Timetable cell representing a time slot
 */
public class TimetableCell {
    private int availableSlots;
    private ArrayList<Employee> employeeList;
    private LocalTime startTime;

    public TimetableCell(LocalTime startTime, int availableSlots) {
        this.employeeList = new ArrayList<>();
        this.startTime = startTime;
        this.availableSlots = availableSlots;
    }

    public TimetableCell(Shift shift) {
        this.employeeList = new ArrayList<>();
        this.startTime = shift.getStartDateTime().toLocalTime();
        this.availableSlots = shift.getCapacity();
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    protected int getAvailableSlots() {
        return availableSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimetableCell that = (TimetableCell) o;
        return availableSlots == that.availableSlots
                && Objects.equals(employeeList, that.employeeList)
                && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableSlots, employeeList, startTime);
    }

}
