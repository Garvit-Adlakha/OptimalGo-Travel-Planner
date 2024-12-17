    package com.Minor.OptimalGo.graph;

    import java.util.*;

    public class Graph {
        private ArrayList<ArrayList<Edge>> adjList;
        private Map<String, Integer> cityIndexMap; // for o(1) lookup time
        public Map<String,String> abbreviationMap;
        private List<String> cities;
        private final int citiesSize;
        public Graph(int numberOfCities) {
            this.citiesSize = numberOfCities;
            adjList = new ArrayList<>(numberOfCities);
            abbreviationMap = new HashMap<>();
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
            if(cityName.length()<=3) cityName= abbreviationMap.getOrDefault(cityName,cityName);
            String finalCityName = cityName;
            return cityIndexMap.computeIfAbsent(cityName, key -> {
                cities.add(finalCityName);
                return cities.size() - 1;
            });
        }
        public int getCityIndex(String cityName) {
            if (cityName.length() <= 3) {
                cityName = abbreviationMap.getOrDefault(cityName, cityName);
            }
            return cityIndexMap.getOrDefault(cityName, -1);
        }
        public String getCityName(int cityIndex) {
            if (cityIndex >= 0 && cityIndex < cities.size()) {
                return cities.get(cityIndex);
            } else {
                throw new IllegalArgumentException("Invalid city index: " + cityIndex);
            }
        }
        public void addEdge(String sourceCity, String destinationCity, String typeOfTransport, int price, int duration, String[] attractions) {
            int sourceIndex = getCityIndex(sourceCity);
            int destinationIndex = getCityIndex(destinationCity);
            if (sourceIndex != -1 && destinationIndex != -1) {
                try {
                    TransportType transportTypeEnum = TransportType.valueOf(typeOfTransport.toUpperCase());
                    for (Edge edge : adjList.get(sourceIndex)) {
                        if (edge.destinationIndex == destinationIndex) {
                            System.out.println("🚨 Error: Edge already exists from " + sourceCity + " to " + destinationCity);
                            return;
                        }
                    }
                    adjList.get(sourceIndex).add(new Edge(destinationIndex, transportTypeEnum, price, duration, attractions));
//                    System.out.println("✅ Route successfully added from " + sourceCity + " to " + destinationCity);
                } catch (IllegalArgumentException e) {
                    System.out.println("🚨 Error: Invalid transport type '" + typeOfTransport + "' provided. Please use a valid type (BUS, TRAIN, FLIGHT, CAR).");
                }
            } else {
                if (sourceIndex == -1) {
                    System.out.println("🚨 Error: Source city '" + sourceCity + "' not found!");
                }
                if (destinationIndex == -1) {
                    System.out.println("🚨 Error: Destination city '" + destinationCity + "' not found!");
                }
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
                    return edge;
                }
            }
            return null;
        }
        public void printGraph() {
            System.out.println("\n🌍  --- Graph Connections ---  🌍");
            for (int i = 0; i < adjList.size(); i++) {
                System.out.print("🏙️  " + cities.get(i) + " -> ");
                if (adjList.get(i).isEmpty()) {
                    System.out.println("No direct connections.");
                } else {
                    System.out.println();
                    for (Edge edge : adjList.get(i)) {
                        System.out.println("    └─> [" + cities.get(edge.destinationIndex) +
                                " | " + edge.transportType.toString() +
                                " | ₹" + edge.price +
                                " | " + edge.duration + " mins]");
                    }
                }
            }
            System.out.println("-------------------------------\n");
        }
        public boolean containsCity(String cityName) {
            if (cityIndexMap.containsKey(cityName)) {
                return true;
            }
            String abbreviation = abbreviationMap.get(cityName);
            if (abbreviation != null && cityIndexMap.containsKey(abbreviation)) {
                return true;
            }
            return false;
        }
        public ArrayList<ArrayList<Edge>> getAdjList() {
            return adjList;
        }
    }
