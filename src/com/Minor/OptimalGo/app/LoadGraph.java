package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.header.ArrayList;
import com.Minor.OptimalGo.parser.CSVParser;

import java.util.HashMap;
import java.util.Map;

public class LoadGraph {
    private Graph graph;
    private Map<String, String> abbreviations = new HashMap<>();
    // Constructor to initialize and load the graph
    public LoadGraph() {
        String citiesFile = "src/com/Minor/OptimalGo/CSVFile/cities_25.csv";
        String routesFile = "src/com/Minor/OptimalGo/CSVFile/transport_25.csv";

        try {
            CSVParser parser = new CSVParser();
            // Parse cities and routes from CSV files
            ArrayList<String[]> cities = parser.parseCitiesCSV(citiesFile);
            ArrayList<String[]> routes = parser.parseRoutesTXT(routesFile);
            this.graph = new Graph(cities.size());
            for (String[] cityData : cities) {
                String cityName = cityData[0].trim() ;
                String abbreviation = cityData[1].trim();
                graph.abbreviationMap.put(abbreviation, cityName);
                graph.addCity(cityName);
            }

            // Add routes to the edges of the graph
            for (String[] route : routes) {
                if (route.length < 6) {
                    System.out.println("Invalid route data: " + String.join(", ", route));
                    continue;
                }
                String origin = route[0].trim();
                String destination = route[1].trim();
                String type = route[2].trim();
                int price;
                int duration;
                String[] attractions = route[5].split(",");

                try {
                    price = Integer.parseInt(route[3].trim());
                    duration = Integer.parseInt(route[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in route data: " + String.join(", ", route));
                    continue;
                }
                graph.addEdge(origin, destination, type, price, duration, attractions);
            }
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
