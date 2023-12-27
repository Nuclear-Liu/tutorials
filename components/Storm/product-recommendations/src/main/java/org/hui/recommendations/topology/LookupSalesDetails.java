package org.hui.recommendations.topology;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.recommendations.domain.Sale;
import org.hui.recommendations.metrics.MultiSuccessRateMetric;
import org.hui.recommendations.services.FlashSaleClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LookupSalesDetails extends BaseRichBolt {
    private final static int TIMEOUT = 100;
    private FlashSaleClient client;
    private OutputCollector collector;
    private final int METRICS_WINDOW = 15;
    private transient MultiSuccessRateMetric successRates;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        client = new FlashSaleClient(TIMEOUT);

        successRates = new MultiSuccessRateMetric();
        context.registerMetric("sales-lookup-success-rate", successRates, METRICS_WINDOW);
    }

    @Override
    public void execute(Tuple input) {
        String customerId = input.getStringByField("customer");
        List<String> salesIds = (List<String>) input.getValueByField("sales");

        List<Sale> sales = salesIds.stream().map(client::lookupSale).collect(Collectors.toList());

        if (sales.isEmpty()) {
            collector.fail(input);
        } else {
            successRates.scope(customerId).incrSuccess(sales.size());
            collector.emit(new Values(customerId, sales));
            collector.ack(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("customer", "sales"));
    }
}
