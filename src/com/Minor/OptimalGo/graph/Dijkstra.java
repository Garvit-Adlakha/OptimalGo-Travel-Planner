package com.Minor.OptimalGo.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
//import com.Minor.OptimalGo.header.ArrayList;
import com.Minor.OptimalGo.header.ArrayList;
import java.util.List;

public class Dijkstra {

    public ArrayList<String> calculateShortestPath(Graph graph, String startCity, String endCity) {
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);

        if (source == -1 || destination == -1) {
            System.out.println("City not found!");
            return null;
        }

        int numberOfCities = graph.citySize();
        int[] durations = new int[numberOfCities];
        int[] preVisitedNode = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];

        for (int i = 0; i < numberOfCities; i++) {
            durations[i] = Integer.MAX_VALUE;
            preVisitedNode[i] = -1;
        }

        durations[source] = 0;
        Comparator<Pairs> durationComparator = new Comparator<Pairs>() {
            @Override
            public int compare(Pairs a, Pairs b) {
                return Integer.compare(a.duration, b.duration);
            }
        };
        PriorityQueue<Pairs> pq = new PriorityQueue<>((durationComparator));
        pq.add(new Pairs(source, 0,0));
        while (!pq.isEmpty()) {
            Pairs current = pq.poll();
            int cityIndex = current.cityIndex;

            if (visited[cityIndex]) continue;

            visited[cityIndex] = true;

            for (Edge edge : graph.getEdges(cityIndex)) {
                int adjCityIndex = edge.destinationIndex;
                int newDuration = durations[cityIndex] + edge.duration;

                if (newDuration < durations[adjCityIndex]) {
                    durations[adjCityIndex] = newDuration;
                    preVisitedNode[adjCityIndex] = cityIndex;
                    pq.add(new Pairs(adjCityIndex, newDuration,0));
                }
            }
        }
        ArrayList<String> fastestRoute = new ArrayList<>();
        for (int currentLocation = destination;currentLocation!= -1; currentLocation= preVisitedNode[currentLocation]) {
            fastestRoute.addFirst( graph.getCityName(currentLocation));
        }

        // Check if the destination was reachable
        if (fastestRoute.size() == 1 && !fastestRoute.getFirst().equals(startCity)) {
            System.out.println(   endCity+ " not reachable from " +startCity );
            return null;
        }

        // Print the fastest route
        System.out.println("Fastest route from " + startCity + " to " + endCity + ":");
        for (int i=0;i<fastestRoute.size();i++) {
            if (i==fastestRoute.size()-1)
                System.out.println(fastestRoute.get(i));
            else {
                System.out.print(fastestRoute.get(i) + " -> ");
            }
        }
        System.out.println("Total Duration: " + durations[destination] + " minutes");

        return fastestRoute;
    }
    public ArrayList<String> calculateCheapestRoute(Graph graph, String startCity, String endCity){
        int source = graph.getCityIndex(startCity);
        int destination = graph.getCityIndex(endCity);
        if (source == -1 || destination == -1) {
            System.out.println("City not found!");
            return null;
        }
        int numberOfCities = graph.citySize();
        int[] prices = new int[numberOfCities];
        int[] preVisitedNode = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            prices[i] = Integer.MAX_VALUE;
            preVisitedNode[i] = -1;
        }
        prices[source] = 0;
        Comparator<Pairs> durationComparator = new Comparator<Pairs>() {
            @Override
            public int compare(Pairs a, Pairs b) {
                return Integer.compare(a.price, b.price);
            }
        };
        PriorityQueue<Pairs> pq = new PriorityQueue<>((durationComparator));
        pq.add(new Pairs(source, 0,0));
        while (!pq.isEmpty()) {
            Pairs current = pq.poll();
            int cityIndex = current.cityIndex;
            if (visited[cityIndex]) continue;
            visited[cityIndex] = true;

            for (Edge edge : graph.getEdges(cityIndex)) {
                int adjCityIndex = edge.destinationIndex;
                int newPrice = prices[cityIndex] + edge.price;

                if (newPrice < prices[adjCityIndex]) {
                    prices[adjCityIndex] = newPrice;
                    preVisitedNode[adjCityIndex] = cityIndex;
                    pq.add(new Pairs(adjCityIndex, 0,newPrice));
                }
            }
        }
        ArrayList<String> cheapestRoute = new ArrayList<>();
        for (int currentLocation = destination;currentLocation!= -1; currentLocation= preVisitedNode[currentLocation]) {
            cheapestRoute.addFirst( graph.getCityName(currentLocation));
        }

        if (cheapestRoute.size() == 1 && !cheapestRoute.getFirst().equals(startCity)) {
            System.out.println(   endCity+ " not reachable from " +startCity );
            return null;
        }
        System.out.println("Fastest route from " + startCity + " to " + endCity + ":");
        for (int i=0;i<cheapestRoute.size();i++) {
            if (i==cheapestRoute.size()-1)
                System.out.println(cheapestRoute.get(i));
            else {
                System.out.print(cheapestRoute.get(i) + " -> ");
            }
        }
        System.out.println("Total Price: " + prices[destination] + "₹");

        return cheapestRoute;
    }
}
