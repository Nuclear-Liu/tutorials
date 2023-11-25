package org.hui.authorization.topology;

import com.google.gson.Gson;
import org.apache.storm.shade.org.apache.commons.io.IOUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.hui.authorization.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Spout extends BaseRichSpout {
    private static final Logger logger = LoggerFactory.getLogger(Spout.class);
    private SpoutOutputCollector collector;
    private List<Order> orders;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("orders.txt")) {
            List<String> jsons = IOUtils.readLines(inputStream, Charset.defaultCharset().name());
            orders = convertJsons(jsons);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Order> convertJsons(List<String> jsons) {
        List<Order> orders = new ArrayList<>(jsons.size());
        Gson gson = new Gson();
        jsons.forEach(json -> {
            Order order = gson.fromJson(json, Order.class);
            orders.add(order);
        });
        return orders;
    }

    @Override
    public void nextTuple() {
        orders.forEach(order -> collector.emit(new Values(order)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("order"));
    }

    @Override
    public void ack(Object msgId) {
        super.ack(msgId);
    }

    @Override
    public void fail(Object msgId) {
        super.fail(msgId);
    }
}
