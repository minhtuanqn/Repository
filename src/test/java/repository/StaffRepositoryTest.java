package java.repository;

import main.java.tuanle.CrudRepository;
import main.java.tuanle.StaffRepository;
import main.java.tuanle.model.Staff;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class StaffRepositoryTest extends DatabaseTestSupport {
    @Test(expected = IllegalArgumentException.class)
    public void whenSave_NullData_ThenReject() {
        new StaffRepository(dataSource).save(null);
    }

    @Test
    public void whenSave_MultipleStaffs_ThenSave() {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        final Staff staffA = RepositoryTestUtils.createMockStaff();
        final Staff staffB = RepositoryTestUtils.createMockStaff();

        // Execute
        repository.save(Arrays.asList(staffA, staffB));

        // Assertion
        assertEquals(1L, (long) staffA.getId());
        assertEquals(2L, (long) staffB.getId());
    }
}
