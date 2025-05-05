package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Booking;
import io.github.MatthewJacobSD.models.Customer;
import io.github.MatthewJacobSD.models.Flight;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;
import io.github.MatthewJacobSD.utils.CSVHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class SBooking extends BaseService<Booking> {
    public SBooking(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI,
                        Map<String, String> referenceFilePaths) {
        super(scanner, fileHandler, consoleUI, "Booking", "bookings.csv", Booking.class, referenceFilePaths);
    }

    @Override
    protected Booking addEntity() {
        consoleUI.showSectionHeader("Add New Booking");
        String id = UUID.randomUUID().toString();
        consoleUI.showStatus("📛 Auto-generated ID: " + id);

        // Prompt for booking date, with validation
        LocalDate date = null;
        while (date == null) {
            consoleUI.showStatus("📅 Enter booking date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine().trim();
            String dateError = Validator.validateDate(dateInput, "Booking date");
            if (dateError != null) {
                consoleUI.showError(dateError);
                consoleUI.showStatus("📅 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            date = LocalDate.parse(dateInput, Validator.DATE_FORMATTER);
        }

        // Prompt for customer ID, with validation
        String customerId = null;
        while (customerId == null) {
            consoleUI.showStatus("🧑 Enter customer ID: ");
            String input = scanner.nextLine().trim();
            String error = Validator.validateUUID(input);
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🧑 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            String content = fileHandler.readFile("customers.csv");
            if (content != null && !content.isEmpty()) {
                List<Customer> customers = CSVHandler.fromCSV(content, Customer.class, null);
                if (customers.stream().noneMatch(c -> c.getId().equals(input))) {
                    consoleUI.showError("Customer ID does not exist in customers.csv.");
                    consoleUI.showStatus("🧑 Try again? (y/n): ");
                    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        return null;
                    }
                    continue;
                }
            } else {
                consoleUI.showError("No customers found in customers.csv.");
                return null;
            }
            customerId = input;
        }

        // Prompt for flight ID, with validation
        String flightId = null;
        while (flightId == null) {
            consoleUI.showStatus("✈️ Enter flight ID: ");
            String input =scanner.nextLine().trim();
            String error = Validator.validateUUID(input);
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("✈️ Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            String content = fileHandler.readFile("flights.csv");
            if (content != null && !content.isEmpty()) {
                List<Flight> flights = CSVHandler.fromCSV(content, Flight.class, null);
                if (flights.stream().noneMatch(f -> f.getId().equals(input))) {
                    consoleUI.showError("Flight ID does not exist in flights.csv.");
                    consoleUI.showStatus("✈️ Try again? (y/n): ");
                    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        return null;
                    }
                    continue;
                }
            } else {
                consoleUI.showError("No flights found in flights.csv.");
                return null;
            }
            flightId = input;
        }

        return new Booking(id, date, customerId, flightId);
    }

    @Override
    protected String validateEntity(Booking booking) {
        if (booking == null) {
            return "Booking cannot be null.";
        }

        // Validate UUID
        String uuidError = Validator.validateUUID(booking.getId());
        if (uuidError != null) {
            return uuidError;
        }

        // Validate date by converting to string
        String dateStr = booking.getDate() != null ? booking.getDate().format(Validator.DATE_FORMATTER) : null;
        String dateError = Validator.validateDate(dateStr, "Booking date");
        if (dateError != null) {
            return dateError;
        }

        // Validate customer ID
        String customerError = Validator.validateUUID(booking.getCustomerId());
        if (customerError != null) {
            return customerError;
        }

        // Validate flight ID
        return Validator.validateUUID(booking.getFlightId());
    }
}