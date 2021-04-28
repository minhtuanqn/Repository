package tuanle;

import main.java.tuanle.model.Staff;

import javax.sql.DataSource;
import java.util.Collection;

public class StaffRepository implements CrudRepository<Staff> {

    private DataSource dataSource;

    /**
     * Initialize
     *
     * @param dataSource Data source
     */
    public StaffRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     *
     * @param data Data
     */
    @Override
    public void save(Collection<Staff> data) {
        if(data == null) {
            throw new IllegalArgumentException();
        }
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
