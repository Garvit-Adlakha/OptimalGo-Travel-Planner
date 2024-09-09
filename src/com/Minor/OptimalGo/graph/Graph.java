package com.Minor.OptimalGo.graph;

import java.util.*;





public class Graph {
    private ArrayList<ArrayList<Edge>> adjList;
    private List<String> cities;
    private final int citiesSize;
    // Constructor to initialize the graph
    public Graph(int numberOfCities) {
        this.citiesSize = numberOfCities;
        adjList = new ArrayList<>(numberOfCities);
        cities = new ArrayList<>(numberOfCities);

        // Initialize adjacency list for all vertices
        for (int i = 0; i < numberOfCities; i++) {
            adjList.add(new ArrayList<>());
        }
    }
<<<<<<< HEAD
    // Method to return the size of the city list
    public int citySize() {
        return citiesSize;
    }
    // Method to add a city to the graph and return its index
=======

>>>>>>> 2b31c780ec9ba7d871d4d8ab1ddfbf53ed0d257f
    public int addCity(String cityName) {
        if (!cities.contains(cityName)) {
            cities.add(cityName);
            return cities.size() - 1;
        } else {
            return getCityIndex(cityName);
        }
    }
<<<<<<< HEAD
    // Method to get the index of a city by its name
=======

    // Get the index of a city by its name
>>>>>>> 2b31c780ec9ba7d871d4d8ab1ddfbf53ed0d257f
    public int getCityIndex(String cityName) {
        return cities.indexOf(cityName);
    }
<<<<<<< HEAD
    // Method to get the city name by its index
    public String getCityName(int cityIndex) {
        if (cityIndex >= 0 && cityIndex < cities.size()) {
            return cities.get(cityIndex);
        } else {
            System.out.println("Error: Invalid city index!");
            return null;
        }
    }

    // Method to add an edge between two cities in the graph
=======

    // Add an edge to the graph
>>>>>>> 2b31c780ec9ba7d871d4d8ab1ddfbf53ed0d257f
    public void addEdge(String sourceCity, String destinationCity, String typeOfTransport, int price, int duration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            adjList.get(sourceIndex).add(new Edge(destinationIndex, typeOfTransport, price, duration));
        } else {
            System.out.println("Error: One or both cities not found!");
        }
    }
<<<<<<< HEAD
    // Method to remove an edge between two cities
    public void removeEdge(String sourceCity, String destinationCity) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            adjList.get(sourceIndex).removeIf(edge -> edge.destinationIndex == destinationIndex);
        } else {
            System.out.println("Error: One or both cities not found!");
        }
    }

    // Method to update the details of an existing edge
    public void updateEdge(String sourceCity, String destinationCity, String newTypeOfTransport, int newPrice, int newDuration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);
        if (sourceIndex != -1 && destinationIndex != -1) {
            for (Edge edge : adjList.get(sourceIndex)) {
                if (edge.destinationIndex == destinationIndex) {
                    edge.typeOfTransport = newTypeOfTransport;
                    edge.price = newPrice;
                    edge.duration = newDuration;
                    return;
                }
            }
            System.out.println("Error: Edge not found between the specified cities!");
        } else {
            System.out.println("Error: One or both cities not found!");
        }
    }
    public void removeCity(String cityName) {
        int cityIndex = getCityIndex(cityName);

        if (cityIndex != -1) {
            adjList.remove(cityIndex);
            cities.remove(cityIndex);

            for (ArrayList<Edge> edges : adjList) {
                edges.removeIf(edge -> edge.destinationIndex == cityIndex);
            }

            // Adjust the destination indices after removing the city
            for (ArrayList<Edge> edges : adjList) {
                for (Edge edge : edges) {
                    if (edge.destinationIndex > cityIndex) {
                        edge.destinationIndex--;
                    }
                }
            }
        } else {
            System.out.println("Error: City not found!");
        }
    }

    // Method to retrieve the list of edges (connections) for a specific city
    public List<Edge> getEdges(int cityIndex) {
        if (cityIndex < 0 || cityIndex >= adjList.size()) {
            System.out.println("Error: Invalid city index!");
            return new ArrayList<>();
        }
        return adjList.get(cityIndex);
    }

    // Method to print the adjacency list of the graph
=======

    // Print the adjacency list of the graph
>>>>>>> 2b31c780ec9ba7d871d4d8ab1ddfbf53ed0d257f
    public void printGraph() {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.print(cities.get(i) + " -> ");
            for (Edge edge : adjList.get(i)) {
                System.out.print(cities.get(edge.destinationIndex) + " (Type: " + edge.typeOfTransport + ", Price: " + edge.price + ", Duration: " + edge.duration + ") ");
            }
            System.out.println();
        }
    }
<<<<<<< HEAD
=======

    // Dijkstra's Algorithm
    public Map<String, Integer> dijkstra(String startCity, String criteria) {
        int startIndex = getCityIndex(startCity);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Start city not found");
        }

        int n = adjList.size();
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startIndex] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        pq.add(new Node(startIndex, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.index;

            for (Edge edge : adjList.get(u)) {
                int v = edge.destination;
                int newDistance = criteria.equals("cost") ? distances[u] + edge.price : distances[u] + edge.duration;

                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                    pq.add(new Node(v, newDistance));
                }
            }
        }

        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < n; i++) {
            result.put(cities.get(i), distances[i]);
        }
        return result;
    }

    private static class Node {
        int index;
        int distance;

        Node(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }
    }
>>>>>>> 2b31c780ec9ba7d871d4d8ab1ddfbf53ed0d257f
}
