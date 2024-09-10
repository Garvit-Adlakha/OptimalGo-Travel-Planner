package com.Minor.OptimalGo.route;

import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Route {
    private Graph graph;
    private final Dijkstra dij;
    private final BFS bfs;
    private final Scanner sc;

    public Route(Graph graph) {
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bfs = new BFS();
        this.sc = new Scanner(System.in);

        if (graph == null) {
            System.out.println("Graph is null!");
            System.exit(1); // Terminate if the graph isn't loaded
        }
    }

    public void routeType() {
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();

            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Clear buffer

                switch (choice) {
                    case 1 -> findFastestRoute();
                    case 2 -> findCheapestRoute();
                    case 3 -> findMostDirectRoute();
                    case 4 -> {
                        System.out.println("Going back to the main menu...");
                        isRunning = false; // Exit loop to return to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please enter a valid option.");
                }

                if (isRunning) {
                    System.out.println("Press Enter to return to the route selection menu...");
                    sc.nextLine();  // Wait for the Enter key press
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear invalid input from scanner buffer
            }
        }

        // Close the scanner when done
        sc.close();
    }

    private void displayMenu() {
        System.out.println("""
                Press 1 for finding the fastest route
                Press 2 for finding the cheapest route
                Press 3 for finding the most direct route
                Press 4 to go back to the main menu
                """);
    }

    private String[] getRouteInput() {
        System.out.print("Enter start city: ");
        String source = sc.nextLine().trim();

        System.out.print("Enter end city: ");
        String endCity = sc.nextLine().trim();

        return new String[]{source, endCity};
    }

    private void findFastestRoute() {
        try {
            String[] cities = getRouteInput();
            if (cities[0].isEmpty() || cities[1].isEmpty()) {
                throw new IllegalArgumentException("City names cannot be empty.");
            }
            dij.calculateShortestPath(graph, cities[0], cities[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while finding the fastest route: " + e.getMessage());
        }
    }

    private void findCheapestRoute() {
        try {
            String[] cities = getRouteInput();
            if (cities[0].isEmpty() || cities[1].isEmpty()) {
                throw new IllegalArgumentException("City names cannot be empty.");
            }
            dij.calculateCheapestRoute(graph, cities[0], cities[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while finding the cheapest route: " + e.getMessage());
        }
    }

    private void findMostDirectRoute() {
        try {
            String[] cities = getRouteInput();
            if (cities[0].isEmpty() || cities[1].isEmpty()) {
                throw new IllegalArgumentException("City names cannot be empty.");
            }
            bfs.findMostDirectRoute(graph, cities[0], cities[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while finding the most direct route: " + e.getMessage());
        }
    }
}
