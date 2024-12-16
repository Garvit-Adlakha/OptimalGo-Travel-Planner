package com.Minor.OptimalGo.CSVFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class GenerateCSV_15
{





        public static void main(String[] args) {
            String filePath = "enhanced_random_routes.csv";

            // Arrays of cities and transport types to randomly select from
            String[] cities = {"Chennai", "Bangalore", "Dehradun", "Mumbai", "Hyderabad", "Delhi", "Pune", "Kolkata", "Lucknow", "Jaipur","Amritsar", "Ahmedabad", "Indore", "Surat","Chandigarh"};
            String[] types = {"car", "train", "flight", "bus"};

            // Weighted probabilities for choosing transportation type based on distance (index corresponds to transport types)
            String[] longDistanceCities = {"Chennai", "Bangalore", "Mumbai", "Delhi", "Hyderabad","Indore","Surat"};
            String[] shortDistanceCities = {"Pune", "Kolkata", "Lucknow", "Jaipur", "Dehradun","Amritsar","Ahmedabad","Chandigarh"};

            // Map for cities to main attractions
            Map<String, String> cityAttractions = new HashMap<>();
            cityAttractions.put("Chennai", "Marina Beach,Kapaleeshwarar Temple");
            cityAttractions.put("Bangalore", "Bangalore Palace,Lalbagh Botanical Garden");
            cityAttractions.put("Dehradun", "Robber's Cave,Forest Research Institute");
            cityAttractions.put("Mumbai", "Gateway of India,Marine Drive");
            cityAttractions.put("Hyderabad", "Charminar,Golkonda Fort");
            cityAttractions.put("Delhi", "Red Fort,Qutub Minar");
            cityAttractions.put("Pune", "Shaniwar Wada,Aga Khan Palace");
            cityAttractions.put("Kolkata", "Victoria Memorial,Howrah Bridge");
            cityAttractions.put("Lucknow", "Bara Imambara,Chota Imambara");
            cityAttractions.put("Jaipur", "Amber Fort,City Palace");
            cityAttractions.put("Amritsar", "Golden Temple,Jallianwala Bagh");
            cityAttractions.put("Ahmedabad", "Sabarmati Ashram,Adalaj Stepwell");
            cityAttractions.put("Indore", "Rajwada Palace,Lal Bagh Palace");
            cityAttractions.put("Surat", "Dumas Beach,Sarthana Nature Park");
            cityAttractions.put("Chandigarh", "Rock Garden,Sukhna Lake");

            Random random = new Random();

            Set<String> generatedEdges = new HashSet<>();
            Set<String> repeatEdges = new HashSet<>();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                // Write CSV header, including the new column for Main Attractions
                writer.write("origin,destination,type,price,duration,main_attraction");
                writer.newLine();

                // Generate 100 random entries, ensuring at least 6 repeated edges
                int generatedCount = 0;
                for (int i = 0; i < 225; i++) {
                    // Randomly select origin and destination ensuring they're not the same
                    String origin = cities[random.nextInt(cities.length)];
                    String destination;
                    do {
                        destination = cities[random.nextInt(cities.length)];
                    } while (origin.equals(destination));

                    // Create a unique edge identifier
                    String edge = origin + "-" + destination;

                    // Check if the edge is already generated
                    if (generatedEdges.contains(edge)) {
                        repeatEdges.add(edge); // Add to repeat edges if it is already present
                    } else {
                        generatedEdges.add(edge); // Otherwise, add it to the set of unique edges
                    }

                    // Randomly decide transport type based on the cities' proximity
                    String type = decideTransportType(origin, destination, longDistanceCities, shortDistanceCities, random);

                    // Generate price and duration based on transport type
                    int[] priceAndDuration = generatePriceAndDuration(type, origin, destination, random);
                    int price = priceAndDuration[0];
                    int duration = priceAndDuration[1];

                    // Get the main attraction of the destination city (not origin)
                    String attraction = cityAttractions.getOrDefault(destination, "No main attraction listed");

                    // Write the generated data to the CSV file, now including main attraction of destination city
                    writer.write(String.format("%s,%s,%s,%d,%d,%s", origin, destination, type, price, duration, attraction));
                    writer.newLine();
                }

                // Now ensure that we have at least 6 repeated edges
                int requiredRepetitions = 6 - repeatEdges.size();
                for (int i = 0; i < requiredRepetitions; i++) {
                    // Pick a random edge to repeat
                    String repeatEdge = (String) generatedEdges.toArray()[random.nextInt(generatedEdges.size())];
                    String[] citiesInEdge = repeatEdge.split("-");
                    String origin = citiesInEdge[0];
                    String destination = citiesInEdge[1];

                    // Randomly decide transport type based on the cities' proximity
                    String type = decideTransportType(origin, destination, longDistanceCities, shortDistanceCities, random);

                    // Generate price and duration based on transport type
                    int[] priceAndDuration = generatePriceAndDuration(type, origin, destination, random);
                    int price = priceAndDuration[0];
                    int duration = priceAndDuration[1];

                    // Get the main attraction of the destination city (not origin)
                    String attraction = cityAttractions.getOrDefault(destination, "No main attraction listed");

                    // Write the repeated edge to the file, now including main attraction of destination city
                    writer.write(String.format("%s,%s,%s,%d,%d,%s", origin, destination, type, price, duration, attraction));
                    writer.newLine();
                }

                System.out.println("CSV file generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Decides transport type based on city distance (long or short distance)
        private static String decideTransportType(String origin, String destination, String[] longDistanceCities, String[] shortDistanceCities, Random random) {
            boolean isLongDistance = isLongDistance(origin, destination, longDistanceCities);

            if (isLongDistance) {
                return random.nextInt(3) == 0 ? "bus" : "flight"; // Flights are more common for long distances
            } else {
                return random.nextInt(2) == 0 ? "car" : "train"; // Car and train for short distances
            }
        }

        // Checks if the route is long distance
        private static boolean isLongDistance(String origin, String destination, String[] longDistanceCities) {
            for (String city : longDistanceCities) {
                if (origin.equals(city) || destination.equals(city)) {
                    return true;
                }
            }
            return false;
        }

        // Generates price and duration based on transport type
        private static int[] generatePriceAndDuration(String type, String origin, String destination, Random random) {
            int price;
            int duration;

            switch (type) {
                case "flight":
                    price = 3000 + random.nextInt(7000); // Flights are expensive
                    duration = 60 + random.nextInt(180); // Flights are fast, between 1 to 3 hours
                    break;
                case "train":
                    price = 1000 + random.nextInt(3000); // Train is moderately priced
                    duration = 300 + random.nextInt(600); // Train takes longer (5 to 10 hours)
                    break;
                case "bus":
                    price = 500 + random.nextInt(1500); // Buses are cheap
                    duration = 300 + random.nextInt(600); // Buses take long (5 to 10 hours)
                    break;
                case "car":
                    price = 1000 + random.nextInt(5000); // Car price can vary a lot
                    duration = 180 + random.nextInt(300); // Car takes 3 to 8 hours
                    break;
                default:
                    price = 0;
                    duration = 0;
                    break;
            }

            return new int[] { price, duration };
        }
    }


