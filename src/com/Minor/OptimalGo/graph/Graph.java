package com.Minor.OptimalGo.graph;

import java.util.*;

public class Graph {
    private ArrayList<ArrayList<Edge>> adjList;
    private Map<String, Integer> cityIndexMap;
    private List<String> cities;
    private final int citiesSize;

    // Constructor to initialize the graph
    public Graph(int numberOfCities) {
        this.citiesSize = numberOfCities;
        adjList = new ArrayList<>(numberOfCities);
        cityIndexMap = new HashMap<>(numberOfCities); // O(1) lookup for cities
        cities = new ArrayList<>(numberOfCities);

        // Initialize adjacency list for all vertices
        for (int i = 0; i < numberOfCities; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Method to return the size of the city list
    public int getCitySize() {
        return citiesSize;
    }

    // Optimized method to add a city with O(1) time complexity
    public int addCity(String cityName) {
        return cityIndexMap.computeIfAbsent(cityName, key -> {
            cities.add(cityName);
            return cities.size() - 1;
        });
    }

    // Method to get the index of a city by its name
    public int getCityIndex(String cityName) {
        return cityIndexMap.getOrDefault(cityName, -1);
    }

    // Method to get the city name by its index
    public String getCityName(int cityIndex) {
        if (cityIndex >= 0 && cityIndex < cities.size()) {
            return cities.get(cityIndex);
        } else {
            throw new IllegalArgumentException("Invalid city index: " + cityIndex);
        }
    }

    // Method to add an edge between two cities in the graph
    public void addEdge(String sourceCity, String destinationCity, String typeOfTransport, int price, int duration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            // Check for duplicate edges
            for (Edge edge : adjList.get(sourceIndex)) {
                if (edge.destinationIndex == destinationIndex) {
                    System.out.println("🚨 Error: Edge already exists!");
                    return;
                }
            }
            adjList.get(sourceIndex).add(new Edge(destinationIndex, typeOfTransport, price, duration));
        } else {
            System.out.println("🚨 Error: One or both cities not found!");
        }
    }

    // Method to remove an edge between two cities
    public void removeEdge(String sourceCity, String destinationCity) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            adjList.get(sourceIndex).removeIf(edge -> edge.destinationIndex == destinationIndex);
            System.out.println("🗑️ Edge removed from " + sourceCity + " to " + destinationCity);
        } else {
            System.out.println("🚨 Error: One or both cities not found!");
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
                    System.out.println("✏️ Edge updated: " + sourceCity + " -> " + destinationCity + " (" + newTypeOfTransport + ", ₹" + newPrice + ", " + newDuration + " mins)");
                    return;
                }
            }
            System.out.println("🚨 Error: Edge not found between the specified cities!");
        } else {
            System.out.println("🚨 Error: One or both cities not found!");
        }
    }

    // Method to remove a city and its corresponding edges
    public void removeCity(String cityName) {
        int cityIndex = getCityIndex(cityName);

        if (cityIndex != -1) {
            adjList.remove(cityIndex);
            cities.remove(cityIndex);
            cityIndexMap.remove(cityName);

            // Remove all edges pointing to this city
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
            System.out.println("🗑️ City " + cityName + " and its edges have been removed.");
        } else {
            System.out.println("🚨 Error: City not found!");
        }
    }

    // Method to retrieve the list of edges (connections) for a specific city
    public List<Edge> getEdges(int cityIndex) {
        if (cityIndex < 0 || cityIndex >= adjList.size()) {
            throw new IllegalArgumentException("Invalid city index: " + cityIndex);
        }
        return adjList.get(cityIndex);
    }

    // Method to print the adjacency list of the graph
    public void printGraph() {
        System.out.println("\n🌍  --- Graph Connections ---  🌍");
        for (int i = 0; i < adjList.size(); i++) {
            System.out.print("🏙️  " + cities.get(i) + " -> ");
            if (adjList.get(i).isEmpty()) {
                System.out.println("No direct connections.");
            } else {
                System.out.println(); // Move to next line for connections
                for (Edge edge : adjList.get(i)) {
                    System.out.println("    └─> [" + cities.get(edge.destinationIndex) +
                            " | " + edge.typeOfTransport +
                            " | ₹" + edge.price +
                            " | " + edge.duration + " mins]");
                }
            }
        }
        System.out.println("-------------------------------\n");
    }
    // Method to check if a city exists in the graph
    public boolean containsCity(String cityName) {
        return cityIndexMap.containsKey(cityName);
    }

    // Method to retrieve the adjacency list
    public ArrayList<ArrayList<Edge>> getAdjList() {
        return adjList;
    }
}
