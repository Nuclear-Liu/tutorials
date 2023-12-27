package org.hui.recommendations.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.ReportedFailedException;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.hui.recommendations.domain.Sale;
import org.hui.recommendations.services.DatabaseClient;
import org.hui.recommendations.topology.exception.Timeout;

import java.util.List;
import java.util.Map;

public class SaveRecommendedSales extends BaseBasicBolt {
    private final static int TIMEOUT = 50;
    private DatabaseClient dbClient;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        dbClient = new DatabaseClient(TIMEOUT);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String customerId = input.getStringByField("customer");
        List<Sale> sales = (List<Sale>) input.getValueByField("sales");

        try {
            dbClient.save(customerId, sales);
        } catch (Timeout e) {
            throw new ReportedFailedException(e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //
    }
}
