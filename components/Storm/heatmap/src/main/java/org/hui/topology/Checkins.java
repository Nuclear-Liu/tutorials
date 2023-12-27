package org.hui.topology;

import org.apache.commons.io.IOUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class Checkins extends BaseRichSpout {
    private Map conf;
    private TopologyContext context;
    private SpoutOutputCollector collector;
    private List<String> checkins;
    private int nextEmitIndex;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.conf = conf;
        this.context = context;
        this.collector = collector;
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("checkins.txt")) {
            checkins = IOUtils.readLines(inputStream, Charset.defaultCharset().name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.nextEmitIndex = 0;
    }

    @Override
    public void nextTuple() {
        String checkin = checkins.get(nextEmitIndex);
        String[] parts = checkin.split(",");
        Long time = Long.valueOf(parts[0]);
        String address = parts[1];
        collector.emit(new Values(time, address));

        /* 避免溢出 */
        nextEmitIndex = (nextEmitIndex + 1) % checkins.size();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("time", "address"));
    }
}
