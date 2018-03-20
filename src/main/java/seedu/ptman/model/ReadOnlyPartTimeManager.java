package seedu.ptman.model;

import javafx.collections.ObservableList;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletName;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.tag.Tag;

/**
 * Unmodifiable view of ptman
 */
public interface ReadOnlyPartTimeManager {

    /**
     * Returns an unmodifiable view of the employees list.
     * This list will not contain any duplicate employees.
     */
    ObservableList<Employee> getEmployeeList();

    /**
     * Returns an unmodifiable view of the shift list.
     * This list will not contain any duplicate shifts.
     */
    ObservableList<Shift> getShiftList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    OutletName getOutletName();

    OperatingHours getOperatingHours();

    OutletContact getOutletContact();

}
