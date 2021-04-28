package tuanle;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import main.java.tuanle.model.Staff;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:" + UUID.randomUUID().toString());   // In-memory database
        config.setDriverClassName("org.h2.Driver");                         // H2 driver

        // Initialize repository
        final DataSource dataSource = new HikariDataSource(config); // Declare your datasource
        final CrudRepository<Staff> repository = new StaffRepository(dataSource);

        // Save a new staff into data persistence
        final Staff staff = new Staff();
        repository.save(Collections.singletonList(staff));

        // Retrieve all staffs
        final Collection<Staff> staffs = repository.findAll();

    }
}
