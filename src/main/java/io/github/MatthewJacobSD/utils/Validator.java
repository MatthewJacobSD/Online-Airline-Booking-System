package io.github.MatthewJacobSD.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{7,15}$");
    private static final Pattern FLIGHT_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]{2,6}$");
    private static final Pattern AIRPORT_CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private static final Pattern ROUTE_NAME_PATTERN = Pattern.compile("^[A-Z]{3}-[A-Z]{3}$");

    public static String validateNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return "Invalid email format (e.g., user@domain.com).";
        }
        return null;
    }

    public static String validatePhone(String phoneNo) {
        if (phoneNo == null || !PHONE_PATTERN.matcher(phoneNo).matches()) {
            return "Phone number must be 7-15 digits.";
        }
        return null;
    }

    public static String validateUUID(String uuid) {
        try {
            java.util.UUID.fromString(uuid);
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid UUID format.";
        }
    }

    public static String validateDate(String dateStr, String fieldName) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusYears(1);
            if (date.isBefore(today)) {
                return fieldName + " must be today or in the future.";
            }
            if (date.isAfter(maxDate)) {
                return fieldName + " cannot be more than one year in the future.";
            }
            return null;
        } catch (DateTimeParseException e) {
            return "Invalid " + fieldName.toLowerCase() + " format. Use yyyy-MM-dd (e.g., 2025-12-31).";
        }
    }

    public static String validateFlightNumber(String flightNo) {
        if (flightNo == null || !FLIGHT_NUMBER_PATTERN.matcher(flightNo).matches()) {
            return "Flight number must be 2-6 alphanumeric characters (e.g., AA123).";
        }
        return null;
    }

    public static String validateAirportCode(String code, String fieldName) {
        if (code == null || !AIRPORT_CODE_PATTERN.matcher(code).matches()) {
            return fieldName + " must be a 3-letter uppercase code (e.g., JFK).";
        }
        return null;
    }

    public static String validateDateTime(String dateTimeStr, String fieldName) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime maxDateTime = now.plusYears(1);
            if (dateTime.isBefore(now)) {
                return fieldName + " must be now or in the future.";
            }
            if (dateTime.isAfter(maxDateTime)) {
                return fieldName + " cannot be more than one year in the future.";
            }
            return null;
        } catch (DateTimeParseException e) {
            return "Invalid " + fieldName.toLowerCase() + " format. Use yyyy-MM-dd HH:mm (e.g., 2025-05-04 14:30).";
        }
    }

    public static String validateRouteName(String name) {
        if (name == null || !ROUTE_NAME_PATTERN.matcher(name).matches()) {
            return "Route name must be in format XXX-YYY (e.g., JFK-LAX).";
        }
        return null;
    }
}