package io.github.MatthewJacobSD.models;

import java.util.Objects;

public class Route {
    // private fields
    private String id;
    private String name;

    // default constructor
    public Route() {}

    // constructor with parameters
    public Route(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }

    // setter
    public void setName(String name) { this.name = name; }

    /**
     * Converts a route object into a human-readable string.
     * <p>
     * This method is used for displaying route information in a readable format.
     *
     * @return A string representation of the route.
     */
    // output method
    @Override
    public String toString() {
        return String.format("Route \n{\nid: %s,\n name: %s\n}\n", id, name);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two routes are considered equal if they have the same id.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    /**
     * Returns a hash code value for the object.
     * <p>
     * This method is used in hash tables, providing a way to quickly locate a data record given its search key.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
