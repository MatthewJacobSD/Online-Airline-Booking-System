package io.github.MatthewJacobSD.utils;

import io.github.MatthewJacobSD.services.*;

import java.util.Map;
import java.util.Scanner;

public class ServiceRouter {
    private final SCustomer sCustomer;
    private final SBooking sBooking;
    private final SFlight sFlight;
    private final SRoute sRoute;

    public ServiceRouter(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        // Customer service doesn't need references
        this.sCustomer = new SCustomer(scanner, fileHandler, consoleUI);

        // Booking service needs customer and flight references
        Map<String, String> bookingRefs = Map.of(
                "customers", "customers.csv",
                "flights", "flights.csv"
        );
        this.sBooking = new SBooking(scanner, fileHandler, consoleUI, bookingRefs);

        // Flight service needs route references
        Map<String, String> flightRefs = Map.of("routes", "routes.csv");
        this.sFlight = new SFlight(scanner, fileHandler, consoleUI, flightRefs);

        // Route service doesn't need references
        this.sRoute = new SRoute(scanner, fileHandler, consoleUI);
    }

    public void route(int choice) {
        switch (choice) {
            case 1 -> sCustomer.data();
            case 2 -> sBooking.data();
            case 3 -> sFlight.data();
            case 4 -> sRoute.data();
            default -> System.out.println("❌ Invalid choice! Please try again.");
        }
    }
}