package com.Minor.OptimalGo.graph;
 enum TransportType {
    BUS,
    TRAIN,
    FLIGHT,
     CAR
}
public class Edge {
    int destinationIndex;
    TransportType transportType; // enum for transport type
    String[] attraction;
    int price;
    int duration;

    public Edge(int destinationIndex, TransportType transportType, int price, int duration,String[] attraction) {
        this.destinationIndex = destinationIndex;
        this.attraction=attraction;
        this.transportType = transportType;
        this.price = price;
        this.duration = duration;
    }
}
