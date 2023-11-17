package org.hui.authorization.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.hui.authorization.Order;
import org.hui.authorization.services.NotificationService;

import java.util.Map;

public class ProcessedOrderNotification extends BaseBasicBolt {
    private NotificationService notificationService;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        notificationService = new NotificationService();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Order order = (Order) input.getValueByField("order");
        notificationService.notifyOrderHasBeenProcessed(order);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // nothing to declare
    }
}
