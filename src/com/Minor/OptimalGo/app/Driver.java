package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.customization.Customization;
import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.route.Route;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    private Graph graph;
    private final Scanner scanner;
    private Route route;
    private final Customization customization = new Customization();

    public Driver() {
        this.scanner = new Scanner(System.in);
    }

    public void repl() {
        boolean running = true;

        // First screen - waits for Enter key press to continue
        waitForEnterKey();

        // Load graph after pressing Enter
        LoadGraph loadGraph = new LoadGraph();
        graph = loadGraph.getGraph();
        route = new Route(graph);  // Initialize the Route class with the loaded graph

        // Main menu screen
        while (running) {
            displayMainMenu();
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                handleUserChoice(choice);
                if (choice != 8) {
                    waitForEnterKey(); // Wait for Enter to return to main menu
                } else {
                    running = false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        closeProgram(); // Close resources before exiting
    }

    private void waitForEnterKey() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private void displayMainMenu() {
        System.out.println("""
                Choose an option:
                1. Find a route
                2. Add customizations
                3. Remove customizations
                4. Listing
                5. Show graph
                6. Save data
                7. Help
                8. Exit
                """);
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> route.routeType();
            case 2 -> customization.addCustomization();
            case 3 -> customization.removeCustomization();
            case 4 -> listing();
            case 5 -> showGraph();
            case 6 -> saveData();
            case 7 -> help();
            case 8 -> System.out.println("Exiting program...");
            default -> System.out.println("Invalid choice. Please select a number between 1 and 8.");
        }
    }

    private void listing() {
        System.out.println("Listing all items...");
        // Implement listing logic here
    }

    private void showGraph() {
        System.out.println("Displaying the graph...");
        // Implement show graph logic here
    }

    private void saveData() {
        System.out.println("Saving data... (To be implemented)");
        // Implement save data logic here
    }

    private void help() {
        System.out.println("""
                Help menu:
                1. Find route: Allows you to find the fastest, cheapest, or most direct route.
                2. Add customizations: Add custom preferences to your travel.
                3. Remove customizations: Remove previously added customizations.
                4. Listing: View all available listings.
                5. Show graph: Display the current travel graph.
                6. Save data: Save the current state of the graph and routes.
                7. Help: Show this help menu.
                8. Exit: Close the application.
                """);
    }

    private void closeProgram() {
        System.out.println("Closing program and releasing resources...");
        scanner.close(); // Close the scanner to release system resources
    }
}
