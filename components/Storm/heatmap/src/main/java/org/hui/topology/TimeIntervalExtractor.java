package org.hui.topology;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.topology.domain.LatLng;

public class TimeIntervalExtractor extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Long time = input.getLongByField("time");
        LatLng geocode = (LatLng) input.getValueByField("geocode");
        String city = input.getStringByField("city");

        long timeInterval = time / (15 * 1_000);
        collector.emit(new Values(timeInterval, geocode, city));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("time-interval", "geocode", "city"));
    }
}
