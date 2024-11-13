package com.Minor.OptimalGo.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.Minor.OptimalGo.header.ArrayList;

public class CSVParser {

    public ArrayList<String[]> parseCitiesCSV(String filePath) throws IOException {
        ArrayList<String[]> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] columns = line.split(","); // Split by comma
                    if (columns.length == 2) {
                        cities.add(new String[] { columns[0].trim(), columns[1].trim() }); // Add name and abbreviation
                    }
                }
            }
        }
        return cities;
    }

    public ArrayList<String[]> parseRoutesTXT(String filePath) throws IOException {
        ArrayList<String[]> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into parts, allowing the last field to handle multiple comma-separated attractions
                String[] parts = line.split(",", -1); // Split using comma but allow trailing empty fields

                // Ensure that we have the correct number of fields (6 columns, but last field can contain commas)
                if (parts.length >= 6) {
                    String origin = parts[0].trim();
                    String destination = parts[1].trim();
                    String type = parts[2].trim();
                    int price;
                    int duration;

                    try {
                        price = Integer.parseInt(parts[3].trim());
                        duration = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in route data: " + String.join(", ", parts));
                        continue;
                    }

                    // Join all remaining parts starting from index 5 to form the attractions string
                    String attractions = String.join(",", java.util.Arrays.copyOfRange(parts, 5, parts.length)).trim();

                    // Add the edge (route) to the graph with attractions
                    routes.add(new String[] { origin, destination, type, String.valueOf(price), String.valueOf(duration), attractions });
                } else {
                    System.out.println("Invalid route data: " + String.join(", ", parts));
                }
            }
        }
        return routes;
    }

}
