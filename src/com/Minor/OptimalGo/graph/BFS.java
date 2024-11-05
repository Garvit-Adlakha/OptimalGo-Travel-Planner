package com.Minor.OptimalGo.graph;

import java.util.*;

public class BFS {
    public List<String> findMostDirectRoute(Graph graph, String startCity, String endCity) {
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);
        if (source == -1 || destination == -1) {
            System.out.println("🚨 Error: One or both cities not found.");
            return Collections.emptyList();
        }

        int numberOfCities = graph.getCitySize();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numberOfCities];
        int[] previous = new int[numberOfCities];
        Edge[] transportDetails = new Edge[numberOfCities];  // Store the edge details for the route
        Arrays.fill(previous, -1);
        queue.offer(source);
        visited[source] = true;

        System.out.println("🔍 Searching for the most direct route...");

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == destination) {
                System.out.println("🏁 Destination found: " + graph.getCityName(destination));
                break;
            }
            for (Edge edge : graph.getEdges(current)) {
                int neighbor = edge.destinationIndex;
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    previous[neighbor] = current;
                    transportDetails[neighbor] = edge;  // Store the edge leading to this neighbor
                    System.out.println("🛤️ Exploring: " + graph.getCityName(neighbor) + " from " + graph.getCityName(current)
                            + " via " + edge.transportType + " (Duration: " + edge.duration + " mins)");
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        List<String> routeDetails = new ArrayList<>();  // Store route with transport details

        for (int at = destination; at != -1; at = previous[at]) {
            path.addFirst(graph.getCityName(at));
            if (transportDetails[at] != null) {
                Edge edge = transportDetails[at];
                routeDetails.add(0, graph.getCityName(previous[at]) + " to " + graph.getCityName(at) + " via " +
                        edge.transportType + " (Duration: " + edge.duration + " mins)");
            }
        }

        if (!path.isEmpty() && path.getFirst().equals(startCity)) {
            System.out.println("\n🛣️ Most direct route from " + startCity + " to " + endCity + ":");
            System.out.println("📍 " + String.join(" ➡️ ", path));
            System.out.println("🚗 Route details with transport and duration:");
            routeDetails.forEach(System.out::println);
            return path;
        } else {
            System.out.println("❌ No direct route found from " + startCity + " to " + endCity + ".");
            return Collections.emptyList();
        }
    }
}


