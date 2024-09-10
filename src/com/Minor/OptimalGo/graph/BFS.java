package com.Minor.OptimalGo.graph;

import java.util.*;

public class BFS {

    // Method to find the most direct route using BFS
    public List<String> findMostDirectRoute(Graph graph, String startCity, String endCity) {
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);

        // Check if both cities are valid
        if (source == -1 || destination == -1) {
            System.out.println("City not found.");
            return Collections.emptyList();
        }

        int numberOfCities = graph.citySize();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numberOfCities];
        int[] previous = new int[numberOfCities];
        Arrays.fill(previous, -1);

        // Start BFS from the source city
        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // If the destination is reached, break the loop
            if (current == destination) {
                break;
            }

            // Get neighbors of the current city
            for (Edge edge : graph.getEdges(current)) {
                int neighbor = edge.destinationIndex; // Access the destination index
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    previous[neighbor] = current;
                }
            }
        }

        // Reconstruct the path from destination to source using the 'previous' array
        LinkedList<String> path = new LinkedList<>();
        for (int at = destination; at != -1; at = previous[at]) {
            path.addFirst(graph.getCityName(at));  // Add city name at the beginning of the list
        }

        // Check if the start city is the first city in the path
        if (!path.isEmpty() && path.getFirst().equals(startCity)) {
            // Print the direct route
            System.out.println("Most direct route from " + startCity + " to " + endCity + ":");
            System.out.println(String.join(" -> ", path));
            return path;
        } else {
            System.out.println("No direct route found from " + startCity + " to " + endCity + ".");
            return Collections.emptyList();
        }
    }
}
