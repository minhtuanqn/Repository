package tuanle.main;

import tuanle.main.CrudRepository;
import tuanle.main.Staff;

import javax.sql.DataSource;
import java.util.Collection;

public class StaffRepository implements CrudRepository<Staff> {

    /**
     * Initialize
     *
     * @param dataSource Data source
     */
    public StaffRepository(DataSource dataSource) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     *
     * @param data Data
     */
    @Override
    public void save(Collection<Staff> data) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Collection<Staff> findAll() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
