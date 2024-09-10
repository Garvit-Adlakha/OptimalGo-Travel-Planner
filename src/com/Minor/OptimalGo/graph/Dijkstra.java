package com.Minor.OptimalGo.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import com.Minor.OptimalGo.header.ArrayList;

public class Dijkstra {

    // Method to find the shortest path based on duration
    public ArrayList<String> calculateShortestPath(Graph graph, String startCity, String endCity) {
        return calculateRoute(graph, startCity, endCity, true); // True for duration
    }

    // Method to find the cheapest route
    public ArrayList<String> calculateCheapestRoute(Graph graph, String startCity, String endCity) {
        return calculateRoute(graph, startCity, endCity, false); // False for price
    }

    // Method to calculate the route based on the chosen criteria (duration or price)
    private ArrayList<String> calculateRoute(Graph graph, String startCity, String endCity, boolean byDuration) {
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);

        if (source == -1 || destination == -1) {
            System.out.println("City not found!");
            return new ArrayList<>(); // Return empty list to avoid null handling
        }

        int numberOfCities = graph.citySize();
        int[] costs = new int[numberOfCities];
        int[] preVisitedNode = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];

        for (int i = 0; i < numberOfCities; i++) {
            costs[i] = Integer.MAX_VALUE;
            preVisitedNode[i] = -1;
        }

        costs[source] = 0;

        // PriorityQueue to select the minimum cost path
        PriorityQueue<Pairs> pq = new PriorityQueue<>(Comparator.comparingInt(a -> byDuration ? a.duration : a.price));
        pq.add(new Pairs(source, 0, 0));

        while (!pq.isEmpty()) {
            Pairs current = pq.poll();
            int cityIndex = current.cityIndex;

            if (visited[cityIndex]) continue;

            visited[cityIndex] = true;

            for (Edge edge : graph.getEdges(cityIndex)) {
                int adjCityIndex = edge.destinationIndex;
                int newCost = costs[cityIndex] + (byDuration ? edge.duration : edge.price);

                if (newCost < costs[adjCityIndex]) {
                    costs[adjCityIndex] = newCost;
                    preVisitedNode[adjCityIndex] = cityIndex;
                    pq.add(new Pairs(adjCityIndex, byDuration ? newCost : 0, byDuration ? 0 : newCost));
                }
            }
        }

        ArrayList<String> route = new ArrayList<>();
        for (int currentLocation = destination; currentLocation != -1; currentLocation = preVisitedNode[currentLocation]) {
            route.addFirst(graph.getCityName(currentLocation));
        }

        // Check if the destination was reachable
        if (route.size() == 1 && !route.getFirst().equals(startCity)) {
            System.out.println(endCity + " not reachable from " + startCity);
            return new ArrayList<>();
        }

        // Print the route
        System.out.println((byDuration ? "Fastest" : "Cheapest") + " route from " + startCity + " to " + endCity + ":");
        for (int i = 0; i < route.size(); i++) {
            if (i == route.size() - 1)
                System.out.println(route.get(i));
            else
                System.out.print(route.get(i) + " -> ");
        }

        System.out.println("Total " + (byDuration ? "Duration: " + costs[destination] + " minutes" : "Price: " + costs[destination] + "₹"));

        return route;
    }
}
