package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Flight;
import io.github.MatthewJacobSD.models.Route;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;
import io.github.MatthewJacobSD.utils.CSVHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class SFlight extends BaseService<Flight> {
    public SFlight(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI,
                   Map<String, String> referenceFilePaths) {
        super(scanner, fileHandler, consoleUI, "Flight", "flights.csv", Flight.class, referenceFilePaths);
    }

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
        LocalDateTime depTime = null;
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
            depTime = LocalDateTime.parse(input, Validator.DATETIME_FORMATTER);
        }

        LocalDateTime arrTime = null;
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
            LocalDateTime arrDateTime = LocalDateTime.parse(input, Validator.DATETIME_FORMATTER);
            if (!arrDateTime.isAfter(depTime)) {
                consoleUI.showError("Arrival time must be after departure time.");
                consoleUI.showStatus("🕒 Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            arrTime = arrDateTime;
        }

        // Prompt for route ID, with validation
        String routeId = null;
        while (routeId == null) {
            consoleUI.showStatus("🛤️ Enter route ID: ");
            String input = scanner.nextLine().trim();
            String error = Validator.validateUUID(input);
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🛤️ Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            String content = fileHandler.readFile("routes.csv");
            if (content != null && !content.isEmpty()) {
                List<Route> routes = CSVHandler.fromCSV(content, Route.class, null);
                if (routes.stream().noneMatch(r -> r.getId().equals(input))) {
                    consoleUI.showError("Route ID does not exist in routes.csv.");
                    consoleUI.showStatus("🛤️ Try again? (y/n): ");
                    if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        return null;
                    }
                    continue;
                }
            } else {
                consoleUI.showError("No routes found in routes.csv.");
                return null;
            }
            routeId = input;
        }

        return new Flight(id, flightNo, depAirport, arrAirport, depTime, arrTime, routeId);
    }

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

        String depTimeError = Validator.validateDateTime(
                flight.getDepTime() != null ? flight.getDepTime().format(Validator.DATETIME_FORMATTER) : null,
                "Departure time");
        if (depTimeError != null) {
            return depTimeError;
        }

        String arrTimeError = Validator.validateDateTime(
                flight.getArrTime() != null ? flight.getArrTime().format(Validator.DATETIME_FORMATTER) : null,
                "Arrival time");
        if (arrTimeError != null) {
            return arrTimeError;
        }

        if (flight.getArrTime() != null && flight.getDepTime() != null &&
                !flight.getArrTime().isAfter(flight.getDepTime())) {
            return "Arrival time must be after departure time.";
        }

        String routeError = Validator.validateUUID(flight.getRouteId());
        if (routeError != null) {
            return routeError;
        }

        String content = fileHandler.readFile("routes.csv");
        if (content != null && !content.isEmpty()) {
            List<Route> routes = CSVHandler.fromCSV(content, Route.class, null);
            if (routes.stream().noneMatch(r -> r.getId().equals(flight.getRouteId()))) {
                return "Route ID does not exist in routes.csv.";
            }
        } else {
            return "No routes found in routes.csv.";
        }

        return null;
    }
}