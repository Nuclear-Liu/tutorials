package org.hui.topology;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichSpout;

public class HeatmapTopologyBuilder {
    public static final String TOPOLOGY_NAME = "realtime-heatmap";
    public static final String CHECKINS_ID = "checkins";
    public static final String HEATMAP_BUILDER_ID = "heatmap-builder";
    public static final String GEOCODE_LOOKUP_ID = "geocode-lookup";
    public static final String PERSISTOR_ID = "persistor";

    public static StormTopology build() {
        return buildWithSpout(CHECKINS_ID, new Checkins());
    }

    public static StormTopology buildWithSpout(String spoutId, BaseRichSpout spout) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(spoutId, spout);
        builder.setBolt(GEOCODE_LOOKUP_ID, new GeocodeLookup()).shuffleGrouping(spoutId);
        builder.setBolt(HEATMAP_BUILDER_ID, new HeatmapBuilder())
                .addConfiguration(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 3)
                .globalGrouping(GEOCODE_LOOKUP_ID);
        builder.setBolt(PERSISTOR_ID, new Persistor()).shuffleGrouping(HEATMAP_BUILDER_ID);
        return builder.createTopology();
    }
}
