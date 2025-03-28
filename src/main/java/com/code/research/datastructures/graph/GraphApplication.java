package com.code.research.datastructures.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GraphApplication {

    /**
     * Demonstrates the usage of the Graph class and Dijkstra's algorithm.
     * This example creates a sample graph and computes the shortest paths from vertex 0.
     *
     * Graph diagram:
     * <pre>
     *         (4)
     *     0 ------> 1
     *     |         |
     *   (1)|         |(1)
     *     v         v
     *     2 ------> 3
     *      \ (2)  /
     *       ---->
     *        (5)
     * </pre>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<>();
        // Build a sample graph.
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 5);

        // Compute the shortest paths from vertex 0.
        Map<Integer, Double> distances = graph.dijkstra(0);

        log.info("Shortest distances from vertex 0:");
        for (Map.Entry<Integer, Double> entry : distances.entrySet()) {
            log.info("Vertex {}: {}", entry.getKey(), entry.getValue());
        }
    }

}
