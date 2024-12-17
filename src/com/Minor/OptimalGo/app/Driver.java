package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.route.Route;
import com.Minor.OptimalGo.header.Runtime; // Import the Runtime class
import com.Minor.OptimalGo.route.RouteService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    private Graph graph;
    private final Scanner scanner;
    private Route route;
    private RouteService routeService;
    private final Runtime runtime = new Runtime(); // Add the Runtime instance

    public Driver() {
        this.scanner = new Scanner(System.in);
    }

    public void repl() {
        boolean running = true;
        waitForEnterKey();
        runtime.start();
        LoadGraph loadGraph = new LoadGraph();
        graph = loadGraph.getGraph();
        route = new Route(graph);
        routeService=new RouteService(graph);
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
                runtime.start(); // Start runtime
                handleUserChoice(choice);
                if (choice != 8) {
                    waitForEnterKey();
                } else {
                    running = false;
                }
                runtime.stop();
                runtime.printDuration();

            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInvalid input. Please enter a valid number.\033[0m"); // Red error
                scanner.nextLine();
            }
        }
        closeProgram(); // Close resources
    }

    private void waitForEnterKey() {
        System.out.println("\033[1;32mPress Enter to continue...\033[0m");
        scanner.nextLine();
    }

    private void displayMainMenu() {
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("\033[1;33m         MAIN MENU            \033[0m");
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
            1. 🛣️  Find a route
            2. ⏱️ Runtime
            3. ❓ Help
            4. 🚪 Exit
            """);
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1 -> route.routeType();
            case 2-> displayRuntime();
            case 3 -> help();
            case 4 -> {
                System.out.println("\033[1;31mExiting program...\033[0m"); // Red exit message
                closeProgram();
                System.exit(1);
            }
            default -> System.out.println("\033[1;31mInvalid choice. Please select a number between 1 and 8.\033[0m");
        }
    }
    private void displayRuntime() {
        while (true) {
            System.out.println("\033[1;34mSelect an Algorithm to Measure Runtime:\033[0m\n");
            System.out.println("""
            \033[1;33m1️⃣ Dijkstra Priority Queue (Fastest)\033[0m 🌟
            \033[1;33m2️⃣ Dijkstra Radix Heap (Fastest)\033[0m ⚡
            \033[1;33m3️⃣ Dijkstra Priority Queue (Cheapest)\033[0m 💸
            \033[1;33m4️⃣ Dijkstra Radix Heap (Cheapest)\033[0m 🪙
            \033[1;33m5️⃣ Bellman-Ford (Cheapest)\033[0m 📉
            \033[1;33m6️⃣ BFS (Direct)\033[0m 🚀
            \033[1;33m7️⃣ Go Back\033[0m 🔙
            """);
            System.out.print("\033[1;32mEnter your choice: \033[0m");
            int choice = scanner.nextInt();
            if (choice == 7) {
                System.out.println("\033[1;35mReturning to the previous menu...\033[0m");
                return;
            }
            String algorithmName = "";
            boolean validChoice = true;
            switch (choice) {
                case 1 -> algorithmName = "Dijkstra Priority Queue (Fastest)";
                case 2 -> algorithmName = "Dijkstra Radix Heap (Fastest)";
                case 3 -> algorithmName = "Dijkstra Priority Queue (Cheapest)";
                case 4 -> algorithmName = "Dijkstra Radix Heap (Cheapest)";
                case 5 -> algorithmName = "Bellman-Ford (Cheapest)";
                case 6 -> algorithmName = "BFS (Direct)";
                default -> {
                    validChoice = false;
                    System.out.println("\033[1;31mInvalid choice! Please select a valid option.\033[0m");
                }
            }
            if (!validChoice) {
                continue;
            }
            long runtime = routeService.measureAverageRuntimeUtil(choice);
            String runtimeText = "Runtime: " + runtime + " ms";
            int boxWidth = Math.max(runtimeText.length(), algorithmName.length()) + 8;  // Adjust for padding
            System.out.println("\033[1;36m╔" + "═".repeat(boxWidth) + "╗\033[0m");
            System.out.println("\033[1;36m║  🔷 " + String.format("%-" + (boxWidth - 7) + "s", algorithmName) + " 🔷  ║\033[0m");
            System.out.println("\033[1;36m╟" + "─".repeat(boxWidth) + "╢\033[0m");
            System.out.println("\033[1;36m║  🚀 " + String.format("%-" + (boxWidth - 7) + "s", runtimeText) + " 🚀  ║\033[0m");
            System.out.println("\033[1;36m╚" + "═".repeat(boxWidth) + "╝\033[0m");
            System.out.println("\n\033[1;35mPress Enter to continue...\033[0m");
            try {
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void help() {
        System.out.println("\033[1;36m==============================\033[0m"); // Cyan separator
        System.out.println("\033[1;33m            HELP              \033[0m"); // Yellow title
        System.out.println("\033[1;36m==============================\033[0m");
        System.out.println("""
                1. 🛣️  Find route: Allows you to find the fastest, cheapest, or most direct route.
                2. ⏱️ Runtime:Show Runtime
                3. ❓ Help: Show this help menu.
                4. 🚪 Exit: Close the application.
                """);
    }
    private void closeProgram() {
        System.out.println("\033[1;31m🚪 Closing program and releasing resources...\033[0m"); // Red close message
       return;
    }
    public Scanner getScanner(){
        return scanner;
    }
}
