package com.Minor.OptimalGo.graph;

import java.util.*;

public class BFS {
    public List<String> findMostDirectRoute(Graph graph, String startCity, String endCity) {
        // Get indices of the source and destination cities
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);
        startCity=graph.getCityName(source);
        endCity=graph.getCityName(destination);

        if (source == -1 || destination == -1) {
            System.out.println("🚨 Error: One or both cities not found in the graph.");
            return Collections.emptyList();
        }

        // Initialize BFS data structures
        int numberOfCities = graph.getCitySize();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numberOfCities];
        int[] previous = new int[numberOfCities];
        Edge[] transportDetails = new Edge[numberOfCities];
        Arrays.fill(previous, -1);

        // Start BFS
        queue.offer(source);
        visited[source] = true;

        System.out.println("🔍 Starting BFS from " + startCity + " to find " + endCity);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == destination) {
                System.out.println("🏁 Reached destination: " + graph.getCityName(destination));
                break;
            }

            // Traverse all edges of the current city
            for (Edge edge : graph.getEdges(current)) {
                int neighbor = edge.destinationIndex;
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    previous[neighbor] = current;
                    transportDetails[neighbor] = edge;

                    System.out.println("🛤️ Exploring: " + graph.getCityName(neighbor) +
                            " from " + graph.getCityName(current) +
                            " via " + edge.transportType +
                            " (Duration: " + edge.duration + " mins)");

                }
            }
        }

        // Reconstruct the path from destination to source
        LinkedList<String> path = new LinkedList<>();
        List<String> routeDetails = new ArrayList<>();
        for (int at = destination; at != -1; at = previous[at]) {
            path.addFirst(graph.getCityName(at));
            if (transportDetails[at] != null) {
                Edge edge = transportDetails[at];
                routeDetails.addFirst(graph.getCityName(previous[at]) + " to " +
                        graph.getCityName(at) + " via " +
                        edge.transportType + " (Duration: " + edge.duration + " mins)");
            }
        }

        if (!path.isEmpty() && path.getFirst().equals(startCity)) {
            System.out.println("\n🛣️ Most direct route from " + startCity + " to " + endCity + ":");
            System.out.println("📍 " + String.join(" ➡️ ", path));
            System.out.println("🚗 Route details:");
            routeDetails.forEach(System.out::println);

            // Display attractions at the destination
            if (transportDetails[destination] != null && transportDetails[destination].attraction != null) {
                System.out.println("\n🏙️ Attractions at " + endCity + ":");
                for (String attraction : transportDetails[destination].attraction) {
                    System.out.println("🎡 " + attraction);
                }
            } else {
                System.out.println("❌ No attractions found at " + endCity + ".");
            }
            return path;
        } else {
            System.out.println("❌ No direct route found from " + startCity + " to " + endCity + ".");
            return Collections.emptyList();
        }
    }
}
