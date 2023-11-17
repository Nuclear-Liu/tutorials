package org.hui.authorization.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.authorization.Order;
import org.hui.authorization.services.AuthorizationService;
import org.hui.authorization.services.OrderDao;

import java.util.Map;

public class AuthorizationCreditCard extends BaseBasicBolt {
    private Map stormConf;
    private TopologyContext context;

    private AuthorizationService authorizationService;
    private OrderDao orderDao;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        this.stormConf = stormConf;
        this.context = context;
        orderDao = new OrderDao();
        authorizationService = new AuthorizationService();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Order order = (Order) input.getValueByField("order");
        boolean isAuthorized = authorizationService.authorize(order);
        if (isAuthorized) {
            orderDao.updateStatusToReadyToShip(order);
        } else {
            orderDao.updateStatusToDenied(order);
        }
        collector.emit(new Values(order));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("order"));
    }
}
