package tuanle.repository.iml;

import org.slf4j.LoggerFactory;
import tuanle.model.Staff;
import tuanle.repository.CrudRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StaffRepository implements CrudRepository<Staff> {

    private DataSource dataSource;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StaffRepository.class);


    /**
     * Initialize datasource
     *
     * @param dataSource Data source
     */
    public StaffRepository(DataSource dataSource) throws SQLException {
        if (dataSource == null) {
            throw new IllegalArgumentException();
        }
        this.dataSource = dataSource;
    }

    /**
     * Set generated ID to query list for testing
     * @param iterator
     * @param rs
     * @throws SQLException
     */
    private void setGeneratedIdToList(Iterator<Staff> iterator, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int primaryKey = rs.getInt(1);
            if(iterator.hasNext()) {
                iterator.next().setId(primaryKey);
            }
        }
    }

    private void closeConnection(PreparedStatement ps, ResultSet rs, Connection cnn) {
        try {
            if(rs != null) {
                rs.close();
            }
        }
        catch (SQLException e) {
            String message = "ERROR: SQLException when close ResultSet for save data to Database";
            LOGGER.error(message, e.fillInStackTrace());
        }
        finally {
            try {
                if(ps != null) {
                    ps.close();
                }
            }
            catch (SQLException e) {
                String message = "ERROR: SQLException when close PrepareStatement for save data to Database";
                LOGGER.error(message, e.fillInStackTrace());
            }
            finally {
                try {
                    if(cnn != null) {
                        cnn.close();
                    }
                }
                catch (SQLException e) {
                    String message = "ERROR: SQLException when close Connection for save data to Database";
                    LOGGER.error(message, e.fillInStackTrace());
                }

            }

        }
    }


    /**
     * {@inheritDoc}
     * Save data to database
     *
     * @param data Data
     */
    @Override
    public void save(Collection<Staff> data) {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (data == null || data.size() == 0) {
            throw new IllegalArgumentException();
        }
        final String sql = "INSERT INTO Staff(id ,firstname,middleName, lastname, dob, phone, address) " +
                " VALUES(?,?,?,?,?,?,?)";
        try {
            cnn = this.dataSource.getConnection();
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            cnn.setAutoCommit(false);
            Iterator<Staff> iterator = data.iterator();
            while (iterator.hasNext()) {
                Staff staff = iterator.next();
                LocalDateTime dobLocalTime = staff.getDob();
                Timestamp dobTimeStamp = null;
                if (dobLocalTime != null) {
                    dobTimeStamp = Timestamp.valueOf(dobLocalTime);
                }
                ps.setInt(1, staff.getId());
                ps.setString(2, staff.getFirstName());
                ps.setString(3, staff.getMiddleName());
                ps.setString(4, staff.getLastName());
                ps.setTimestamp(5, dobTimeStamp);
                ps.setString(6, staff.getPhone());
                ps.setString(7, staff.getAddress());
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
            rs = ps.getGeneratedKeys();
            setGeneratedIdToList(data.iterator(), rs);
            cnn.commit();
        } catch (SQLException e) {
            try {
                cnn.rollback();
            }
            catch (SQLException ex) {
                String message = "ERROR: SQLException when can not rollback data when occur SQL exception";
                LOGGER.error(message, ex.fillInStackTrace());
            }
            String message = "ERROR: SQLException when save data to Database";
            LOGGER.error(message, e.fillInStackTrace());
        }
        finally {
            closeConnection(ps, rs, cnn);
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
        final String sql = "SELECT id, firstname,middleName, lastname, dob, phone, address " +
                "FROM Staff";
        try (Connection cnn = dataSource.getConnection();
             PreparedStatement ps = cnn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                Timestamp dobTimeStamp = rs.getTimestamp("dob");
                LocalDateTime dob = null;
                if(dobTimeStamp != null) {
                    dob = dobTimeStamp.toLocalDateTime();
                }
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Staff staff = new Staff.Builder(id).hasFirstName(firstname)
                        .withMiddleName(middleName)
                        .andLastName(lastName)
                        .wasBorn(dob)
                        .hasPhoneNumber(phone)
                        .withAddress(address).build();
                staffCollection.add(staff);
            }

        } catch (SQLException e) {
            String message = "ERROR: SQLException when query data from Database";
            LOGGER.error(message, e.fillInStackTrace());
        }
        return staffCollection;
    }
}
