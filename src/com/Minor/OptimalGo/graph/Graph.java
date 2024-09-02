package com.Minor.OptimalGo.graph;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private ArrayList<ArrayList<Edge>> adjList;
    private List<String> cities;

    // Constructor to initialize the graph
    public Graph(int numberOfCities) {
        adjList = new ArrayList<>(numberOfCities);
        cities= new ArrayList<>(numberOfCities);

        // Initialize adjacency list for all vertices
        for (int i = 0; i < numberOfCities; i++) {
            adjList.add(new ArrayList<Edge>());
        }
    }
    public int addCity(String cityName) {
        cities.add(cityName);  // Add city name to the list
        return cities.size() - 1;  // Return the index of the added city
    }
    // Get the index of a city by its name
    public int getCityIndex(String cityName) {
        return cities.indexOf(cityName);  // Get the index of the city in the list
    }
    // Add an edge to the graph
    public void addEdge(String sourceCity, String destinationCity, String typeOfTransport, int price, int duration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            adjList.get(sourceIndex).add(new Edge(destinationIndex, typeOfTransport, price, duration));
        } else {
            System.out.println("Error: One or both cities not found!");
        }
    }
    // Print the adjacency list of the graph
    public void printGraph() {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.print(cities.get(i) + " -> ");
            for (Edge edge : adjList.get(i)) {
                System.out.print(cities.get(edge.destination) + " (Type: " + edge.typeOfTransport + ", Price: " + edge.price + ", Duration: " + edge.duration + ") ");
            }
            System.out.println();
        }
    }
}