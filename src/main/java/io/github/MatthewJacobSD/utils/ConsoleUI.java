package io.github.MatthewJacobSD.utils;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMainMenu() {
        System.out.println("\n✈️ Airline Online Booking System");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. Manage Customer Data");
        System.out.println("2. Manage Booking Data");
        System.out.println("3. Manage Flight Data");
        System.out.println("4. Manage Route Data");
        System.out.println("5. Exit");
        System.out.print("🔄 Choose an option: ");
    }

    public int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            showError("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public void printExitMessage() {
        System.out.println("👋 Exiting system. Goodbye!");
    }

    public void showSectionHeader(String title) {
        System.out.println("\n" + title);
        System.out.println("―".repeat(title.length()));
    }

    public void showStatus(String message) {
        System.out.println("• " + message);
    }

    public void showProgress(int count) {
        if (count % 100 == 0) {
            System.out.print("⏳ Processed " + count + " lines\r");
        }
    }

    public void showSuccess(String message) {
        System.out.println("✓ " + message);
    }

    public void showWarning(String s) {
        System.out.println("⚠️  " + s);
    }

    public void showError(String message) {
        System.err.println("✗ " + message);
    }

    public void showPreview(String[] lines) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━");
        for (String line : lines) {
            System.out.println("│ " + line);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━");
    }

    public boolean confirmActionChoice(String prompt) {
        System.out.print(prompt + " (y/n): ");
        return !scanner.nextLine().trim().equalsIgnoreCase("y");
    }
}