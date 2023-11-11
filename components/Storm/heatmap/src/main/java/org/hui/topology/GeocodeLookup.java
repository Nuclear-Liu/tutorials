package org.hui.topology;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.hui.topology.domain.LatLng;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class GeocodeLookup extends BaseBasicBolt {
    private OkHttpClient httpClient;
    private HttpUrl.Builder urlBuilder;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        httpClient = new OkHttpClient();
        urlBuilder = HttpUrl.get("https://api.map.baidu.com/geocoding/v3/")
                .newBuilder()
                .addQueryParameter("output", "json")
                .addQueryParameter("ak", "ak");
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String address = input.getStringByField("address");
        Long time = input.getLongByField("time");

        urlBuilder.addQueryParameter("address", address);

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        LatLng latLng = new LatLng();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.code() == 200) {
                JsonMapper jsonMapper = new JsonMapper();
                JsonNode jsonNode = jsonMapper.readTree(response.body().string());
                latLng = jsonMapper.convertValue(jsonNode.get("result").get("location"), LatLng.class);
            }
        } catch (IOException e) {
            System.out.println(e);
            // throw new RuntimeException(e);
            latLng.setLng(BigDecimal.ZERO);
            latLng.setLat(BigDecimal.ZERO);
        }
        String city = new String("city");
        collector.emit(new Values(time, latLng, city));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("time", "geocode", "city"));
    }
}
