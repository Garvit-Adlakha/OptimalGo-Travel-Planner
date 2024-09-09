package com.Minor.OptimalGo.route;

import com.Minor.OptimalGo.app.LoadGraph;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Route {
    public Graph graph;
    public void routeType(Graph graph) {
            this.graph=graph;
        if (graph == null) {
            System.out.println("Graph: Null");
            System.exit(1);
        }
        Dijkstra dij=new Dijkstra();
        boolean isRunning = true;
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            System.out.println("""
                    press 1 for finding fastest route
                    press 2 for finding cheapest route
                    press 3 for finding most direct route
                    press 4 to go back to the main menu
                    """);
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter start  city");
                        String source=sc.nextLine();
                        System.out.println();
                        System.out.println("Enter end city");
                        String endCity=sc.nextLine();
                        System.out.println();
                        dij.calculateShortestPath(graph,source,endCity);
                    }
                    case 2 -> {
                        System.out.println("Enter start city");
                        String source=sc.nextLine();
                        System.out.println();
                        System.out.println("Enter end city");
                        String endCity=sc.nextLine();
                        System.out.println();
                        dij.calculateCheapestRoute(graph,source,endCity);
                    }
                    case 3 -> {
                        System.out.println("Finding most direct route...");
                        // Implement logic to find the most direct route
                    }
                    case 4 -> {
                        System.out.println("Going back to the main menu...");
                        isRunning = false;  // Exit the loop and return to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                // After processing a valid choice, wait for Enter to go back to the menu
                if (isRunning) {
                    System.out.println("Press Enter to go back to the route selection...");
                    sc.nextLine();  // Wait for Enter key press
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Consume invalid input and clear scanner buffer
            }
        }
    }
}
