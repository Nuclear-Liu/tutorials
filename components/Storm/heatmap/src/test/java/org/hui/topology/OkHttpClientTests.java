package org.hui.topology;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hui.topology.domain.LatLng;

import java.io.IOException;

public class OkHttpClientTests {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder queryUrlBuilder = HttpUrl.get("https://api.map.baidu.com/geocoding/v3/").newBuilder();
        queryUrlBuilder.addQueryParameter("output", "json")
                .addQueryParameter("ak", "ak")
                .addQueryParameter("address", "浙江省杭州市西湖区湖滨路25号");

        Request request = new Request
                .Builder()
                .url(queryUrlBuilder.build()).build();

        try {
            Response response = client.newCall(request).execute();
            JsonMapper jsonMapper = new JsonMapper();
            JsonNode jsonNode = jsonMapper.readTree(response.body().string());
            LatLng latLng = jsonMapper.convertValue(jsonNode.get("result").get("location"), LatLng.class);

            System.out.println(latLng);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
