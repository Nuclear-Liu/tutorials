package org.hui.topology;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;

public class HeatmapTopologyBuilder {
    public static final String TOPOLOGY_NAME = "realtime-heatmap";
    public static final String CHECKINS_ID = "checkins";
    public static final String TIME_INTERVAL_EXTRACTOR_ID = "time-interval-extractor";
    public static final String HEATMAP_BUILDER_ID = "heatmap-builder";
    public static final String GEOCODE_LOOKUP_ID = "geocode-lookup";
    public static final String PERSISTOR_ID = "persistor";

    public static StormTopology build() {
        return buildWithSpout(CHECKINS_ID, new Checkins());
    }

    public static StormTopology buildWithSpout(String spoutId, BaseRichSpout spout) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(spoutId, spout, 4);

        builder.setBolt(GEOCODE_LOOKUP_ID, new GeocodeLookup(), 8)
                .setNumTasks(64).shuffleGrouping(spoutId);

        builder.setBolt(TIME_INTERVAL_EXTRACTOR_ID, new TimeIntervalExtractor(), 4)
                .shuffleGrouping(GEOCODE_LOOKUP_ID);

        builder.setBolt(HEATMAP_BUILDER_ID, new HeatmapBuilder(), 4)
                .addConfiguration(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 3)
                .fieldsGrouping(TIME_INTERVAL_EXTRACTOR_ID, new Fields("time-interval", "city"));

        builder.setBolt(PERSISTOR_ID, new Persistor(), 1).setNumTasks(4).shuffleGrouping(HEATMAP_BUILDER_ID);

        return builder.createTopology();
    }
}
