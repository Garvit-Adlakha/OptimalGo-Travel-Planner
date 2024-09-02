package com.Minor.OptimalGo.app;

import com.Minor.OptimalGo.graph.Graph;
import com.Minor.OptimalGo.parser.CSVParser;

import java.util.List;

public class LoadGraph {

    public void load() {
        String citiesTXT = "src/com/Minor/OptimalGo/CSVFile/cities";
        String routesTXT = "src/com/Minor/OptimalGo/CSVFile/transport";

        try {
            CSVParser parser = new CSVParser();
            List<String> cities = parser.parseCitiesTXT(citiesTXT);
            List<String[]> routes = parser.parseRoutesTXT(routesTXT);

            // Create a graph with the number of cities
            Graph graph = new Graph(cities.size());

            // Add cities to the graph
            for (String city : cities) {
                graph.addCity(city);
            }

            // Debug: Print cities added to the graph
            System.out.println("Cities added to graph:");
            for (int i = 0; i < cities.size(); i++) {
                System.out.println(i + ": " + cities.get(i));
            }

            // Add routes to the graph
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

                int originIndex = graph.getCityIndex(origin);
                int destinationIndex = graph.getCityIndex(destination);

                if (originIndex == -1 || destinationIndex == -1) {
                    System.out.println("Error: One or both cities not found! Origin: " + origin + ", Destination: " + destination);
                } else {
                    graph.addEdge(origin, destination, type, price, duration);
                }
            }

            // Print the graph
            graph.printGraph();
        } catch (Exception e) {
            System.out.println("Error loading graph: " + e.getMessage());
        }
    }
}
