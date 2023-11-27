package org.hui.recommendations.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.recommendations.services.FlashSaleRecommendationClient;
import org.hui.recommendations.topology.exception.Timeout;

import java.util.List;
import java.util.Map;

public class FindRecommendedSales extends BaseBasicBolt {
    public static final String RETRY_STREAM = "retry";
    public static final String SUCCESS_STREAM = "success";
    private FlashSaleRecommendationClient client;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        long timeout = (long) stormConf.get("timeout");
        client = new FlashSaleRecommendationClient((int) timeout);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String customerId = input.getStringByField("customer");
        try {
            List<String> sales = client.findSalesFor(customerId);
            if (!sales.isEmpty()) {
                collector.emit(SUCCESS_STREAM, new Values(customerId, sales));
            }
        } catch (Timeout e) {
            collector.emit(RETRY_STREAM, new Values(customerId));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(SUCCESS_STREAM, new Fields("customer", "sales"));
        declarer.declareStream(RETRY_STREAM, new Fields("customer"));
    }
}
