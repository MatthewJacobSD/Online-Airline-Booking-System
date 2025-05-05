package io.github.MatthewJacobSD.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Booking {
    // private fields
    private String id;
    private LocalDate date;

    // no args constructor
    public Booking() {}

    // constructor with parameters
    public Booking(String id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    // getters
    public String getId() { return id; }
    public LocalDate getDate() { return date; }

    // setter
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Output method.
     * <p>
     * Converts a booking object into a human-readable string.
     *
     * @return A string representation of the booking.
     */
    @Override
    public String toString() {
        // format date for csv transition
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Booking \n{\nid: %s,\n date: %s\n}", id, date.format(formatter));
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
