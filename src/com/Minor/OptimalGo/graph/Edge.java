package com.Minor.OptimalGo.graph;

public class Edge {
    int destinationIndex;
    String typeOfTransport;
    int price;
    int duration;

    public Edge(int destinationIndex, String typeOfTransport, int price, int duration) {
        this.destinationIndex = destinationIndex;
        this.typeOfTransport = typeOfTransport;
        this.price = price;
        this.duration = duration;
    }
}