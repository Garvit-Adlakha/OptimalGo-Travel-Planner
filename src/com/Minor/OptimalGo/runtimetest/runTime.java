package com.Minor.OptimalGo.runtimetest;

import com.Minor.OptimalGo.graph.BFS;
import com.Minor.OptimalGo.graph.Dijkstra;
import com.Minor.OptimalGo.graph.Graph;

public class runTime {
    private Graph graph;
    private final Dijkstra dij;
    private final BFS bfs;

    public runTime(Graph graph) {
        this.graph = graph;
        this.dij = new Dijkstra();
        this.bfs = new BFS();
    }

    // Method to calculate and print the runtime of the BFS algorithm
    public void testBFS(String startCity, String endCity) {
        long startTime = System.nanoTime();
        bfs.findMostDirectRoute(graph, startCity, endCity);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Convert nanoseconds to milliseconds
        System.out.println("BFS runtime: " + duration + " ms");
    }

    // Method to calculate and print the runtime of Dijkstra's shortest path (fastest route)
    public void testDijkstraFastestRoute(String startCity, String endCity) {
        long startTime = System.nanoTime();
        dij.calculateShortestPath(graph, startCity, endCity);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Dijkstra (Fastest route) runtime: " + duration + " ms");
    }

    // Method to calculate and print the runtime of Dijkstra's cheapest route
    public void testDijkstraCheapestRoute(String startCity, String endCity) {
        long startTime = System.nanoTime();
        dij.calculateCheapestRoute(graph, startCity, endCity);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Dijkstra (Cheapest route) runtime: " + duration + " ms");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(10); // Create a graph with 10 cities (replace with actual graph data)
        runTime tester = new runTime(graph);

        // Add cities and edges here (replace with actual data)
        graph.addCity("CityA");
        graph.addCity("CityB");
        graph.addEdge("CityA", "CityB", "Bus", 100, 60);

        // Test BFS runtime
        tester.testBFS("CityA", "CityB");

        // Test Dijkstra fastest route runtime
        tester.testDijkstraFastestRoute("CityA", "CityB");

        // Test Dijkstra cheapest route runtime
        tester.testDijkstraCheapestRoute("CityA", "CityB");
    }
}
