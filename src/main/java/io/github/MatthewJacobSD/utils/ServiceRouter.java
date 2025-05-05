package io.github.MatthewJacobSD.utils;

import io.github.MatthewJacobSD.services.SBooking;
import io.github.MatthewJacobSD.services.SCustomer;
import io.github.MatthewJacobSD.services.SFlight;
import io.github.MatthewJacobSD.services.SRoute;

import java.util.Scanner;

public class ServiceRouter {
    private final SCustomer sCustomer;
    private final SBooking sBooking;
    private final SFlight sFlight;
    private final SRoute sRoute;

    public ServiceRouter(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        this.sCustomer = new SCustomer(scanner, fileHandler, consoleUI);
        this.sBooking = new SBooking(scanner, fileHandler, consoleUI);
        this.sFlight = new SFlight(scanner, fileHandler, consoleUI);
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