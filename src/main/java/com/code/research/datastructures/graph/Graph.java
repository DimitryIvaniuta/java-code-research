package com.code.research.datastructures.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Represents a weighted directed graph using an adjacency list.
 *
 * @param <V> the type of vertices in the graph
 */
@Slf4j
public class Graph<V> {

    /**
     * The adjacency list mapping each vertex to its list of outgoing edges.
     */
    private final Map<V, List<Edge<V>>> adjList;

    /**
     * Constructs an empty graph.
     */
    public Graph() {
        this.adjList = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Adds a directed edge from source to destination with the specified weight.
     * If the vertices do not exist, they are added to the graph.
     *
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param weight      the weight of the edge
     */
    public void addEdge(V source, V destination, double weight) {
        addVertex(source);
        addVertex(destination);
        adjList.get(source).add(new Edge<>(destination, weight));
    }

    /**
     * Computes the shortest distances from the start vertex to all other vertices
     * using Dijkstra's algorithm.
     *
     * @param start the starting vertex
     * @return a map from each vertex to its shortest distance from the start vertex.
     *         If a vertex is unreachable, the distance will be Double.POSITIVE_INFINITY.
     */
    public Map<V, Double> dijkstra(V start) {
        Map<V, Double> distances = new HashMap<>();
        // Initialize distances to infinity.
        for (V vertex : adjList.keySet()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);

        // Priority queue ordered by the current shortest distance.
        PriorityQueue<Pair<V, Double>> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue));
        minHeap.add(new Pair<>(start, 0.0));

        while (!minHeap.isEmpty()) {
            Pair<V, Double> current = minHeap.poll();
            V u = current.getKey();
            double currentDist = current.getValue();

            // Skip if we already found a better path.
            if (currentDist > distances.get(u)) {
                continue;
            }

            // Iterate over all adjacent edges.
            for (Edge<V> edge : adjList.getOrDefault(u, Collections.emptyList())) {
                V v = edge.getDestination();
                double newDist = distances.get(u) + edge.getWeight();
                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    minHeap.add(new Pair<>(v, newDist));
                }
            }
        }
        return distances;
    }

    /**
     * Returns a list of all vertices in the graph.
     *
     * @return a list of vertices
     */
    public List<V> getVertices() {
        return new ArrayList<>(adjList.keySet());
    }

}
