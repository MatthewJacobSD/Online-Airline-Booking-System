package io.github.MatthewJacobSD.utils;

import io.github.MatthewJacobSD.models.*;
import java.lang.reflect.Field;
import java.util.*;

public class ReferenceValidator {
    private final FileHandler fileHandler;
    private final ConsoleUI consoleUI;
    private final Map<String, String> referenceFilePaths;
    private final Map<String, List<String>> idCache = new HashMap<>();

    public ReferenceValidator(FileHandler fileHandler, ConsoleUI consoleUI,
                              Map<String, String> referenceFilePaths) {
        this.fileHandler = fileHandler;
        this.consoleUI = consoleUI;
        this.referenceFilePaths = referenceFilePaths;
    }

    public boolean validateReference(String referenceType, String idToCheck) {
        if (!referenceFilePaths.containsKey(referenceType)) {
            consoleUI.showError("No reference file configured for " + referenceType);
            return false;
        }

        if (!idCache.containsKey(referenceType)) {
            loadReferenceIds(referenceType);
        }

        boolean exists = idCache.get(referenceType).contains(idToCheck);
        if (!exists) {
            consoleUI.showError(referenceType + " ID " + idToCheck + " not found in " +
                    referenceFilePaths.get(referenceType));
        }
        return exists;
    }

    private void loadReferenceIds(String referenceType) {
        String filePath = referenceFilePaths.get(referenceType);
        String content = fileHandler.readFile(filePath);

        if (content == null || content.isEmpty()) {
            consoleUI.showError("Could not load reference file: " + filePath);
            idCache.put(referenceType, Collections.emptyList());
            return;
        }

        try {
            Class<?> refClass = getReferenceClass(referenceType);
            List<?> items = CSVHandler.fromCSV(content, refClass, null);
            List<String> ids = extractIdsFromItems(items);
            idCache.put(referenceType, ids);
        } catch (Exception e) {
            consoleUI.showError("Error processing " + referenceType + " file: " + e.getMessage());
            idCache.put(referenceType, Collections.emptyList());
        }
    }

    private Class<?> getReferenceClass(String referenceType) {
        return switch (referenceType) {
            case "routes" -> Route.class;
            case "customers" -> Customer.class;
            case "flights" -> Flight.class;
            default -> throw new IllegalArgumentException("Unknown reference type: " + referenceType);
        };
    }

    private List<String> extractIdsFromItems(List<?> items) {
        return items.stream()
                .map(item -> {
                    try {
                        Field idField = item.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
                        return (String) idField.get(item);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}