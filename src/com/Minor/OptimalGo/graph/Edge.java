package com.Minor.OptimalGo.graph;
 enum TransportType {
    BUS,
    TRAIN,
    FLIGHT,
    CAR,
}
public class Edge {
    int destinationIndex;
    TransportType transportType; // enum for transport type
    int price;
    int duration;

    public Edge(int destinationIndex, TransportType transportType, int price, int duration) {
        this.destinationIndex = destinationIndex;
        this.transportType = transportType;
        this.price = price;
        this.duration = duration;
    }
}
