package com.Minor.OptimalGo.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.Minor.OptimalGo.header.ArrayList;
import java.util.List;

public class CSVParser {
    public ArrayList<String> parseCitiesTXT(String filePath) throws IOException {
        ArrayList<String> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.endsWith(",")) {
                    cities.add(line);
                }
            }
        }
        return cities;
    }
    public ArrayList<String[]> parseRoutesTXT(String filePath) throws IOException {
        ArrayList<String[]> routes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
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
