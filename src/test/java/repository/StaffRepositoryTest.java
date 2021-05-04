package repository;

import org.junit.Test;
import tuanle.repository.CrudRepository;
import tuanle.repository.iml.StaffRepository;
import tuanle.model.Staff;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class StaffRepositoryTest extends DatabaseTestSupport {
    @Test(expected = IllegalArgumentException.class)
    public void whenSave_NullData_ThenReject() throws SQLException {
        new StaffRepository(dataSource).save(null);
    }

    @Test
    public void whenSave_MultipleStaffs_ThenSave() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        final Staff staffA = RepositoryTestUtils.createMockStaff();
        final Staff staffB = RepositoryTestUtils.createMockStaff();

        staffA.setId(1);
        staffA.setFirstName("A");
        staffA.setMiddleName("B");
        staffA.setLastName("C");
        staffA.setPhone("09876");
        staffA.setDob(LocalDateTime.of(2020, 11, 26, 13, 55, 36, 123));
        staffA.setAddress("A");

        staffB.setId(2);
        staffB.setFirstName("A");
        staffB.setMiddleName("B");
        staffB.setLastName("C");
        staffB.setPhone("09876");
        staffB.setDob( LocalDateTime.of(2020, 11, 26, 13, 55, 36, 123));
        staffB.setAddress("B");

        // Execute
        repository.save(Arrays.asList(staffA, staffB));

        // Assertion
        assertEquals(1L, (long) staffA.getId());
        assertEquals(2L, (long) staffB.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSave_EmptyListStaffs_ThenSave() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        repository.save(new ArrayList<Staff>());
    }

    @Test(expected = SQLException.class)
    public void whenSave_DataSourceIsNull_ThenSave() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(null);
        repository.save(new ArrayList<Staff>());
    }
}
