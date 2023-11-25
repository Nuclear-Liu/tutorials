package org.hui.authorization.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.authorization.Order;
import org.hui.authorization.services.OrderDao;

import java.util.Map;

public class VerifyOrderStatus extends BaseBasicBolt {
    private OrderDao orderDao;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        orderDao = new OrderDao();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Order order = (Order) input.getValueByField("order");

        if (orderDao.isNotReadyToShip(order)) {
            collector.emit(new Values(order));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("order"));
    }
}
