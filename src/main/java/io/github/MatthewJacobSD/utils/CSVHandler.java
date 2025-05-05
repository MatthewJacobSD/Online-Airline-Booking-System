package io.github.MatthewJacobSD.utils;

import io.github.MatthewJacobSD.services.BaseService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.MatthewJacobSD.utils.Validator.DATE_FORMATTER;
import static io.github.MatthewJacobSD.utils.Validator.DATETIME_FORMATTER;

public class CSVHandler {

    /**
     * Converts a list of objects to CSV format.
     * @param objects List of objects to convert.
     * @return CSV formatted string.
     */
    public static String toCSV(List<?> objects) {
        if (objects == null || objects.isEmpty()) {
            return "";
        }
        StringBuilder csvContent = new StringBuilder();
        processObject(objects.getFirst(), csvContent, true); // Headers only
        objects.forEach(obj -> processObject(obj, csvContent, false)); // Data rows
        return csvContent.toString();
    }

    /**
     * Processes an object into CSV format.
     * @param obj The object to process.
     * @param csvContent StringBuilder to append the result to.
     * @param includeHeader Whether to include the header row.
     */
    private static void processObject(Object obj, StringBuilder csvContent, boolean includeHeader) {
        try {
            Class<?> objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();

            if (includeHeader) {
                appendFields(fields, csvContent, true, null); // Headers only
            } else {
                appendFields(fields, csvContent, false, obj); // Data row
            }
        } catch (Exception e) {
            System.err.println("❌ Error processing object: " + e.getMessage());
        }
    }

    /**
     * Appends fields to the CSV content.
     * @param fields Array of fields to process.
     * @param builder StringBuilder to append to.
     * @param isHeader Whether we're processing headers.
     * @param obj The object to get values from (null if isHeader).
     * @throws IllegalAccessException If field access fails.
     */
    private static void appendFields(Field[] fields, StringBuilder builder,
                                     boolean isHeader, Object obj)
            throws IllegalAccessException {
        StringBuilder line = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = isHeader ? field.getName() : field.get(obj);
            String formattedValue = formatValue(value);
            line.append(formattedValue).append(",");
        }

