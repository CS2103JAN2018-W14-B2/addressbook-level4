package seedu.ptman.model.outletinformation;

import java.time.LocalDateTime;

import seedu.ptman.model.employee.UniqueEmployeeList;

/**
 * PLACEHOLDER: For testing purposes
 */
public class Shift {
    private LocalDateTime startDateTime;
    private UniqueEmployeeList employeeList;
    private int capacity;
    private int numRegistered;

    public Shift(LocalDateTime startDateTime, int capacity, int numRegistered) {
        this.startDateTime = startDateTime;
        this.capacity = capacity;
        this.numRegistered = numRegistered;
    }

    public Shift(LocalDateTime startDateTime, int capacity) {
        this.startDateTime = startDateTime;
        this.capacity = capacity;
        this.numRegistered = 0;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumRegistered() {
        return numRegistered;
    }

}
