package com.Minor.OptimalGo.route;
import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.BellmanFord;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

// Service class to handle the route logic and runtime measurements
class RouteService {
    private final Graph graph;
    private final Dijkstra dij;
    private final BellmanFord bellmanFord;
    private final BFS bfs;
    private final Scanner sc;

    public RouteService(Graph graph) {
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bellmanFord = new BellmanFord();
        this.bfs = new BFS();
        this.sc = new Scanner(System.in);
    }
    public void findFastestRoute(String[] cities) {
        executeWithRuntime("fastest", () -> dij.calculateWithPriorityQueue(graph, cities[0], cities[1], true));
    }

    public void findCheapestRoute(String[] cities) {
        executeWithRuntime("cheapest", () -> dij.calculateWithRadixHeap(graph, cities[0], cities[1], false));
    }

    public void findMostDirectRoute(String[] cities) {
        executeWithRuntime("most direct", () -> bfs.findMostDirectRoute(graph, cities[0], cities[1]));
    }
    // Functional interface to decouple route finding from the user interface
    @FunctionalInterface
    interface RouteFinder {
        void findRoute(String[] cities);
    }

    public void executeWithRuntime(String routeType, Runnable routeFunction) {
        try {
            System.out.print("\033[1;36mCalculating the " + routeType + " route\033[0m");
            simulateProgress();

            long startTime = System.nanoTime();
            routeFunction.run();
            long endTime = System.nanoTime();

            long runtimeInMillis = (endTime - startTime) / 1_000_000;
            long runtimeInNanos = endTime - startTime;

            if (runtimeInMillis == 0) {
                System.out.println("\033[1;32m" + capitalizeFirstLetter(routeType) + " route completed in " + runtimeInNanos + " ns\033[0m");
            } else {
                System.out.println("\033[1;32m" + capitalizeFirstLetter(routeType) + " route completed in " + runtimeInMillis + " ms\033[0m");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while finding the " + routeType + " route: " + e.getMessage() + "\033[0m");
        }
    }

    public void compareFastestRoutes() {
        try {
            String[] cities = getRouteInput();
            System.out.println("\033[1;36mComparing fastest routes\033[0m");

            long runtimePriorityQueue = calculateRouteRuntime(() -> dij.calculateWithPriorityQueue(graph, cities[0], cities[1], true), "PriorityQueue");
            long runtimeRadixHeap = calculateRouteRuntime(() -> dij.calculateWithRadixHeap(graph, cities[0], cities[1], true), "RadixHeap");

            printComparisonResults(new String[]{"ID", "Algorithm", "Runtime (ms)"}, new String[][]{
                    {"1", "PriorityQueue", String.valueOf(runtimePriorityQueue)},
                    {"2", "RadixHeap", String.valueOf(runtimeRadixHeap)}
            });

            determineFasterAlgorithm(runtimePriorityQueue, runtimeRadixHeap, "fastest");
        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
        }
    }

    public void compareCheapestRoutes() {
        try {
            String[] cities = getRouteInput();
            System.out.println("\033[1;36mComparing cheapest routes\033[0m");

            long runtimeDijkstra = calculateRouteRuntime(() -> dij.calculateWithPriorityQueue(graph, cities[0], cities[1], false), "Dijkstra");
            long runtimeBellmanFord = calculateRouteRuntime(() -> bellmanFord.calculateCheapestRoute(graph, cities[0], cities[1]), "Bellman-Ford");

            printComparisonResults(new String[]{"ID", "Algorithm", "Runtime (ms)"}, new String[][]{
                    {"1", "Dijkstra", String.valueOf(runtimeDijkstra)},
                    {"2", "Bellman-Ford", String.valueOf(runtimeBellmanFord)}
            });

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
        }
    }

    private void printComparisonResults(String[] headers, String[][] rows) {
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }
        // Print headers
        System.out.printf("\033[1;34m\033[1m"); // Bold blue
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + (columnWidths[i]) + "s | ", headers[i]);
        }
        System.out.println("\033[0m"); // Reset color after printing headers

        // Print an underline
        System.out.println("\033[1;34m" + "-".repeat(Arrays.stream(columnWidths).map(w -> w).sum() + (headers.length - 1) * 3) + "\033[0m");
        // Print each row with purple color
        for (String[] row : rows) {
            System.out.printf("\033[1;35m"); // Purple color for rows
            for (int i = 0; i < row.length; i++) {
                if (i > 0) {
                    System.out.printf("%" + (columnWidths[i]) + "s | ", row[i]);
                } else {
                    System.out.printf("%-" + (columnWidths[i]) + "s | ", row[i]);
                }
            }
            System.out.println("\033[0m"); // Reset color after each row
        }
        System.out.println("\033[1;34m" + "-".repeat(Arrays.stream(columnWidths).map(w -> w).sum() + (headers.length - 1) * 3) + "\033[0m");
    }

    private void determineFasterAlgorithm(long runtime1, long runtime2, String routeType) {
        if (runtime2 > runtime1) {
            System.out.println("\033[1;32mPriority Queue Dijkstra's is faster\033[0m");
        } else if (runtime2 < runtime1) {
            System.out.println("\033[1;32mRadix Heap Dijkstra's is faster\033[0m");
        } else {
            System.out.println("\033[1;32mBoth are equally fast\033[0m");
        }
    }

    private long calculateRouteRuntime(Runnable routeFunction, String algorithm) {
        simulateProgress();
        long startTime = System.nanoTime();
        routeFunction.run();
        long endTime = System.nanoTime();
        long runtimeInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("\033[1;32m" + algorithm + " route completed in " + runtimeInMillis + " ms\033[0m");
        return runtimeInMillis;
    }

    public String[] getRouteInput() {
        String source = "";
        String destination = "";

        while (true) {
            try {
                System.out.print("\033[1;34mEnter the starting city: \033[0m");
                source = sc.nextLine().trim().toLowerCase();
                source=capitalizeFirstLetter(source);
                if(source.length()<=3){
                    source = source.toUpperCase();
                }
                System.out.print("\033[1;34mEnter the destination city: \033[0m");
                destination = sc.nextLine().trim().toLowerCase();
                destination=capitalizeFirstLetter(destination);
                if (destination.length()<=3){
                   destination= destination.toUpperCase();
                }
                if (source.isEmpty() || destination.isEmpty()) {
                    throw new IllegalArgumentException("City names cannot be empty. Please enter valid source and destination.");
                }

                if (!graph.containsCity(source) || !graph.containsCity(destination)) {
                    throw new IllegalArgumentException("Invalid city names. Please enter valid source and destination.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\033[1;31m" + e.getMessage() + "\033[0m"); // Print error message in red
            }
        }
        return new String[]{source, destination};
    }


    private void simulateProgress() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String capitalizeFirstLetter(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}

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
