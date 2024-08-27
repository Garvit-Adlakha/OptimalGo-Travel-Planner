package com.Minor.OptimalGo.customization;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customization {
    public void addCustomization() {
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);
        while (isRunning) {
            System.out.println("""
                    press 1 for adding city
                    press 2 for adding transport
                    press 3 to go back to the main menu
                    """);
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline left by nextInt()

                switch (choice) {
                    case 1 -> {
                        System.out.println("Adding city...");
                        // Implement logic to add city
                    }
                    case 2 -> {
                        System.out.println("Adding transport...");
                        // Implement logic to add transport
                    }
                    case 3 -> {
                        System.out.println("Going back to the main menu...");
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                // After processing a valid choice, wait for Enter to go back to the menu
                if (isRunning) {
                    System.out.println("Press Enter to go back to the customization menu...");
                    sc.nextLine();  // Wait for Enter key press
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input and clear scanner buffer
            }
        }
    }

    public void removeCustomization() {
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            System.out.println("""
                    press 1 for removing city
                    press 2 for removing transport
                    press 3 to go back to the main menu
                    """);
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline left by nextInt()

                switch (choice) {
                    case 1 -> {
                        System.out.println("Removing city...");
                        // Implement logic to remove city
                    }
                    case 2 -> {
                        System.out.println("Removing transport...");
                        // Implement logic to remove transport
                    }
                    case 3 -> {
                        System.out.println("Going back to the main menu...");
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                // After processing a valid choice, wait for Enter to go back to the menu
                if (isRunning) {
                    System.out.println("Press Enter to go back to the customization menu...");
                    sc.nextLine();  // Wait for Enter key press
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input and clear scanner buffer
            }
        }
    }
}