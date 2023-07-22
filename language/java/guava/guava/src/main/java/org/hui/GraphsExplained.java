package org.hui;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

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
    }
}
