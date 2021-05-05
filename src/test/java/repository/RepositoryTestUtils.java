package repository;


import tuanle.model.Staff;

import java.time.LocalDateTime;

public final class RepositoryTestUtils {

    private RepositoryTestUtils() {
        // Empty constructor
    }

    public static Staff createMockStaff() {
        Staff staff = new Staff();
        staff.setId(2199);
        staff.setFirstName("A");
        staff.setMiddleName("B");
        staff.setLastName("C");
        staff.setPhone("09876");
        staff.setDob(LocalDateTime.of(2020, 11, 26, 13, 55, 36, 123));
        staff.setAddress("A");
        return staff;
    }
}
