package com.Minor.OptimalGo.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    // Method to parse cities from a .txt file, excluding the first row
    public List<String> parseCitiesTXT(String filePath) throws IOException {
        List<String> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the first line (header)
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Remove any trailing commas or whitespace
                line = line.trim();
                if (!line.isEmpty() && !line.endsWith(",")) {
                    cities.add(line);
                }
            }
        }
        return cities;
    }

    // Method to parse routes from a .txt file, excluding the first row
    public List<String[]> parseRoutesTXT(String filePath) throws IOException {
        List<String[]> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the first line (header)
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Ignore empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    routes.add(parts);
                } else {
                    System.out.println("Invalid route format: " + line);
                }
            }
        }
        return routes;
    }
}
