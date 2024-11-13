package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.header.ArrayList;
import com.Minor.OptimalGo.parser.CSVParser;

import java.util.HashMap;
import java.util.Map;

public class LoadGraph {
    private Graph graph;
    private Map<String, String> abbreviations = new HashMap<>(); // You may use this later if needed

    // Constructor to initialize and load the graph
    public LoadGraph() {
        String citiesFile = "src/com/Minor/OptimalGo/CSVFile/cities";  // Ensure the path is correct
        String routesFile = "src/com/Minor/OptimalGo/CSVFile/transport";

        try {
            CSVParser parser = new CSVParser();
            // Parse cities and routes from CSV files
            ArrayList<String[]> cities = parser.parseCitiesCSV(citiesFile);
            ArrayList<String[]> routes = parser.parseRoutesTXT(routesFile);

            // Create the graph with empty nodes equal to the number of cities present in the cities file
            this.graph = new Graph(cities.size());
            for (String[] cityData : cities) {
                String cityName = cityData[0].trim().toUpperCase();  // Normalize to uppercase
                String abbreviation = cityData[1].trim().toUpperCase(); // Normalize to uppercase
                graph.abbreviationMap.put(abbreviation, cityName); // Add abbreviation and full city name to abbreviationMap
                graph.addCity(cityName); // Add the city to the graph
            }

            // Add routes to the edges of the graph
            for (String[] route : routes) {
                if (route.length < 6) {  // Ensure there are at least 6 fields (origin, destination, type, price, duration, attractions)
                    System.out.println("Invalid route data: " + String.join(", ", route));
                    continue;
                }
                String origin = route[0].trim().toUpperCase();  // Normalize to uppercase
                String destination = route[1].trim().toUpperCase(); // Normalize to uppercase
                String type = route[2].trim();
                int price;
                int duration;
                String[] attractions = route[5].split(",");  // Attractions are assumed to be comma-separated

                try {
                    price = Integer.parseInt(route[3].trim());
                    duration = Integer.parseInt(route[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in route data: " + String.join(", ", route));
                    continue;
                }

                // Add the edge (route) to the graph with attractions
                graph.addEdge(origin, destination, type, price, duration, attractions);
            }

            // Print the graph using the printGraph method of the graph class
            graph.printGraph();

        } catch (Exception e) {
            System.out.println("Error loading graph: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to return the loaded graph
    public Graph getGraph() {
        return this.graph;
    }
}
