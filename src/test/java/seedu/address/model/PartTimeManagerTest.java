package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalPartTimeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.employee.Employee;
import seedu.address.model.tag.Tag;

public class PartTimeManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PartTimeManager partTimeManager = new PartTimeManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), partTimeManager.getEmployeeList());
        assertEquals(Collections.emptyList(), partTimeManager.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        partTimeManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyPartTimeManager_replacesData() {
        PartTimeManager newData = getTypicalPartTimeManager();
        partTimeManager.resetData(newData);
        assertEquals(newData, partTimeManager);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsAssertionError() {
        // Repeat ALICE twice
        List<Employee> newEmployees = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        PartTimeManagerStub newData = new PartTimeManagerStub(newEmployees, newTags);

        thrown.expect(AssertionError.class);
        partTimeManager.resetData(newData);
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        partTimeManager.getEmployeeList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        partTimeManager.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyPartTimeManager whose employees and tags lists can violate interface constraints.
     */
    private static class PartTimeManagerStub implements ReadOnlyPartTimeManager {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        PartTimeManagerStub(Collection<Employee> employees, Collection<? extends Tag> tags) {
            this.employees.setAll(employees);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
