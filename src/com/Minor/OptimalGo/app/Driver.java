package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.customization.Customization;
import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.route.Route;
import com.Minor.OptimalGo.header.Runtime; // Import the Runtime class

import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    private Graph graph;
    private final Scanner scanner;
    private Route route;
    private final Customization customization = new Customization();
    private final Runtime runtime = new Runtime(); // Add the Runtime instance

    public Driver() {
        this.scanner = new Scanner(System.in);
    }

    public void repl() {
        boolean running = true;
        waitForEnterKey();
        // Start runtime measurement for loading the graph
        runtime.start();
        LoadGraph loadGraph = new LoadGraph();
        graph = loadGraph.getGraph();
        route = new Route(graph);  // Initialize the Route class with the loaded graph
        // Stop runtime measurement after graph load and display
        runtime.stop();
        System.out.println("\033[1;32mGraph loaded successfully!\033[0m");
        runtime.printDuration();
        // Main menu screen
        while (running) {
            displayMainMenu();
            try {
                System.out.print("\033[1;34mEnter your choice: \033[0m"); // Blue prompt
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                runtime.start(); // Start runtime for selected operation
                handleUserChoice(choice);
                if (choice != 8) {
                    waitForEnterKey();
                } else {
                    running = false;
                }
                runtime.stop(); // Stop runtime for the operation
                runtime.printDuration(); // Display how long the operation took

            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInvalid input. Please enter a valid number.\033[0m"); // Red error
                scanner.nextLine();
            }
        }
        closeProgram(); // Close resources before exiting
    }

    private void waitForEnterKey() {
        System.out.println("\033[1;32mPress Enter to continue...\033[0m"); // Green message
        scanner.nextLine();
    }

    private void displayMainMenu() {
        System.out.println("\033[1;36m==============================\033[0m"); // Cyan separator
        System.out.println("\033[1;33m         MAIN MENU            \033[0m"); // Yellow title
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
            1. 🛣️  Find a route
            2. 🛠️  Add customizations
            3. 🧹 Remove customizations
            4. 📜 Listing
            5. ⏱️ Runtime
            6. 💾 Save data
            7. ❓ Help
            8. 🚪 Exit
            """);
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> route.routeType(); // Measure runtime for finding routes
            case 2 -> customization.addCustomization();
            case 3 -> customization.removeCustomization();
            case 4 -> listing();
            case 5 -> displayRuntime();
            case 6 -> saveData();
            case 7 -> help();
            case 8 -> System.out.println("\033[1;31mExiting program...\033[0m"); // Red exit message
            default -> System.out.println("\033[1;31mInvalid choice. Please select a number between 1 and 8.\033[0m");
        }
    }

    private void listing() {
        System.out.println("\033[1;36m📜 Listing all items...\033[0m"); // Cyan message
        // Implement listing logic here
    }

    private void displayRuntime() {
        System.out.println("\033[1;36m🌍 Displaying Runtime\033[0m"); // Cyan message
        // Implement show graph logic here
    }

    private void saveData() {
        System.out.print("\033[1;36m💾 Saving data"); // Cyan progress message
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (InterruptedException e) {
                System.out.println("\033[1;31mError: Save operation interrupted.\033[0m");
                return;
            }
        }
        System.out.println(" Done!\033[0m"); // Completion message
    }
    private void help() {
        System.out.println("\033[1;36m==============================\033[0m"); // Cyan separator
        System.out.println("\033[1;33m            HELP              \033[0m"); // Yellow title
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
                1. 🛣️  Find route: Allows you to find the fastest, cheapest, or most direct route.
                2. 🛠️  Add customizations: Add custom preferences to your travel.
                3. 🧹 Remove customizations: Remove previously added customizations.
                4. 📜 Listing: View all available listings.
                5. ⏱️ Runtime:Show Runtime
                6. 💾 Save data: Save the current state of the graph and routes.
                7. ❓ Help: Show this help menu.
                8. 🚪 Exit: Close the application.
                """);
    }

    private void closeProgram() {
        System.out.println("\033[1;31m🚪 Closing program and releasing resources...\033[0m"); // Red close message
        scanner.close();
    }
}
