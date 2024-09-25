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
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bfs = new BFS();
        this.bellmanFord = new BellmanFord();
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

        if (source.isEmpty()) {
            throw new IllegalArgumentException("Start city cannot be empty.");
        }

        if (endCity.isEmpty()) {
            throw new IllegalArgumentException("End city cannot be empty.");
        }
        return new String[]{source, endCity};
    }

    private void measureRuntime(String routeType, RouteFinder routeFinder) {
        try {
            String[] cities = getRouteInput(); // Get the input only once
            System.out.print("\033[1;36mCalculating the " + routeType + " route\033[0m");

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

            // User input for route calculation choice
            System.out.println("Choose route calculation method:");
            System.out.println("1: Priority Queue");
            System.out.println("2: Radix Heap");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1: // Fastest route with PriorityQueue
                    System.out.print("\033[1;34mCalculating fastest route with PriorityQueue\033[0m");
                    simulateProgress(); // Call this before starting the timer
                    long pqStartTime = System.nanoTime();
                    dij.calculateWithPriorityQueue(graph, cities[0], cities[1], true);
                    long pqEndTime = System.nanoTime();
                    System.out.println("\033[1;32mFastest route (PriorityQueue) completed in " + ((pqEndTime - pqStartTime) / 1_000_000) + " ms\033[0m"); // Convert to milliseconds
                    break;

                case 2: // Fastest route with RadixHeap
                    System.out.print("\033[1;34mCalculating fastest route with RadixHeap...\033[0m");
                    simulateProgress(); // Call this before starting the timer
                    long rhStartTime = System.nanoTime();
                    dij.calculateWithRadixHeap(graph, cities[0], cities[1], true);
                    long rhEndTime = System.nanoTime();
                    System.out.println("\033[1;32mFastest route (RadixHeap) completed in " + ((rhEndTime - rhStartTime) / 1_000_000) + " ms\033[0m"); // Convert to milliseconds
                    break;

                default:
                    System.out.println("\033[1;31mInvalid choice. Please enter 1 or 2.\033[0m");
                    break;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while comparing the fastest routes: " + e.getMessage() + "\033[0m");
        }
    }


    private void compareCheapestRoutes() {
        try {
            String[] cities = getRouteInput();
            System.out.println("\033[1;36mComparing cheapest routes\033[0m");

            // User input for route calculation choice
            System.out.println("Choose route calculation method:");
            System.out.println("1: Dijkstra");
            System.out.println("2: Bellman-Ford");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1: // Cheapest route with Dijkstra
                    System.out.print("\033[1;34mCalculating cheapest route with Dijkstra\033[0m");
                    simulateProgress(); // Call this before starting the timer
                    long dijkstraStartTime = System.nanoTime();
                    dij.calculateWithPriorityQueue(graph, cities[0], cities[1], false);
                    long dijkstraEndTime = System.nanoTime();
                    System.out.println("\033[1;32mCheapest route (Dijkstra) completed in " + ((dijkstraEndTime - dijkstraStartTime) / 1_000_000) + " ms\033[0m"); // Convert to milliseconds
                    break;

                case 2: // Cheapest route with Bellman-Ford
                    System.out.print("\033[1;34mCalculating cheapest route with Bellman-Ford...\033[0m");
                    simulateProgress(); // Call this before starting the timer
                    long bellmanStartTime = System.nanoTime();
                    bellmanFord.calculateCheapestRoute(graph, cities[0], cities[1]);
                    long bellmanEndTime = System.nanoTime();
                    System.out.println("\033[1;32mCheapest route (Bellman-Ford) completed in " + ((bellmanEndTime - bellmanStartTime) / 1_000_000) + " ms\033[0m"); // Convert to milliseconds
                    break;

                default:
                    System.out.println("\033[1;31mInvalid choice. Please enter 1 or 2.\033[0m");
                    break;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\033[1;31mError: " + e.getMessage() + "\033[0m"); // Red error
        } catch (Exception e) {
            System.out.println("\033[1;31mAn unexpected error occurred while comparing the cheapest routes: " + e.getMessage() + "\033[0m");
        }
    }

    private void simulateProgress() {
        // Simulated delay for the calculation process
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500); // Simulate processing time
                System.out.print("\033[1;36m.\033[0m"); // Display progress
            }
            System.out.println(); // New line after progress
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("\033[1;31mProgress simulation interrupted.\033[0m"); // Red error
        }
    }
}
