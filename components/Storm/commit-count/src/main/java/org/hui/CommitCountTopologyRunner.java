package org.hui;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.hui.topology.CommitFeeder;
import org.hui.topology.EmailCounter;
import org.hui.topology.EmailExtractor;

public class CommitCountTopologyRunner {
    private static final int TEN_MINUTES = 600000;

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("commit-feeder", new CommitFeeder());

        builder.setBolt("email-extractor", new EmailExtractor())
                .shuffleGrouping("commit-feeder");

        builder.setBolt("email-counter", new EmailCounter())
                .fieldsGrouping("email-extractor", new Fields("email"));

        Config config = new Config();
        config.setDebug(true);

        StormTopology commitCountTopology = builder.createTopology();

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("commit-count-topology", config, commitCountTopology);

        Utils.sleep(TEN_MINUTES);
        cluster.killTopology("github-commit-count-topology");
        cluster.shutdown();
    }
}