package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.customization.Customization;
import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.route.Route;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    public Graph graph;
    private Scanner scanner;
    Route route = new Route();
    Customization customization = new Customization();

    public Driver() {
        this.scanner = new Scanner(System.in);
    }

    public void repl() {
        boolean running = true;

        // First screen - waits for Enter key press to continue
        while (true) {
            System.out.println("Press Enter to Start");
            String input = scanner.nextLine();
            System.out.println();
            if (input.isEmpty()) {
                System.out.println("Enter key was pressed!");
                LoadGraph loadGraph=new LoadGraph();
                graph=loadGraph.getGraph();
                System.out.println("Press Enter to Start");
                String inp = scanner.nextLine();
                break;
            }
        }

        // Second screen with options and go back functionality
        while (running) {
            System.out.println("""
                    press 1 for finding route
                    press 2 for adding customizations
                    press 3 for removing customization
                    press 4 for listing
                    press 5 for showing graph
                    press 6 for save data
                    press 7 for help
                    press 8 for exit
                    """);

            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character left after nextInt()

                switch (choice) {
                    case 1 -> route.routeType(graph);
                    case 2 -> customization.addCustomization();
                    case 3 -> customization.removeCustomization();
                    case 4 -> listing();
                    case 5 -> showGraph();
                    case 6 -> saveData();
                    case 7 -> help();
                    case 8 -> {
                        System.out.println("Exiting program...");
                        running = false;

                    }
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }

                // Option to go back to the main menu or exit after each action
                if (running) {
                    System.out.println("Press Enter to go back to the main menu...");
                    scanner.nextLine();
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input and clear scanner buffer
            }
        }
    }

    private void listing() {
        System.out.println("Listing all...");
        // Implement listing logic
    }

    private void showGraph() {
        System.out.println("Showing graph...");
        // Implement show graph logic
    }

    private void saveData() {
        System.out.println("Saving data... (Soon to be implemented)");
        // Implement save data logic
    }

    private void help() {
        System.out.println("""
                Help menu:
                - 1: Find route.
                - 2: Add customizations.
                - 3: Remove customizations.
                - 4: Listing.
                - 5: Show the graph.
                - 6: Save data.
                - 7: Show this help menu.
                - 8: Exit the program.
                """);
    }
}
