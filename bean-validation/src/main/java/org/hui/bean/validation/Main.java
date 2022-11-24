package org.hui.bean.validation;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8080);

        HttpServer server = HttpServer.create(address, 0);

        server.createContext("/", new ProcessHandler());
        server.setExecutor(Executors.newCachedThreadPool());

        server.start();


        LOGGER.info("server started: {}", address.getPort());

    }
}

class ProcessHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "application-json");
                exchange.sendResponseHeaders(200, 0);
                break;
            default:
                break;
        }
        //
    }
}