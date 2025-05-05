package io.github.MatthewJacobSD;

import io.github.MatthewJacobSD.utils.ConsoleUI;
import io.github.MatthewJacobSD.utils.FileHandler;
import io.github.MatthewJacobSD.utils.ServiceRouter;

import java.util.Scanner;

public class AirlineOnlineBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleUI consoleUI = new ConsoleUI(scanner);
        FileHandler fileHandler = new FileHandler(consoleUI);
        ServiceRouter router = new ServiceRouter(scanner, fileHandler, consoleUI);

        // Show a welcome message
        while (true) {
            consoleUI.showMainMenu();
            int choice = consoleUI.getUserChoice();

            if (choice == 5) {
                consoleUI.printExitMessage();
                scanner.close();
                break;
            }
            router.route(choice);
        }

    }
}