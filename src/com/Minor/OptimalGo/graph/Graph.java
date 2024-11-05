package com.Minor.OptimalGo.graph;

import java.util.*;

public class Graph {
    private ArrayList<ArrayList<Edge>> adjList;
    private Map<String, Integer> cityIndexMap; // for o(1) lookup time
    private List<String> cities;
    private final int citiesSize;

    public Graph(int numberOfCities) {
        this.citiesSize = numberOfCities;
        adjList = new ArrayList<>(numberOfCities);
        cityIndexMap = new HashMap<>(numberOfCities);
        cities = new ArrayList<>(numberOfCities);

        for (int i = 0; i < numberOfCities; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public int getCitySize() {
        return citiesSize;
    }
    public int addCity(String cityName) {
        return cityIndexMap.computeIfAbsent(cityName, key -> {
            cities.add(cityName);
            return cities.size() - 1;
        });
    }
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
            try {
                TransportType transportTypeEnum = TransportType.valueOf(typeOfTransport.toUpperCase());

                // Check for duplicate edges
                for (Edge edge : adjList.get(sourceIndex)) {
                    if (edge.destinationIndex == destinationIndex) {
                        System.out.println("🚨 Error: Edge already exists!");
                        return;
                    }
                }
                adjList.get(sourceIndex).add(new Edge(destinationIndex, transportTypeEnum, price, duration));

            } catch (IllegalArgumentException e) {
                System.out.println("🚨 Error: Invalid transport type provided!");
            }
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

    public void updateEdge(String sourceCity, String destinationCity, String newTypeOfTransport, int newPrice, int newDuration) {
        int sourceIndex = getCityIndex(sourceCity);
        int destinationIndex = getCityIndex(destinationCity);

        if (sourceIndex != -1 && destinationIndex != -1) {
            for (Edge edge : adjList.get(sourceIndex)) {
                if (edge.destinationIndex == destinationIndex) {
                    try {
                        TransportType transportTypeEnum = TransportType.valueOf(newTypeOfTransport.toUpperCase());
                        edge.transportType = transportTypeEnum;
                        edge.price = newPrice;
                        edge.duration = newDuration;
                        System.out.println("✏️ Edge updated: " + sourceCity + " -> " + destinationCity + " (" + newTypeOfTransport + ", ₹" + newPrice + ", " + newDuration + " mins)");
                        return;
                    } catch (IllegalArgumentException e) {
                        System.out.println("🚨 Error: Invalid transport type provided!");
                        return;
                    }
                }
            }
            System.out.println("🚨 Error: Edge not found between the specified cities!");
        } else {
            System.out.println("🚨 Error: One or both cities not found!");
        }
    }
    public void removeCity(String cityName) {
        int cityIndex = getCityIndex(cityName);

        if (cityIndex != -1) {
            adjList.remove(cityIndex);
            cities.remove(cityIndex);
            cityIndexMap.remove(cityName);
            for (ArrayList<Edge> edges : adjList) {
                edges.removeIf(edge -> edge.destinationIndex == cityIndex);
            }
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
    public Edge getEdge(int sourceCityIndex, int destinationCityIndex) {
        if (sourceCityIndex < 0 || sourceCityIndex >= adjList.size()) {
            throw new IllegalArgumentException("Invalid source city index: " + sourceCityIndex);
        }

        for (Edge edge : adjList.get(sourceCityIndex)) {
            if (edge.destinationIndex == destinationCityIndex) {
                return edge; // Return the matching edge
            }
        }

        return null;
    }
    // Method to get the transport type between two cities
    public String getTransportType(int sourceCityIndex, int destinationCityIndex) {
        Edge edge = getEdge(sourceCityIndex, destinationCityIndex);
        if (edge != null) {
            return edge.transportType.toString();
        } else {
            return "No direct transport between the cities.";
        }
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
                            " | " + edge.transportType.toString() + // Correct field name
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
