package com.Minor.OptimalGo.header;

import java.util.ArrayList;
import java.util.List;

public class RadixHeap {

    private List<List<Node>> buckets;
    private int numberOfCities;
    private int maxKey;
    private int minKey;

    // Node structure to represent city and cost
    public class Node {
        public int cityIndex;
        public int key;

        public Node(int cityIndex, int key) {
            this.cityIndex = cityIndex;
            this.key = key;
        }
    }

    public RadixHeap(int numberOfCities) {
        this.numberOfCities = numberOfCities;
        buckets = new ArrayList<>();
        for (int i = 0; i < numberOfCities; i++) {
            buckets.add(new ArrayList<>());
        }
        maxKey = Integer.MIN_VALUE;
        minKey = Integer.MAX_VALUE;
    }

    // Insert into RadixHeap
    public void insert(int cityIndex, int key) {
        minKey = Math.min(minKey, key);
        maxKey = Math.max(maxKey, key);

        int bucketIndex = getBucketIndex(key);
        //edge cases
        if (bucketIndex < 0 || bucketIndex >= buckets.size()) {
            throw new IndexOutOfBoundsException("Bucket index out of bounds: " + bucketIndex);
        }
        buckets.get(bucketIndex).add(new Node(cityIndex, key));
    }

    // Extract minimum from RadixHeap
    public Node extractMin() {
        int bucketIndex = findNonEmptyBucket();
        if (bucketIndex == -1) {
            return null;
        }
        Node minNode = null;
        for (Node node : buckets.get(bucketIndex)) {
            if (minNode == null || node.key < minNode.key) {
                minNode = node;
            }
        }
        buckets.get(bucketIndex).remove(minNode);
        return minNode;
    }
    // Find the first non-empty bucket
    private int findNonEmptyBucket() {
        for (int i = 0; i < buckets.size(); i++) {
            if (!buckets.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    private int getBucketIndex(int key) {
        return Math.min(Math.max(0, key - minKey), buckets.size() - 1);
    }
    // Check if the radix heap is empty
    public boolean isEmpty() {
        for (List<Node> bucket : buckets) {
            if (!bucket.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
