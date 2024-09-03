package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;

import java.util.Map;

public class Login {
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.repl();

        // Example of using Graph directly in the main method
        exampleGraphOperations();
    }

    private static void exampleGraphOperations() {
        Graph graph = new Graph(4);

        // Add cities
        graph.addCity("A");
        graph.addCity("B");
        graph.addCity("C");
        graph.addCity("D");

        // Add edges
        graph.addEdge("A", "B", "Bus", 100, 60);
        graph.addEdge("A", "C", "Train", 200, 90);
        graph.addEdge("B", "C", "Flight", 150, 30);
        graph.addEdge("C", "D", "Bus", 120, 45);
        graph.addEdge("D", "A", "Train", 250, 120);

        // Print the graph
        graph.printGraph();

        // Find shortest path based on cost
        Map<String, Integer> costDistances = graph.dijkstra("A", "cost");
        System.out.println("Distances based on cost from A:");
        costDistances.forEach((city, distance) -> System.out.println("To " + city + ": " + distance));

        // Find shortest path based on time
        Map<String, Integer> timeDistances = graph.dijkstra("A", "time");
        System.out.println("Distances based on time from A:");
        timeDistances.forEach((city, distance) -> System.out.println("To " + city + ": " + distance));
    }
}
