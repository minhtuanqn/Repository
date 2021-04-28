package repository;

import main.java.tuanle.model.Staff;

public final class RepositoryTestUtils {

    private RepositoryTestUtils() {
        // Empty constructor
    }

    public static Staff createMockStaff() {
        return new Staff();
    }
}
