package com.Minor.OptimalGo.graph;

public class Edge {
    int destination;
    String typeOfTransport;
    int price;
    int duration;

    public Edge(int destination, String typeOfTransport, int price, int duration) {
        this.destination = destination;
        this.typeOfTransport = typeOfTransport;
        this.price = price;
        this.duration = duration;
    }
}
