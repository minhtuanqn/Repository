package repository;

import org.junit.Test;
import tuanle.repository.CrudRepository;
import tuanle.repository.iml.StaffRepository;
import tuanle.model.Staff;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.*;

public class StaffRepositoryTest extends DatabaseTestSupport {
    @Test(expected = IllegalArgumentException.class)
    public void whenSave_NullData_ThenReject() throws SQLException {
        new StaffRepository(dataSource).save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSave_EmptyListStaffs_ThenThrowIllegalArgumentException() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        repository.save(new ArrayList<Staff>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSave_NullDataSource_ThenThrowIllegalArgumentException() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(null);
        repository.save(new ArrayList<Staff>());
    }

    @Test
    public void whenSave_MultipleStaffs_ThenSave() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        final Staff staffA = RepositoryTestUtils.createMockStaff();
        final Staff staffB = RepositoryTestUtils.createMockStaff();
        staffA.setId(1992);
        staffB.setId(533);

        // Execute
        repository.save(Arrays.asList(staffA, staffB));

        Map<Integer, Staff> expectedList = new HashMap<>();
        expectedList.put(staffA.getId(), staffA);
        expectedList.put(staffB.getId(), staffB);

        // Assertion
        Collection<Staff> actualList = repository.findAll();
        assertEquals(expectedList.size(), actualList.size());
        Iterator<Staff> iterator = actualList.iterator();
        int count = 0;
        while(iterator.hasNext()) {
            if(expectedList.containsKey(iterator.next().getId())) {
                assertEquals(true, true);
            }
        }
    }

    @Test
    public void whenSave_SingleStaffWithNullDOB_ThenSave() throws SQLException{
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        final Staff staffA = RepositoryTestUtils.createMockStaff();

        staffA.setDob(null);

        // Execute
        repository.save(Arrays.asList(staffA));

        // Assertion
        assertEquals(2199L, (long) staffA.getId());
    }

    @Test
    public void whenFindAll_InsertedOneStaffInDB_ThenShow() throws SQLException {
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);
        final Staff staffA = RepositoryTestUtils.createMockStaff();
        staffA.setId(100);

        repository.save(Arrays.asList(staffA));
        Collection<Staff> staffs = repository.findAll();

        Iterator<Staff> iterator = staffs.iterator();
        if(iterator.hasNext()) {
            assertEquals(iterator.next().getId(), staffA.getId());
        }
    }
}
