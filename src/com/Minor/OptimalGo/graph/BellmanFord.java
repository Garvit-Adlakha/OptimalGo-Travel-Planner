package com.Minor.OptimalGo.graph;

import com.Minor.OptimalGo.header.ArrayList;

public class BellmanFord {
    // Method to find the cheapest path
    public ArrayList<String> calculateCheapestRoute(Graph graph, String startCity, String endCity) {
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);
        if (source == -1 || destination == -1) {
            System.out.println("City not found!");
            return new ArrayList<>();
        }
        int numberOfCities = graph.getCitySize();
        int[] costs = new int[numberOfCities];
        int[] preVisitedNode = new int[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            costs[i] = Integer.MAX_VALUE;
            preVisitedNode[i] = -1;
        }
        costs[source] = 0;
        // Relax edges repeatedly
        for (int i = 0; i < numberOfCities - 1; i++) {
            for (int cityIndex = 0; cityIndex < numberOfCities; cityIndex++) {
                for (Edge edge : graph.getEdges(cityIndex)) {
                    int adjCityIndex = edge.destinationIndex;
                    int newCost = costs[cityIndex] + edge.price;
                    if (costs[cityIndex] != Integer.MAX_VALUE && newCost < costs[adjCityIndex]) {
                        costs[adjCityIndex] = newCost;
                        preVisitedNode[adjCityIndex] = cityIndex;
                    }
                }
            }
        }
        //negative weight cycle check
        for (int cityIndex = 0; cityIndex < numberOfCities; cityIndex++) {
            for (Edge edge : graph.getEdges(cityIndex)) {
                int adjCityIndex = edge.destinationIndex;
                if (costs[cityIndex] != Integer.MAX_VALUE && costs[cityIndex] + edge.price < costs[adjCityIndex]) {
                    System.out.println("Graph contains a negative-weight cycle.");
                    return new ArrayList<>();
                }
            }
        }
        return buildRoute(graph, startCity, endCity, costs, preVisitedNode);
    }
    // Helper method to build the route and print it in a formatted way
    private ArrayList<String> buildRoute(Graph graph, String startCity, String endCity, int[] costs, int[] preVisitedNode) {
        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> stepCosts = new ArrayList<>();
        int destination = graph.getCityIndex(endCity);
        for (int currentLocation = destination; currentLocation != -1; currentLocation = preVisitedNode[currentLocation]) {
            route.addFirst(graph.getCityName(currentLocation));
            if (preVisitedNode[currentLocation] != -1) {
                int prevCityIndex = preVisitedNode[currentLocation];
                int stepCost = costs[currentLocation] - costs[prevCityIndex];
                stepCosts.addFirst(stepCost);
            }
        }
        if (route.size() == 1 && !route.getFirst().equals(startCity)) {
            System.out.println(endCity + " not reachable from " + startCity);
            return new ArrayList<>();
        }
        System.out.println("\n🌍  ----- Route Summary -----  🌍");
        System.out.println("🚩 Cheapest route from " + startCity + " to " + endCity + ":\n");

        for (int i = 0; i < route.size(); i++) {
            if (i < route.size() - 1) {
                System.out.println("🛣️  " + route.get(i) + " ➡️  " + route.get(i + 1) + " (" + stepCosts.get(i) + " ₹)");
            } else {
                System.out.println("🏁 " + route.get(i) + "\n");
            }
        }
        System.out.println("===================================");
        System.out.println("💰 Total Price: " + costs[destination] + " ₹");
        System.out.println("🎉 Destination reached successfully! 🎉");
        System.out.println("===================================\n");

        return route;
    }
}
