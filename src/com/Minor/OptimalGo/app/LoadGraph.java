package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.header.ArrayList;
import com.Minor.OptimalGo.parser.CSVParser;

import java.util.Arrays;
import java.util.List;

public class LoadGraph {
    private Graph graph;

    // Constructor to initialize and load the graph
    public LoadGraph() {
        String citiesFile = "src/com/Minor/OptimalGo/CSVFile/cities";
        String routesFile = "src/com/Minor/OptimalGo/CSVFile/transport";

        try {
            CSVParser parser = new CSVParser();

            // Parse cities and routes from CSV files
            ArrayList<String> cities = parser.parseCitiesTXT(citiesFile);
            ArrayList<String[]> routes = parser.parseRoutesTXT(routesFile);

            // Create the graph with empty nodes equal to the number of cities present in cities file
            this.graph = new Graph(cities.size());

            //loop to add all cities to the empty nodes of the graph
            for (String city : cities) {
                graph.addCity(city);
            }

            // Add routes to the edges of the graph
            for (String[] route : routes) {
                if (route.length != 5) {
                    System.out.println("Invalid route data: " + String.join(", ", route));
                    continue;
                }
                String origin = route[0].trim();
                String destination = route[1].trim();
                String type = route[2].trim();
                int price;
                int duration;
                try {
                    price = Integer.parseInt(route[3].trim());
                    duration = Integer.parseInt(route[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in route data: " + String.join(", ", route));
                    continue;
                }

                // Add the edge (route) to the graph
                graph.addEdge(origin, destination, type, price, duration);
            }

            // Print the graph call from print graph method of graph class
            graph.printGraph();

        } catch (Exception e) {
            System.out.println("Error loading graph: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //method to return the loaded graph
    public Graph getGraph() {
        return this.graph;
    }
}
