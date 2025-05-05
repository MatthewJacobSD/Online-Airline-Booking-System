package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Route;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class SRoute extends BaseService<Route> {
    public SRoute(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        super(scanner, fileHandler, consoleUI, "Route", "routes.csv", Route.class,
                Map.of()); // no references needed for routes
    }

    // the new route method overrides the abstract method in BaseService
    @Override
    protected Route addEntity() {
        consoleUI.showSectionHeader("Add New Route");
        String id = UUID.randomUUID().toString();
        consoleUI.showStatus("🛤️ Auto-generated ID: " + id);

        // Prompt for route name, with validation
        String name = null;
        while (name == null) {
            consoleUI.showStatus("🛤️ Enter route name (e.g., JFK-LAX): ");
            String input = scanner.nextLine().trim().toUpperCase();
            String error = Validator.validateRouteName(input);
            if (error != null) {
                consoleUI.showError(error);
                consoleUI.showStatus("🛤️ Try again? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    return null;
                }
                continue;
            }
            name = input;
        }

        return new Route(id, name);
    }

    // return null if the route is invalid
    @Override
    protected String validateEntity(Route route) {
        if (route == null) {
            return "Route cannot be null.";
        }

        String uuidError = Validator.validateUUID(route.getId());
        if (uuidError != null) {
            return uuidError;
        }

        if (route.getName() == null || route.getName().trim().isEmpty()) {
            return "Route name cannot be empty.";
        }

        return Validator.validateRouteName(route.getName());
    }
}