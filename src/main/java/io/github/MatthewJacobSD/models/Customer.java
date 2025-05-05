package io.github.MatthewJacobSD.models;

import java.util.Objects;

public class Customer {
    // private fields
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String address;

    // default constructor
    public Customer() {}

    // constructor with parameters
    public Customer(String id,
                    String firstName,
                    String lastName,
                    String email,
                    String phoneNo,
                    String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    // getters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNo() { return phoneNo; }
    public String getAddress() { return address; }

    // setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public void setAddress(String address) { this.address = address; }

    /**
     * Converts a customer object into a human-readable string.
     * <p>
     * This method is used for displaying customer information in a readable format.
     *
     * @return A string representation of the customer.
     */
    // output method - terminal view
    @Override
    public String toString() {
        return String.format(
                "Customer \n{\nid: %s,\n firstName: %s,\n lastName: %s,\n email: %s,\n phoneNo: %s,\n address: %s\n}",
                id, firstName, lastName, email, phoneNo, address);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two customers are considered equal if they have the same id.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    /**
     * Returns a hash code value for the object.
     * <p>
     * The hash code is based on the id of the customer.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}