package com.Minor.OptimalGo.route;

import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.BellmanFord;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;

@FunctionalInterface
interface RouteFinder {
    void findRoute(String[] cities);
}

public class Route {
    private final Graph graph;
    private final Dijkstra dij;
    private final BellmanFord bellmanFord;
    private final BFS bfs;
    private final Scanner sc;

    public Route(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bfs = new BFS();
        this.bellmanFord = new BellmanFord();
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
                    case 1 -> measureRuntime("fastest", this::findFastestRoute);
                    case 2 -> measureRuntime("cheapest", this::findCheapestRoute);
                    case 3 -> measureRuntime("most direct", this::findMostDirectRoute);
                    case 4 -> compareFastestRoutes();
                    case 5 -> compareCheapestRoutes();
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
                "4. Compare fastest routes (PriorityQueue vs RadixHeap) 🆚\n" +
                "5. Compare cheapest routes (Dijkstra vs Bellman-Ford) 💰\n" +
                "6. Return to the main menu \u2B05\uFE0F\n" +
                "\033[0m"); // Reset color
    }

    private String[] getRouteInput() throws IllegalArgumentException {
        System.out.print("\033[1;34mEnter start city: \033[0m");
        String source = sc.nextLine().trim();

        System.out.print("\033[1;34mEnter end city: \033[0m");
        String endCity = sc.nextLine().trim();

        if (source.isEmpty() || endCity.isEmpty()) {
            throw new IllegalArgumentException("Both start and end city cannot be empty.");
        }
        return new String[]{source, endCity};
    }

    private void measureRuntime(String routeType, RouteFinder routeFinder) {
        try {
            String[] cities = getRouteInput(); // Get the input only once
            System.out.print("\033[1;36mCalculating the " + routeType + " route...\033[0m");
     
            // Measure runtime
            simulateProgress();
            long startTime = System.nanoTime();
            routeFinder.findRoute(cities); // Pass the cities to the route finder
            long endTime = System.nanoTime();
            System.out.println("\033[1;32m" + capitalizeFirstLetter(routeType) + " route completed in " + ((endTime - startTime) / 1_000_000) + " ms\033[0m"); // Convert to milliseconds

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while finding the " + routeType + " route: " + e.getMessage() + "\033[0m");
        }
    }

    private void findFastestRoute(String[] cities) {
        dij.calculateWithPriorityQueue(graph, cities[0], cities[1], true); // byDuration=true
    }

    private void findCheapestRoute(String[] cities) {
        dij.calculateWithRadixHeap(graph, cities[0], cities[1], false); // byDuration=false
    }

    private void findMostDirectRoute(String[] cities) {
        bfs.findMostDirectRoute(graph, cities[0], cities[1]);
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void compareFastestRoutes() {
        try {
            String[] cities = getRouteInput();
            System.out.println("\033[1;36mComparing fastest routes\033[0m");

            // Measure Priority Queue
            long runtimePriorityQueue = calculateRouteRuntime(() -> dij.calculateWithPriorityQueue(graph, cities[0], cities[1], true), "PriorityQueue");

            // Measure Radix Heap
            long runtimeRadixHeap = calculateRouteRuntime(() -> dij.calculateWithRadixHeap(graph, cities[0], cities[1], true), "RadixHeap");

            // Print table
            printComparisonTable(new String[]{"ID", "Algorithm", "Runtime (ms)"},
                    new String[]{"1", "PriorityQueue", String.valueOf(runtimePriorityQueue)},
                    new String[]{"2", "RadixHeap", String.valueOf(runtimeRadixHeap)});

            // Output the comparison result
            if (runtimeRadixHeap > runtimePriorityQueue) {
                System.out.println("\033[1;32mPriority Queue Dijkstra's is faster\033[0m");
            } else if (runtimeRadixHeap < runtimePriorityQueue) {
                System.out.println("\033[1;32mRadix Heap Dijkstra's is faster\033[0m");
            } else {
                System.out.println("\033[1;32mBoth are equally fast\033[0m");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
        }
    }

    private void compareCheapestRoutes() {
        try {
            String[] cities = getRouteInput();
            System.out.println("\033[1;36mComparing cheapest routes\033[0m");

            // Measure Dijkstra
            long runtimeDijkstra = calculateRouteRuntime(() -> dij.calculateWithPriorityQueue(graph, cities[0], cities[1], false), "Dijkstra");

            // Measure Bellman-Ford
            long runtimeBellmanFord = calculateRouteRuntime(() -> bellmanFord.calculateCheapestRoute(graph, cities[0], cities[1]), "Bellman-Ford");

            // Print table
            printComparisonTable(new String[]{"ID", "Algorithm", "Runtime (ms)"},
                    new String[]{"1", "Dijkstra", String.valueOf(runtimeDijkstra)},
                    new String[]{"2", "Bellman-Ford", String.valueOf(runtimeBellmanFord)});

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
        }
    }

    private long calculateRouteRuntime(Runnable routeCalculation, String algorithm) {
        System.out.print("\033[1;34mCalculating route with " + algorithm + "...\033[0m");
        simulateProgress();
        long startTime = System.nanoTime();
        routeCalculation.run();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        System.out.println("\033[1;32mDone in " + duration + " ms\033[0m");
        return duration;
    }

    private void simulateProgress() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.print(".");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Move to next line after progress
    }

    private void printComparisonTable(String[] header, String[]... rows) {
        String horizontalLine = "\033[1;33m+---------------------+-------------------------+----------------+\033[0m";

        System.out.println(horizontalLine); // Top border
        // Print the header
        System.out.printf("\033[1;33m| %-19s | %-23s | %-12s |\033[0m\n", header[0], header[1], header[2]);
        System.out.println(horizontalLine); // Separator line after header

        // Print each row
        for (String[] row : rows) {
            System.out.printf("| %-19s | %-23s | %-12s |\n", row[0], row[1], row[2]);
        }

        System.out.println(horizontalLine); // Bottom border
    }

}
