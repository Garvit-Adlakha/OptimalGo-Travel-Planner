package com.Minor.OptimalGo.route;

import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Route {
    private final Graph graph;
    private final Dijkstra dij;
    private final BFS bfs;
    private final Scanner sc;

    public Route(Graph graph) {
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bfs = new BFS();
        this.sc = new Scanner(System.in);

        if (graph == null) {
            System.out.println("\033[1;31mGraph is null! Exiting...\033[0m"); // Red error
            System.exit(1); // Terminate if the graph isn't loaded
        }
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
                    case 1 -> findFastestRoute();
                    case 2 -> findCheapestRoute();
                    case 3 -> findMostDirectRoute();
                    case 4 -> {
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
                sc.nextLine(); // Clear invalid input from scanner buffer
            }
        }
    }

    private void displayMenu() {
        String cyanLine = "\033[1;36m==============================\033[0m";
        String yellowTitle = "\033[1;33m         ROUTE MENU           \033[0m";

        System.out.println(cyanLine); // Cyan separator
        System.out.println(yellowTitle); // Yellow title
        System.out.println(cyanLine); // Cyan separator again
        System.out.println("\033[1;35m" + // Purple text for the options
                "1. Embark on the fastest route \uD83C\uDFC3\n" +
                "2. Discover the cheapest route \uD83D\uDCB0\n" +
                "3. Take the most direct route \uD83D\uDEA9\n" +
                "4. Return to the main menu \u2B05\uFE0F\n" +
                "\033[0m"); // Reset color
    }

    private String[] getRouteInput() throws IllegalArgumentException {
        System.out.print("\033[1;34mEnter start city: \033[0m");
        String source = sc.nextLine().trim();

        System.out.print("\033[1;34mEnter end city: \033[0m");
        String endCity = sc.nextLine().trim();

        if (source.isEmpty()) {
            throw new IllegalArgumentException("Start city cannot be empty.");
        }

        if (endCity.isEmpty()) {
            throw new IllegalArgumentException("End city cannot be empty.");
        }

        return new String[]{source, endCity};
    }

    private void findFastestRoute() {
        try {
            String[] cities = getRouteInput();
            System.out.print("\033[1;36mCalculating the fastest route\033[0m");
            simulateProgress();  // Simulate progress with a single message
            dij.calculateShortestPath(graph, cities[0], cities[1]);

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while finding the fastest route: " + e.getMessage() + "\033[0m");
        }
    }

    private void findCheapestRoute() {
        try {
            String[] cities = getRouteInput();
            System.out.print("\033[1;36mCalculating the cheapest route\033[0m");
            simulateProgress();  // Simulate progress with a single message
            dij.calculateCheapestRoute(graph, cities[0], cities[1]);

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while finding the cheapest route: " + e.getMessage() + "\033[0m");
        }
    }

    private void findMostDirectRoute() {
        try {
            String[] cities = getRouteInput();
            System.out.print("\033[1;36mCalculating the most direct route\033[0m");
            simulateProgress();  // Simulate progress with a single message
            bfs.findMostDirectRoute(graph, cities[0], cities[1]);

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while finding the most direct route: " + e.getMessage() + "\033[0m");
        }
    }

    private void simulateProgress() {
        // Simulate a loading process
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500); // Simulate progress delay
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\033[1;32m Done!\033[0m"); // Green completion message
    }
}
