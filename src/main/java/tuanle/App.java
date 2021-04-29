package tuanle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tuanle.db.DBHelper;
import tuanle.model.Staff;
import tuanle.repository.CrudRepository;
import tuanle.repository.iml.StaffRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;

public class App {
    public static void main(String[] args) {

        // Initialize repository
        final DataSource dataSource = DBHelper.getDataSource(); // Declare datasource
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);

        // Save a new staff into data persistence
        final Staff staff = new Staff();
        staff.setId(2);
        staff.setFirstName("A");
        staff.setMiddleName("B");
        staff.setLastName("C");
        staff.setPhone("09876");
        staff.setDob(LocalDateTime.of(2020, 11, 26, 13, 55, 36, 123));
        staff.setAddress("A");

        repository.save(Collections.singletonList(staff));
        Collection<Staff> collection = repository.findAll();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println("ID of staff: " + ((Staff) iterator.next()).getId());
        }

        // Retrieve all staffs
        final Collection<Staff> staffs = repository.findAll();

    }
}
