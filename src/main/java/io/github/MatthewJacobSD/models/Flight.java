package io.github.MatthewJacobSD.models;

import java.util.Objects;

public class Flight {
    // private fields
    private String id;
    private String flightNo;
    private String depAirport;
    private String arrAirport;
    private String depTime;
    private String arrTime;

    // default constructor
    public Flight() {}

    // constructor with parameters
    public Flight(String id,
                  String flightNo,
                  String depAirport,
                  String arrAirport,
                  String depTime,
                  String arrTime) {
        this.id = id;
        this.flightNo = flightNo;
        this.depAirport = depAirport;
        this.arrAirport = arrAirport;
        this.depTime = depTime;
        this.arrTime = arrTime;
    }

    // getters
    public String getId() { return id; }
    public String getFlightNo() { return flightNo; }
    public String getDepAirport() { return depAirport; }
    public String getArrAirport() { return arrAirport; }
    public String getDepTime() { return depTime; }
    public String getArrTime() { return arrTime; }

    // setters
    public void setFlightNo(String flightNo) { this.flightNo = flightNo; }
    public void setDepAirport(String depAirport){ this.depAirport = depAirport; }
    public void setArrAirport(String arrAirport) { this.arrAirport = arrAirport; }
    public void setDepTime(String depTime) { this.depTime = depTime; }
    public void setArrTime(String arrTime) { this.arrTime = arrTime; }

    /**
     * Converts a flight object into a human-readable string.
     * <p>
     * This method is used for displaying flight information in a readable format.
     *
     * @return A string representation of the flight.
     */
    // output method
    @Override
    public String toString() {
        return String.format(
                "Flight \n{\nid: %s,\n flightNo: %s,\n depAirport: %s,\n arrAirport: %s,\n depTime: %s,\n arrTime: %s\n}\n",
                id, flightNo, depAirport, arrAirport, depTime, arrTime);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two flights are considered equal if they have the same id.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    /**
     * Returns a hash code value for the object.
     * <p>
     * The hash code is based on the id of the flight.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
