package com.Minor.OptimalGo.customization;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customization {

    public void addCustomization() {
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            displayAddMenu();

            try {
                System.out.print("\033[1;34mEnter your choice: \033[0m");  // Blue prompt
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline left by nextInt()

                switch (choice) {
                    case 1 -> {
                        System.out.println("\033[1;32mAdding city...\033[0m");  // Green message
                        // Implement logic to add city
                    }
                    case 2 -> {
                        System.out.println("\033[1;32mAdding transport...\033[0m");  // Green message
                        // Implement logic to add transport
                    }
                    case 3 -> {
                        System.out.println("\033[1;36mGoing back to the main menu...\033[0m");  // Cyan message
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");  // Red error message
                }

                if (isRunning) {
                    System.out.println("\033[1;33mPress Enter to go back to the customization menu...\033[0m");  // Yellow prompt
                    sc.nextLine();  // Wait for Enter key press
                }

            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInvalid input. Please enter a number.\033[0m");  // Red error
                sc.nextLine();  // Clear invalid input
            }
        }
    }

    public void removeCustomization() {
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            displayRemoveMenu();

            try {
                System.out.print("\033[1;34mEnter your choice: \033[0m");  // Blue prompt
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline left by nextInt()

                switch (choice) {
                    case 1 -> {
                        System.out.println("\033[1;31mRemoving city...\033[0m");  // Red action
                        // Implement logic to remove city
                    }
                    case 2 -> {
                        System.out.println("\033[1;31mRemoving transport...\033[0m");  // Red action
                        // Implement logic to remove transport
                    }
                    case 3 -> {
                        System.out.println("\033[1;36mGoing back to the main menu...\033[0m");  // Cyan message
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");  // Red error message
                }

                if (isRunning) {
                    System.out.println("\033[1;33mPress Enter to go back to the customization menu...\033[0m");  // Yellow prompt
                    sc.nextLine();  // Wait for Enter key press
                }

            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInvalid input. Please enter a number.\033[0m");  // Red error
                sc.nextLine();  // Clear invalid input
            }
        }
    }

    private void displayAddMenu() {
        System.out.println("\033[1;36m==============================\033[0m");  // Cyan separator
        System.out.println("\033[1;33m       ADD CUSTOMIZATION      \033[0m");  // Yellow title
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
                Press 1 for adding city
                Press 2 for adding transport
                Press 3 to go back to the main menu
                """);
    }

    private void displayRemoveMenu() {
        System.out.println("\033[1;36m==============================\033[0m");  // Cyan separator
        System.out.println("\033[1;33m    REMOVE CUSTOMIZATION      \033[0m");  // Yellow title
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
                Press 1 for removing city
                Press 2 for removing transport
                Press 3 to go back to the main menu
                """);
    }
}
