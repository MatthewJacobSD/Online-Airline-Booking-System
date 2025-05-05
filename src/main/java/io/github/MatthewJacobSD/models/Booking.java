package io.github.MatthewJacobSD.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Booking {
    // private fields
    private String id;
    private LocalDate date;
    private String customerId;
    private String flightId;

    // no args constructor
    public Booking() {}

    // constructor with parameters
    public Booking(String id, LocalDate date, String customerId, String flightId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.flightId = flightId;
    }

    // getters
    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getCustomerId() { return customerId; }
    public String getFlightId() { return flightId; }

    // setters
    public void setDate(LocalDate date) { this.date = date; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    /**
     * Output method.
     * <p>
     * Converts a booking object into a human-readable string.
     *
     * @return A string representation of the booking.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Booking \n{\nid: %s,\n date: %s,\n customerId: %s,\n flightId: %s\n}",
                id, date.format(formatter), customerId, flightId);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two bookings are considered equal if they have the same id.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    /**
     * Returns a hash code value for the object.
     * <p>
     * The hash code is based on the id of the booking.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}