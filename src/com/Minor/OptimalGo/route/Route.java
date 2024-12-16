package com.Minor.OptimalGo.route;
import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.BellmanFord;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;


// Class to handle user interactions and routing options
public class Route {
    private final RouteService routeService;
    private final Scanner sc;

    public Route(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        this.routeService = new RouteService(graph);
        this.sc = new Scanner(System.in);
    }

    public void routeType() {
        boolean isRunning = true;

        while (isRunning) {
            displayMenu();

            try {
                System.out.print("\033[1;34mEnter your choice: \033[0m"); // Blue prompt
                int choice = sc.nextInt();
                sc.nextLine(); // Clear buffer

                switch (choice) {
                    case 1 -> {
                        String[] cities = routeService.getRouteInput(); // Get user input for cities
                        routeService.findFastestRoute(cities); // Call method directly with input
                    }
                    case 2 -> {
                        String[] cities = routeService.getRouteInput();
                        routeService.findCheapestRoute(cities);
                    }
                    case 3 -> {
                        String[] cities = routeService.getRouteInput();
                        routeService.findMostDirectRoute(cities);
                    }
                    case 4 -> routeService.compareFastestRoutes();
                    case 5 -> routeService.compareCheapestRoutes();
                    case 6 -> {
                        System.out.println("\033[1;36mGoing back to the main menu...\033[0m"); // Cyan message
                        isRunning = false; // Exit loop to return to the main menu
                    }
                    default -> System.out.println("\033[1;31mInvalid choice. Please enter a valid option.\033[0m"); // Red error
                }

                if (isRunning) {
                    System.out.println("\033[1;32mPress Enter to return to the route selection menu...\033[0m"); // Green message
                    sc.nextLine();  // Wait for the Enter key press
                }

            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInvalid input. Please enter a valid number.\033[0m"); // Red error
                sc.nextLine();
            }
        }
    }

    private void displayMenu() {
        System.out.println("\033[1;33mSelect Route Type:\033[0m"); // Yellow header
        System.out.println("1. Find Fastest Route");
        System.out.println("2. Find Cheapest Route");
        System.out.println("3. Find Most Direct Route");
        System.out.println("4. Compare Fastest Routes");
        System.out.println("5. Compare Cheapest Routes");
        System.out.println("6. Go Back");
    }
}
