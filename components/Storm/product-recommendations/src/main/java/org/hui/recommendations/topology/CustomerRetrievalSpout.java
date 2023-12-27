package org.hui.recommendations.topology;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class CustomerRetrievalSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private final Random idGenerator = new Random();

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        int numberPart = idGenerator.nextInt(9_999_999) + 1;
        String customerId = "customer-" + numberPart;
        collector.emit(new Values(customerId));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("customer"));
    }
}
