package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

@Slf4j
class Dijkstra {

    // Inner class to represent an edge with a destination and weight.
    static class Edge {
        int destination;
        int weight;

        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Method to compute the shortest paths from a source vertex using Dijkstra's algorithm.
    public static int[] shortestPath(Map<Integer, List<Edge>> graph, int source, int numVertices) {
        int[] distances = new int[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        // Priority queue stores the vertex along with the current distance.
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{source, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int u = current[0];
            int currentDist = current[1];

            // If we have already found a better path, skip.
            if (currentDist > distances[u]) continue;

            // Traverse adjacent vertices.
            for (Edge edge : graph.getOrDefault(u, new ArrayList<>())) {
                int v = edge.destination;
                int newDist = distances[u] + edge.weight;
                if (newDist < distances[v]) {
                    distances[v] = newDist;
                    queue.offer(new int[]{v, newDist});
                }
            }
        }
        return distances;
    }

    public static void main(String[] args) {
        // Constructing a sample graph.
        Map<Integer, List<Edge>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(new Edge(1, 4), new Edge(2, 1)));
        graph.put(1, List.of(new Edge(3, 1)));
        graph.put(2, Arrays.asList(new Edge(1, 2), new Edge(3, 5)));
        graph.put(3, new ArrayList<>());

        int numVertices = 4;
        int source = 0;
        int[] distances = shortestPath(graph, source, numVertices);

        log.info("Shortest distances from vertex {}: ", source);
        IntStream.range(0, distances.length)
                .forEach(i -> log.info("To {} : {}", i, distances[i]));
    }
}
