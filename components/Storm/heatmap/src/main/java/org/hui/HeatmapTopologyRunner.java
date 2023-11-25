package org.hui;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.hui.topology.HeatmapTopologyBuilder;

public class HeatmapTopologyRunner {
    private static final int TEN_MINUTES = 600_000;

    public static void main(String[] args) {
        Config config = new Config();
        config.setDebug(true);
        config.setNumWorkers(2);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(HeatmapTopologyBuilder.TOPOLOGY_NAME, config, HeatmapTopologyBuilder.build());

        // Utils.sleep(TEN_MINUTES);
        // cluster.killTopology(HeatmapTopologyBuilder.TOPOLOGY_NAME);
        // cluster.shutdown();
    }
}