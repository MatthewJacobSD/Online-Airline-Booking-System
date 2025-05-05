package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Flight;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class SFlight extends BaseService<Flight> {
    public SFlight(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        super(scanner, fileHandler, consoleUI, "Flight", "flights.csv", Flight.class);
    }

    // the new flight method overrides the abstract method in BaseService
    @Override
    protected Flight addEntity() {
        consoleUI.showSectionHeader("Add New Flight");
        String id = UUID.randomUUID().toString();
        consoleUI.showStatus("✈️ Auto-generated ID: " + id);

        // Prompt for flight number, with validation
        String flightNo = null;
        while (flightNo == null) {
            consoleUI.showStatus("✈️ Enter flight number (e.g., AA123): ");
            String input = scanner.nextLine().trim();
            String error = Validator.validateFlightNumber(input);
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("✈️ Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            flightNo = input;
        }

        // Prompt for departure and arrival airports, with validation
        String depAirport = null;
        while (depAirport == null) {
            consoleUI.showStatus("🛫 Enter departure airport (3-letter code, e.g., JFK): ");
            String input = scanner.nextLine().trim().toUpperCase();
            String error = Validator.validateAirportCode(input, "Departure airport");
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🛫 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            depAirport = input;
        }

        // Prompt for arrival airport, with validation
        String arrAirport = null;
        while (arrAirport == null) {
            consoleUI.showStatus("🛬 Enter arrival airport (3-letter code, e.g., LAX): ");
            String input = scanner.nextLine().trim().toUpperCase();
            String error = Validator.validateAirportCode(input, "Arrival airport");
            if (error != null || input.equals(depAirport)) {
                consoleUI.showError(error != null ? error : "Arrival airport cannot be the same as departure airport.");
                consoleUI.showStatus("🛬 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            arrAirport = input;
        }

        // Prompt for departure and arrival times, with validation
        String depTime = null;
        while (depTime == null) {
            consoleUI.showStatus("🕒 Enter departure time (e.g., 2025-05-04 14:30): ");
            String input = scanner.nextLine().trim();
            String error = Validator.validateDateTime(input, "Departure time");
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🕒 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            depTime = input;
        }

        // Prompt for arrival time, with validation
        String arrTime = null;
        while (arrTime == null) {
            consoleUI.showStatus("🕒 Enter arrival time (e.g., 2025-05-04 17:45): ");
            String input = scanner.nextLine().trim();
            String error = Validator.validateDateTime(input, "Arrival time");
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🕒 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            LocalDateTime depDateTime = LocalDateTime.parse(depTime, Validator.DATETIME_FORMATTER);
            LocalDateTime arrDateTime = LocalDateTime.parse(input, Validator.DATETIME_FORMATTER);
            if (!arrDateTime.isAfter(depDateTime)) {
                consoleUI.showError("Arrival time must be after departure time.");
                consoleUI.showStatus("🕒 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            arrTime = input;
        }

        return new Flight(id, flightNo, depAirport, arrAirport, depTime, arrTime);
    }

    // return null if the flight is invalid
    @Override
    protected String validateEntity(Flight flight) {
        if (flight == null) {
            return "Flight cannot be null.";
        }

        String uuidError = Validator.validateUUID(flight.getId());
        if (uuidError != null) {
            return uuidError;
        }

        String flightNoError = Validator.validateFlightNumber(flight.getFlightNo());
        if (flightNoError != null) {
            return flightNoError;
        }

        String depAirportError = Validator.validateAirportCode(flight.getDepAirport(), "Departure airport");
        if (depAirportError != null) {
            return depAirportError;
        }

        String arrAirportError = Validator.validateAirportCode(flight.getArrAirport(), "Arrival airport");
        if (arrAirportError != null) {
            return arrAirportError;
        }

        if (flight.getDepAirport().equals(flight.getArrAirport())) {
            return "Departure and arrival airports cannot be the same.";
        }

        String depTimeError = Validator.validateDateTime(flight.getDepTime(), "Departure time");
        if (depTimeError != null) {
            return depTimeError;
        }

        String arrTimeError = Validator.validateDateTime(flight.getArrTime(), "Arrival time");
        if (arrTimeError != null) {
            return arrTimeError;
        }

        try {
            LocalDateTime depDateTime = LocalDateTime.parse(flight.getDepTime(), Validator.DATETIME_FORMATTER);
            LocalDateTime arrDateTime = LocalDateTime.parse(flight.getArrTime(), Validator.DATETIME_FORMATTER);
            if (!arrDateTime.isAfter(depDateTime)) {
                return "Arrival time must be after departure time.";
            }
        } catch (DateTimeParseException e) {
            return "Invalid datetime format in flight.";
        }

        return null;
    }
}