package org.hui.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.hui.topology.domain.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatmapBuilder extends BaseBasicBolt {
    private Map<Long, List<LatLng>> heatmaps;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        heatmaps = new HashMap<>();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Long time = input.getLongByField("time");
        LatLng geocode = (LatLng) input.getValueByField("geocode");

        Long timeInterval = selectTimeInterval(time);
        List<LatLng> checkins = getCheckinsForInterval(timeInterval);
        checkins.add(geocode);
    }

    private List<LatLng> getCheckinsForInterval(Long timeInterval) {
        List<LatLng> hotzones = heatmaps.get(timeInterval);
        if (hotzones == null) {
            hotzones = new ArrayList<>();
            heatmaps.put(timeInterval, hotzones);
        }
        return hotzones;
    }

    private Long selectTimeInterval(Long time) {
        return time / (15 * 1000);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
