package io.github.MatthewJacobSD;

import io.github.MatthewJacobSD.utils.*;
import java.util.Scanner;

public class AirlineOnlineBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleUI consoleUI = new ConsoleUI(scanner);
        FileHandler fileHandler = new FileHandler(consoleUI);
        ServiceRouter router = new ServiceRouter(scanner, fileHandler, consoleUI);

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