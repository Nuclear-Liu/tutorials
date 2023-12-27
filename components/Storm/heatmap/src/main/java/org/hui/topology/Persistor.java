package org.hui.topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.hui.topology.domain.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Persistor extends BaseBasicBolt {
    private final Logger logger = LoggerFactory.getLogger(Persistor.class);
    private Jedis jedis;
    private ObjectMapper objectMapper;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        jedis = new Jedis("127.0.0.1", 6379);
        objectMapper = new ObjectMapper();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Long timeInterval = input.getLongByField("time-interval");
        List<LatLng> hz = (List<LatLng>) input.getValueByField("hotzones");
        List<String> hotzones = asListOfStrings(hz);

        try {
            String key = "checkins-" + timeInterval;
            String value = objectMapper.writeValueAsString(hotzones);
            logger.info("heatmap:{} {}", key, value);
            String set = jedis.set(key, value);
            System.out.println(set);
        } catch (JsonProcessingException e) {
            logger.error("Error persisting for time: " + timeInterval, e);
        }
    }

    private List<String> asListOfStrings(List<LatLng> hotzones) {
        return hotzones.stream().map(LatLng::toUrlValue).collect(Collectors.toList());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // No output fields to be declared
    }

    @Override
    public void cleanup() {
        if (jedis.isConnected()) {
            jedis.close();
        }
    }
}
