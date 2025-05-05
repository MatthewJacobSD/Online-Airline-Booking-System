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

    public static String toCSV(List<?> objects) {
        if (objects == null || objects.isEmpty()) {
            return "";
        }
        StringBuilder csvContent = new StringBuilder();
        processObject(objects.getFirst(), csvContent, true);
        objects.forEach(obj -> processObject(obj, csvContent, false));
        return csvContent.toString();
    }

    private static void processObject(Object obj, StringBuilder csvContent, boolean includeHeader) {
        try {
            Class<?> objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();

            if (includeHeader) {
                appendFields(fields, csvContent, true, null);
            } else {
                appendFields(fields, csvContent, false, obj);
            }
        } catch (Exception e) {
            System.err.println("❌ Error processing object: " + e.getMessage());
        }
    }

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
        String strValue = value.toString().replace("\"", "\"\"");
        if (strValue.contains(",") || strValue.contains("\"") || strValue.contains("\n")) {
            return "\"" + strValue + "\"";
        }
        return strValue;
    }

    public static <T> List<T> fromCSV(String csvContent, Class<T> clazz, BaseService<T> service) {
        List<T> objects = new ArrayList<>();
        if (csvContent == null || csvContent.trim().isEmpty()) {
            return objects;
        }

        String[] lines = csvContent.split("\n");
        if (lines.length < 1) {
            return objects;
        }

        String[] headers = parseCSVLine(lines[0]);
        Field[] fields = clazz.getDeclaredFields();

        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).toList();
        if (!Arrays.stream(headers).allMatch(fieldNames::contains)) {
            System.err.println("❌ CSV headers do not match class fields. Expected: " + fieldNames + ", Found: " + Arrays.toString(headers));
            return objects;
        }

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

    private static String validateFieldValue(Field field, String value, String className) {
        if (value.isEmpty()) {
            return null;
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

        // Remove any empty trailing fields
        while (!values.isEmpty() && values.getLast().isEmpty()) {
            values.removeLast();
        }

        return values.toArray(new String[0]);
    }

    public static String[] parseHeaders(String headerLine) throws Exception {
        return parseCSVLine(headerLine);
    }
}