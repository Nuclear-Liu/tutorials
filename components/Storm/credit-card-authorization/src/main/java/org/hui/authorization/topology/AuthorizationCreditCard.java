package org.hui.authorization.topology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.FailedException;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.authorization.Order;
import org.hui.authorization.services.AuthorizationService;
import org.hui.authorization.services.OrderDao;

import java.util.Map;

public class AuthorizationCreditCard extends BaseRichBolt {
    private Map stormConf;
    private TopologyContext context;
    private OutputCollector collector;

    private AuthorizationService authorizationService;
    private OrderDao orderDao;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.stormConf = stormConf;
        this.context = context;
        this.collector = collector;
        orderDao = new OrderDao();
        authorizationService = new AuthorizationService();
    }

    @Override
    public void execute(Tuple input) {
        Order order = (Order) input.getValueByField("order");
        try {
            boolean isAuthorized = authorizationService.authorize(order);
            if (isAuthorized) {
                orderDao.updateStatusToReadyToShip(order);
            } else {
                orderDao.updateStatusToDenied(order);
            }
            collector.emit(input, new Values(order));
            collector.ack(input);
        } catch (FailedException e) {
            collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("order"));
    }
}
