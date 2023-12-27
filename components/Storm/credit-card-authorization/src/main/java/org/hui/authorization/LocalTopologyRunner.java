package org.hui.authorization;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;
import org.hui.authorization.topology.AuthorizeCreditCard;
import org.hui.authorization.topology.ProcessedOrderNotification;
import org.hui.authorization.topology.Spout;
import org.hui.authorization.topology.VerifyOrderStatus;

public class LocalTopologyRunner {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("file-based-spout",new Spout());

        builder.setBolt("verify-order-status", new VerifyOrderStatus())
                .shuffleGrouping("file-based-spout");

        builder.setBolt("authorize-order", new AuthorizeCreditCard())
                .shuffleGrouping("verify-order-status");

        builder.setBolt("accepted-notification", new ProcessedOrderNotification())
                .shuffleGrouping("authorize-order");

        Config config = new Config();
        config.setDebug(true);

        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("credit-card-topology",config,builder.createTopology());

        Utils.sleep(600_000);
        localCluster.shutdown();
    }
}
