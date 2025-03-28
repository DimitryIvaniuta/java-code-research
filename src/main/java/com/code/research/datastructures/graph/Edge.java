package com.code.research.datastructures.graph;

/**
 * Represents a weighted directed edge in the graph.
 *
 * @param <V> the vertex type
 */
public class Edge<V> {

    /**
     * The destination vertex of this edge.
     */
    private final V destination;

    /**
     * The weight of this edge.
     */
    private final double weight;

    /**
     * Constructs an edge with the specified destination and weight.
     *
     * @param destination the destination vertex
     * @param weight      the weight of the edge
     */
    public Edge(V destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the destination vertex.
     *
     * @return the destination vertex
     */
    public V getDestination() {
        return destination;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }
}