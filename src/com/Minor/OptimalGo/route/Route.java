package com.Minor.OptimalGo.route;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Route {
    public void routeType() {
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            System.out.println("""
                    press 1 for finding fastest route
                    press 2 for finding cheapest route
                    press 3 for finding most direct route
                    press 4 to go back to the main menu
                    """);
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();  // Consume newline left by nextInt()

                switch (choice) {
                    case 1 -> {
                        System.out.println("Finding fastest route...");
                        // Implement logic to find the fastest route
                    }
                    case 2 -> {
                        System.out.println("Finding cheapest route...");
                        // Implement logic to find the cheapest route
                    }
                    case 3 -> {
                        System.out.println("Finding most direct route...");
                        // Implement logic to find the most direct route
                    }
                    case 4 -> {
                        System.out.println("Going back to the main menu...");
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                // After processing a valid choice, wait for Enter to go back to the menu
                if (isRunning) {
                    System.out.println("Press Enter to go back to the route selection...");
                    sc.nextLine();  // Wait for Enter key press
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input and clear scanner buffer
            }
        }
    }
}
