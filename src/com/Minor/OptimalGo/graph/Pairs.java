package com.Minor.OptimalGo.graph;

public class Pairs {
    int cityIndex;
    int duration;  // Duration for the fastest path
    int price;     // Price for the cheapest route

    public Pairs(int cityIndex, int duration, int price) {
        this.cityIndex = cityIndex;
        this.duration = duration;  // Assign duration first
        this.price = price;        // Assign price second
    }
}
