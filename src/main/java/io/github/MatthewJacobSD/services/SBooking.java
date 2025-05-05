package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Booking;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class SBooking extends BaseService<Booking> {
    public SBooking(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        super(scanner, fileHandler, consoleUI, "Booking", "bookings.csv", Booking.class);
    }

    // the new booking method overrides the abstract method in BaseService
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
                    return null; // Skip this booking
                }
                continue;
            }
            date = LocalDate.parse(dateInput, Validator.DATE_FORMATTER);
        }

        return new Booking(id, date);
    }

    // return null if the booking is invalid
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
        return Validator.validateDate(dateStr, "Booking date");
    }
}