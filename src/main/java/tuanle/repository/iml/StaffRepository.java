package tuanle.repository.iml;

import tuanle.model.Staff;
import tuanle.repository.CrudRepository;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class StaffRepository implements CrudRepository<Staff> {

    private DataSource dataSource;
    private Connection cnn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * Close connection
     */
    private void closeConnection() {
        try {
            if(rs != null) {
                rs.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(cnn != null) {
                cnn.close();
            }
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger("DBLogger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
    }

    /**
     * Initialize datasource
     *
     * @param dataSource Data source
     */
    public StaffRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * {@inheritDoc}
     * Save data to database
     * @param data Data
     */
    @Override
    public void save(Collection<Staff> data) {
        if(data == null) {
            throw new IllegalArgumentException();
        }
        try {
            cnn = this.dataSource.getConnection();
            if(cnn != null) {
                String sql = "insert into Staff(id ,firstname,middleName, lastname, dob, phone, address) " +
                        " values(?,?,?,?,?,?,?)";
                Iterator iterator =  data.iterator();
                while (iterator.hasNext()) {
                    Staff staff = (Staff) iterator.next();
                    ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1,staff.getId());
                    ps.setString(2, staff.getFirstName());
                    ps.setString(3, staff.getMiddleName());
                    ps.setString(4, staff.getLastName());
                    if(staff.getDob() == null) {
                        ps.setTimestamp(5, null);
                    }
                    else {
                        ps.setTimestamp(5, Timestamp.valueOf(staff.getDob()));
                    }
                    ps.setString(6, staff.getPhone());
                    ps.setString(7, staff.getAddress());
                    ps.executeUpdate();
                    rs = ps.getGeneratedKeys();
                    if(rs.next()) {
                        int primaryKey = rs.getInt(1);
                        staff.setId(primaryKey);
                    }
                }
            }
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger("DBLogger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        }
        finally {
            closeConnection();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return collection of staff in database
     */
    @Override
    public Collection<Staff> findAll() {
        Collection<Staff> staffCollection = new ArrayList<>();
        try {
            cnn = dataSource.getConnection();
            if(cnn != null) {
                String sql = "select id, firstname,middleName, lastname, dob, phone, address " +
                        "from Staff";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String firstname = rs.getString("firstname");
                    String middleName = rs.getString("middleName");
                    String lastName = rs.getString("lastName");
                    LocalDateTime dob = rs.getTimestamp("dob").toLocalDateTime();
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    Staff staff = new Staff(id, firstname, middleName, lastName, dob, phone, address);
                    staffCollection.add(staff);
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger("DBLogger");
            logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
        } finally {
            closeConnection();
        }
        return staffCollection;
    }
}
