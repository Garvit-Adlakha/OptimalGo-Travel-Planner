package com.Minor.OptimalGo.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import com.Minor.OptimalGo.header.ArrayList;
import com.Minor.OptimalGo.header.RadixHeap;
import com.Minor.OptimalGo.header.RadixHeap.Node;

public class Dijkstra {
    public ArrayList<String> calculateWithPriorityQueue(Graph graph, String startCity, String endCity, boolean byDuration) {
        return calculateRoute(graph, startCity, endCity, byDuration, false);
    }
    public ArrayList<String> calculateWithRadixHeap(Graph graph, String startCity, String endCity, boolean byDuration) {
        return calculateRoute(graph, startCity, endCity, byDuration, true);
    }
    private ArrayList<String> calculateRoute(Graph graph, String startCity, String endCity, boolean byDuration, boolean useRadixHeap) {

        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);

        if (source == -1 || destination == -1) {
            System.out.println("City not found!");
            return new ArrayList<>();
        }
        int numberOfCities = graph.getCitySize();
        int[] costs = new int[numberOfCities];
        int[] preVisitedNode = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];

        for (int i = 0; i < numberOfCities; i++) {
            costs[i] = Integer.MAX_VALUE;
            preVisitedNode[i] = -1;
        }
        costs[source] = 0;
        if (useRadixHeap) {
            RadixHeap heap = new RadixHeap(numberOfCities);
            heap.insert(source, 0);

            while (!heap.isEmpty()) {
                Node current = heap.extractMin();
                int cityIndex = current.cityIndex;

                if (visited[cityIndex]) continue;
                visited[cityIndex] = true;

                for (Edge edge : graph.getEdges(cityIndex)) {
                    int adjCityIndex = edge.destinationIndex;
                    int newCost = costs[cityIndex] + (byDuration ? edge.duration : edge.price);

                    if (adjCityIndex >= 0 && adjCityIndex < numberOfCities) {
                        if (newCost < costs[adjCityIndex]) {
                            costs[adjCityIndex] = newCost;
                            preVisitedNode[adjCityIndex] = cityIndex;
                            heap.insert(adjCityIndex, newCost);
                        }
                    } else {
                        System.out.println("Invalid adjacency city index: " + adjCityIndex);
                    }
                }
            }
        } else {
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

                    if (adjCityIndex >= 0 && adjCityIndex < numberOfCities) {
                        if (newCost < costs[adjCityIndex]) {
                            costs[adjCityIndex] = newCost;
                            preVisitedNode[adjCityIndex] = cityIndex;
                            pq.add(new Pairs(adjCityIndex, byDuration ? newCost : 0, byDuration ? 0 : newCost));
                        }
                    } else {
                        System.out.println("Invalid adjacency city index: " + adjCityIndex);
                    }
                }
            }
        }

        return buildRoute(graph, startCity, endCity, costs, preVisitedNode, byDuration);
    }

    private ArrayList<String> buildRoute(Graph graph, String startCity, String endCity, int[] costs, int[] preVisitedNode, boolean byDuration) {
        ArrayList<String> route = new ArrayList<>();
        ArrayList<Integer> stepCosts = new ArrayList<>();
        ArrayList<String> transportTypes = new ArrayList<>();
        int destination = graph.getCityIndex(endCity);
        System.out.println("Building route from " + startCity + " to " + endCity);
        String destinationAttractions = "No attractions found";
        for (int currentLocation = destination; currentLocation != -1; currentLocation = preVisitedNode[currentLocation]) {
            if (currentLocation < 0 || currentLocation >= costs.length) {
                System.out.println("Error: currentLocation index out of bounds: " + currentLocation);
                break;
            }
            route.add(0, graph.getCityName(currentLocation));
            if (preVisitedNode[currentLocation] != -1) {
                int prevCityIndex = preVisitedNode[currentLocation];
                Edge edge = graph.getEdge(prevCityIndex, currentLocation);
                if (edge != null) {
                    int stepCost = costs[currentLocation] - costs[prevCityIndex];
                    stepCosts.add(0, stepCost);
                    transportTypes.add(0, edge.transportType.name());
                    if (currentLocation == destination && edge.attraction != null && edge.attraction.length > 0) {
                        destinationAttractions = String.join(", ", edge.attraction);
                    }
                }
            }
        }
        if (route.size() == 1 && !route.get(0).equals(startCity)) {
            System.out.println(endCity + " not reachable from " + startCity);
            return new ArrayList<>();
        }
        System.out.println("\n🌍  ----- Route Summary -----  🌍");
        System.out.println("🚩 " + (byDuration ? "Fastest" : "Cheapest") + " route from " + startCity + " to " + endCity + ":\n");
        for (int i = 0; i < route.size(); i++) {
            if (i < route.size() - 1) {
                System.out.println("🛣️  " + route.get(i) + " ➡️  " + route.get(i + 1) + " (" +
                        transportTypes.get(i) + ", " + (byDuration ? stepCosts.get(i) + " mins" : stepCosts.get(i) + " ₹") + ")");
            } else {
                System.out.println("🏁 " + route.get(i) + "\n");
            }
        }
        System.out.println("===================================");
        System.out.println("🕒 Total " + (byDuration ? "Duration: " + costs[destination] + " minutes" : "Price: " + costs[destination] + " ₹"));
        System.out.println("🌟 Attractions : " + destinationAttractions);
        System.out.println("🎉 Destination reached successfully! 🎉");
        System.out.println("===================================\n");

        return route;
    }

}