        if (!line.isEmpty()) {
            line.deleteCharAt(line.length() - 1);
            builder.append(line).append("\n");
        }
    }

    /**
     * Formats a value for CSV (quotes strings, formats dates).
     * @param value The value to format.
     * @return Formatted string.
     */
    private static String formatValue(Object value) {
        switch (value) {
            case null -> {
                return "";
            }
            case LocalDate date -> {
                return date.format(DATE_FORMATTER);
            }
            case LocalDateTime dateTime -> {
                return dateTime.format(DATETIME_FORMATTER);
            }
            default -> {
            }
        }
        String strValue = value.toString();
        strValue = strValue.replace("\"", "\"\"");
        if (strValue.contains(",") || strValue.contains("\"")) {
            return "\"" + strValue + "\"";
        }
        return strValue;
    }

    /**
     * Parses a CSV string into a list of objects of the specified class.
     * @param csvContent The CSV string to parse.
     * @param clazz The class type to instantiate (e.g., Customer.class).
     * @param service The BaseService to validate parsed objects (nullable).
     * @return A list of parsed objects.
     */
    public static <T> List<T> fromCSV(String csvContent, Class<T> clazz, BaseService<T> service) {
        List<T> objects = new ArrayList<>();
        if (csvContent == null || csvContent.trim().isEmpty()) {
            return objects;
        }

        String[] lines = csvContent.split("\n");
        if (lines.length < 1) {
            return objects;
        }

        // Extract headers from the first line
        String[] headers = parseCSVLine(lines[0]);
        Field[] fields = clazz.getDeclaredFields();

        // Validate headers match field names
        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).toList();
        if (!Arrays.stream(headers).allMatch(fieldNames::contains)) {
            System.err.println("❌ CSV headers do not match class fields. Expected: " + fieldNames + ", Found: " + Arrays.toString(headers));
            return objects;
        }

        // Process data lines (skip header)
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            T obj = parseLine(line, headers, clazz, service);
            if (obj != null) {
                objects.add(obj);
            }
        }

        return objects;
    }

    /**
     * Parses a single CSV line into an object.
     * @param line The CSV line.
     * @param headers The CSV headers.
     * @param clazz The class type to instantiate.
     * @param service The BaseService to validate the object (nullable).
     * @return The parsed object or null if parsing fails.
     */
    private static <T> T parseLine(String line, String[] headers, Class<T> clazz, BaseService<T> service) {
        try {
            String[] values = parseCSVLine(line);
            if (values.length != headers.length) {
                System.err.println("❌ Mismatched field count in CSV line: " + line);
                return null;
            }

            T instance;
            try {
                instance = clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                System.err.println("❌ No no-arg constructor found for " + clazz.getSimpleName());
                return null;
            } catch (Exception e) {
                System.err.println("❌ Failed to instantiate " + clazz.getSimpleName() + ": " + e.getMessage());
                return null;
            }

            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < headers.length; i++) {
                String header = headers[i];
                String value = values[i];

                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(header)) {
                        String validationError = validateFieldValue(field, value, clazz.getSimpleName());
                        if (validationError != null) {
                            System.err.println("❌ Validation error for " + clazz.getSimpleName() + " field " + header + ": " + validationError);
                            return null;
                        }
                        setFieldValue(instance, field, value);
                        break;
                    }
                }
            }

            // Validate the entire object using BaseService
            if (service != null) {
                String entityError = service.isValidEntity(instance);
                if (entityError != null) {
                    System.err.println("❌ Entity validation failed for " + clazz.getSimpleName() + ": " + entityError);
                    return null;
                }
            }

            return instance;
        } catch (Exception e) {
            System.err.println("❌ Error parsing CSV line '" + line + "': " + e.getMessage());
            return null;
        }
    }

    /**
     * Validates a field value before setting it.
     * @param field The field to validate.
     * @param value The string value to validate.
     * @param className The name of the class for error messages.
     * @return Validation error message or null if valid.
     */
    private static String validateFieldValue(Field field, String value, String className) {
        if (value.isEmpty()) {
            return null; // Null values are allowed
        }

        String fieldName = field.getName();
        Class<?> fieldType = field.getType();

        if (fieldName.equals("id")) {
            return Validator.validateUUID(value);
        }

        if (fieldType == String.class) {
            switch (className) {
                case "Customer":
                    switch (fieldName) {
                        case "email" -> {
                            return Validator.validateEmail(value);
                        }
                        case "phoneNo" -> {
                            return Validator.validatePhone(value);
                        }
                        case "firstName", "lastName", "address" -> {
                            return Validator.validateNonEmpty(value, fieldName);
                        }
                    }
                    break;
                case "Flight":
                    if (fieldName.equals("flightNo")) {
                        return Validator.validateFlightNumber(value);
                    } else if (fieldName.equals("depAirport") || fieldName.equals("arrAirport")) {
                        return Validator.validateAirportCode(value, fieldName);
                    }
                    break;
                case "Route":
                    if (fieldName.equals("name")) {
                        return Validator.validateRouteName(value);
                    }
                    break;
            }
        } else if (fieldType == LocalDate.class) {
            return Validator.validateDate(value, fieldName);
        } else if (fieldType == LocalDateTime.class) {
            return Validator.validateDateTime(value, fieldName);
        }

        return null;
    }

    /**
     * Sets a field value on an object, handling type conversion.
     * @param instance The object to set the field on.
     * @param field The field to set.
     * @param value The string value to parse.
     */
    private static void setFieldValue(Object instance, Field field, String value) throws IllegalAccessException {
        if (value.isEmpty()) {
            field.set(instance, null);
            return;
        }

        Class<?> fieldType = field.getType();
        try {
            if (fieldType == String.class) {
                field.set(instance, value);
            } else if (fieldType == LocalDate.class) {
                field.set(instance, LocalDate.parse(value, DATE_FORMATTER));
            } else if (fieldType == LocalDateTime.class) {
                field.set(instance, LocalDateTime.parse(value, DATETIME_FORMATTER));
            } else {
                System.err.println("❌ Unsupported field type: " + fieldType.getSimpleName() + " for field " + field.getName());
            }
        } catch (DateTimeParseException e) {
            System.err.println("❌ Invalid date format for field " + field.getName() + ": " + value);
        }
    }

    /**
     * Parses a CSV line, handling quoted fields and commas.
     * @param line The CSV line to parse.
     * @return An array of field values.
     */
    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (c == ',' && !inQuotes) {
                values.add(field.toString().trim());
                field = new StringBuilder();
                continue;
            }

            field.append(c);
        }

        // Add the last field
        values.add(field.toString().trim());
        return values.toArray(new String[0]);
    }
}