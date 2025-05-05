package io.github.MatthewJacobSD.services;

import io.github.MatthewJacobSD.models.Customer;
import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.Validator;

import java.util.Scanner;
import java.util.UUID;

public class SCustomer extends BaseService<Customer> {
    public SCustomer(Scanner scanner, FileHandler fileHandler, ConsoleUI consoleUI) {
        super(scanner, fileHandler, consoleUI, "Customer", "customers.csv", Customer.class);
    }

    // the new customer method overrides the abstract method in BaseService
    @Override
    protected Customer addEntity() {
        consoleUI.showSectionHeader("Add New Customer");
        String id = UUID.randomUUID().toString();
        consoleUI.showStatus("📛 Auto-generated ID: " + id);

        consoleUI.showStatus("📛 Enter first name: ");
        String firstName = scanner.nextLine().trim();

        consoleUI.showStatus("📛 Enter last name: ");
        String lastName = scanner.nextLine().trim();

        consoleUI.showStatus("📛 Enter email: ");
        String email = scanner.nextLine().trim();

        consoleUI.showStatus("📛 Enter phone number: ");
        String phoneNo = scanner.nextLine().trim();

        consoleUI.showStatus("📛 Enter address: ");
        String address = scanner.nextLine().trim();

        return new Customer(id, firstName, lastName, email, phoneNo, address);
    }

    // return null if the customer is invalid
    @Override
    protected String validateEntity(Customer customer) {
        // Validate UUID
        String uuidError = Validator.validateUUID(customer.getId());
        if (uuidError != null) {
            return uuidError;
        }

        // Validate non-empty fields
        String firstNameError = Validator.validateNonEmpty(customer.getFirstName(), "First name");
        if (firstNameError != null) {
            return firstNameError;
        }

        String lastNameError = Validator.validateNonEmpty(customer.getLastName(), "Last name");
        if (lastNameError != null) {
            return lastNameError;
        }

        String emailError = Validator.validateNonEmpty(customer.getEmail(), "Email");
        if (emailError != null) {
            return emailError;
        }

        String phoneError = Validator.validateNonEmpty(customer.getPhoneNo(), "Phone number");
        if (phoneError != null) {
            return phoneError;
        }

        String addressError = Validator.validateNonEmpty(customer.getAddress(), "Address");
        if (addressError != null) {
            return addressError;
        }

        // Validate email format
        String emailFormatError = Validator.validateEmail(customer.getEmail());
        if (emailFormatError != null) {
            return emailFormatError;
        }

        // Validate a phone number format
        return Validator.validatePhone(customer.getPhoneNo());
    }
}