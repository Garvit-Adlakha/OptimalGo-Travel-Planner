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

    // Method to return the size of the city list
    public int citySize() {
        return citiesSize;
    }

    // Method to add a city
    public int addCity(String cityName) {
        if (!cities.contains(cityName)) {
            cities.add(cityName);
            return cities.size() - 1;
        } else {
            return getCityIndex(cityName);
        }
    }

    // Method to get the index of a city by its name
    public int getCityIndex(String cityName) {
        return cities.indexOf(cityName);
    }

    // Method to get the city name by its index
    public String getCityName(int cityIndex) {
        if (cityIndex >= 0 && cityIndex < cities.size()) {
            return cities.get(cityIndex);
        } else {
            System.out.println("🚨 Error: Invalid city index!");
            return null;
        }
    }

    // Method to add an edge between two cities in the graph
    public void addEdge(String sourceCity, String destinationCity, String typeOfTransport, int price, int duration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            adjList.get(sourceIndex).add(new Edge(destinationIndex, typeOfTransport, price, duration));
            System.out.println("✅ Edge added from " + sourceCity + " to " + destinationCity + " (" + typeOfTransport + ", ₹" + price + ", " + duration + " mins)");
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
            System.out.println("🚨 Error: Invalid city index!");
            return new ArrayList<>();
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
                for (Edge edge : adjList.get(i)) {
                    System.out.print("[" + cities.get(edge.destinationIndex) + " | " + edge.typeOfTransport + " | ₹" + edge.price + " | " + edge.duration + " mins] ");
                }
                System.out.println();
            }
        }
        System.out.println("-------------------------------\n");
    }

    // Method to retrieve the adjacency list
    public ArrayList<ArrayList<Edge>> getAdjList() {
        return adjList;
    }
}
