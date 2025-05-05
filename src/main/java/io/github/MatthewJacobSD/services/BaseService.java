package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.CSVHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public abstract class BaseService<T> {
    protected final Scanner scanner;
    protected final FileHandler fileHandler;
    protected final ConsoleUI consoleUI;
    protected final String entityName;
    protected final String csvFileName;
    protected final Class<T> entityClass;

    public BaseService(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI, String entityName, String csvFileName, Class<T> entityClass) {
        this.scanner = scanner;
        this.fileHandler = fileHandler;
        this.consoleUI = consoleUI;
        this.entityName = entityName;
        this.csvFileName = csvFileName;
        this.entityClass = entityClass;
    }

    // Abstract method to add a new entity
    protected abstract T addEntity();

    // Abstract method to validate an entity
    protected abstract String validateEntity(T entity);

    // Public method to validate an entity for external use
    public String isValidEntity(T entity) {
        return validateEntity(entity);
    }


    // Reads entity data
    public void read() {
        consoleUI.showSectionHeader("Read " + entityName + " Data");
        consoleUI.showStatus("📂 Enter file path (e.g., " + csvFileName + "): ");
        String path = scanner.nextLine().trim();

        consoleUI.showStatus("⏳ Loading " + entityName.toLowerCase() + " data...");
        String content = fileHandler.readFile(path);

        // Check content if is empty or none
        if (content != null && !content.isEmpty()) {
            consoleUI.showSectionHeader(entityName + " Data");
            List<T> objects = CSVHandler.fromCSV(content, entityClass, this);
            if (objects.isEmpty()) {
                consoleUI.showError("No valid " + entityName.toLowerCase() + " data found.");
            } else {
                objects.forEach(obj -> System.out.println("📋 " + obj));
            }
        } else {
            consoleUI.showError("No " + entityName.toLowerCase() + " data found or file is empty.");
        }
    }

    // Writes entity data
    public void write() {
        consoleUI.showSectionHeader("Save " + entityName + " Data");
        consoleUI.showStatus("📂 Enter save path (e.g., " + csvFileName + "): ");
        String path = scanner.nextLine().trim();

        consoleUI.showStatus("➕ Add " + entityName.toLowerCase() + "s (press enter after each, blank line to finish):");
        Set<T> entities = new LinkedHashSet<>(); // Use Set to prevent duplicates

        do { // validation loop per entity added
            T entity = addEntity();
            String validationError = validateEntity(entity);
            if (validationError != null) {
                consoleUI.showError(validationError);
                consoleUI.showStatus("➕ Try adding " + entityName.toLowerCase() + " again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    continue;
                }
                entity = addEntity();
                validationError = validateEntity(entity);
                if (validationError != null) {
                    consoleUI.showError(validationError + " Skipping this " + entityName.toLowerCase() + ".");
                    continue;
                }
            }
            entities.add(entity);
            consoleUI.showStatus("➕ Add another " + entityName.toLowerCase() + "? (y/n): ");
        } while (scanner.nextLine().trim().equalsIgnoreCase("y"));

        // Debug: Log entities to check for duplicates
        consoleUI.showStatus("📋 Entities to save: " + entities.size());
        entities.forEach(obj -> consoleUI.showStatus("• " + obj));

        consoleUI.showStatus("🔄 Append to existing file? (y/n, append adds to existing data): ");
        boolean append = scanner.nextLine().trim().equalsIgnoreCase("y");

        // Check for existing UUIDs when appending
        if (append) {
            String content = fileHandler.readFile(path);
            if (content != null && !content.isEmpty()) {
                List<T> existing = CSVHandler.fromCSV(content, entityClass, this);
                List<String> existingIds = extractIds(existing.stream());
                List<String> newIds = extractIds(entities.stream());

                if (existingIds.stream().anyMatch(newIds::contains)) {
                    consoleUI.showWarning("Some IDs already exist in " + path + ". This may create duplicates.");
                    if (consoleUI.confirmActionChoice("Continue with append?")) {
                        consoleUI.showStatus("⏹️ Operation cancelled by user");
                        return;
                    }
                }
            }
        }

        consoleUI.showStatus("⏳ Saving " + entityName.toLowerCase() + " data...");
        boolean success = fileHandler.writeCSV(path, new ArrayList<>(entities), append);

        if (success) {
            consoleUI.showSuccess(entityName + " data saved successfully!");
            consoleUI.showSectionHeader("Saved " + entityName + " Data");
            entities.forEach(obj -> System.out.println("📋 " + obj));
            consoleUI.showStatus("📄 Check " + path + " for saved data");
        } else {
            consoleUI.showError("Failed to save " + entityName.toLowerCase() + " data.");
        }
    }

    // Extracts IDs from a stream of entities
    private List<String> extractIds(Stream<T> stream) {
        return stream
                .map(obj -> {
                    try {
                        Field idField = obj.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
                        return (String) idField.get(obj);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    // Entity data menu
    public void data() {
        while (true) {
            consoleUI.showSectionHeader(entityName + " Data Menu");
            System.out.println("1. Read " + entityName.toLowerCase() + " data");
            System.out.println("2. Write " + entityName.toLowerCase() + " data");
            System.out.println("3. Return to main menu");
            consoleUI.showStatus("🔄 Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                consoleUI.showError("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> read();
                case 2 -> write();
                case 3 -> { return; }
                default -> consoleUI.showError("Invalid choice! Please try again.");
            }
        }
    }
}