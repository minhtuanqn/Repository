package tuanle.model;

import java.time.LocalDateTime;

/**
 * Staff model
 */
public class Staff {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDateTime dob;
    private String phone;
    private String address;

    public Staff() {
    }

    public Staff(Integer id, String firstName, String middleName, String lastName,
                 LocalDateTime dob, String phone, String address) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String firstName;
        private String middleName;
        private String lastName;
        private LocalDateTime dob;
        private String phone;
        private String address;

        public Builder(Integer id) {
            this.id = id;
        }

        public Builder hasFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder andLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder wasBorn(LocalDateTime dob) {
            this.dob = dob;
            return this;
        }

        public Builder hasPhoneNumber(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Staff build() {
            Staff staff = new Staff(this.id, this.firstName, this.middleName, this.lastName,
                    this.dob, this.phone, this.address);
            return staff;
        }


    }


}
