package org.hui.topology;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.topology.domain.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HeatmapBuilder extends BaseBasicBolt {
    private Map<Long, List<LatLng>> heatmaps;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        heatmaps = new ConcurrentHashMap<>();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config config = new Config();
        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 60);
        return config;
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        if (isTickTuple(input)) {
            emitHeatmap(collector);
        } else {
            Long time = input.getLongByField("time");
            LatLng geocode = (LatLng) input.getValueByField("geocode");

            Long timeInterval = selectTimeInterval(time);
            List<LatLng> checkins = getCheckinsForInterval(timeInterval);
            checkins.add(geocode);
        }
    }

    private void emitHeatmap(BasicOutputCollector outputCollector) {
        Long now = System.currentTimeMillis();
        Long emitUpToTimeInterval = selectTimeInterval(now);
        Set<Long> timeIntervalsAvailable = heatmaps.keySet();
        for (Long timeInterval : timeIntervalsAvailable) {
            if (timeInterval <= emitUpToTimeInterval) {
                List<LatLng> hotzones = heatmaps.remove(timeInterval);
                outputCollector.emit(new Values(timeInterval, hotzones));
            }
        }
    }

    private boolean isTickTuple(Tuple tuple) {
        String sourceComponent = tuple.getSourceComponent();
        String sourceStreamId = tuple.getSourceStreamId();
        return sourceComponent.equals(Constants.SYSTEM_COMPONENT_ID) && sourceStreamId.equals(Constants.SYSTEM_TICK_STREAM_ID);
    }

    private List<LatLng> getCheckinsForInterval(Long timeInterval) {
        return heatmaps.computeIfAbsent(timeInterval, k -> new ArrayList<>());
    }

    private Long selectTimeInterval(Long time) {
        return time / (15 * 1000);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("time-interval", "hotzones"));
    }
}
