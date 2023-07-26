package org.hui;

import com.google.common.graph.*;

public class GraphsExplained {
    public static void main(String[] args) {
        ImmutableGraph<String> immutableGraph = GraphBuilder.undirected()
                .allowsSelfLoops(true)
                .<String>immutable()
                .putEdge("bread", "bread")
                .putEdge("chocolate", "peanut butter")
                .putEdge("peanut butter", "jelly")
                .build();
        System.out.println(immutableGraph);

        // creating mutable graphs
        MutableGraph<Integer> graph = GraphBuilder.<Integer>undirected().build();

        MutableValueGraph<String, Double> roads = ValueGraphBuilder
                .directed()
                .incidentEdgeOrder(ElementOrder.stable())
                .build();

        // creating an immutable graph
        ImmutableGraph<Integer> graph1 = GraphBuilder
                .undirected()
                .<Integer>immutable()
                .putEdge(1,2)
                .putEdge(1,3)
                .putEdge(2,3)
                .addNode(4)
                .build();

    }
}
