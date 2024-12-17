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
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] columns = line.split(",");
                    if (columns.length == 2) {
                        cities.add(new String[] { columns[0].trim(), columns[1].trim() });
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
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", -1);
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
                    String attractions = String.join(",", java.util.Arrays.copyOfRange(parts, 5, parts.length)).trim();
                    routes.add(new String[] { origin, destination, type, String.valueOf(price), String.valueOf(duration), attractions });
                } else {
                    System.out.println("Invalid route data: " + String.join(", ", parts));
                }
            }
        }
        return routes;
    }

}
