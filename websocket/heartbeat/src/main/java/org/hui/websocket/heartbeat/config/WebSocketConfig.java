package org.hui.websocket.heartbeat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 首先注入 {@link ServerEndpointExporter} ，这个 Bean 会自动注册使用了 {@link javax.websocket.server.ServerEndpoint} 注解声明的 WebSocket Endpoint.
 * <p>
 * 如果使用独立的 servlet 容器，而不是使用 Spring Boot 内置容器，不需要注入 {@link ServerEndpointExporter} ，因为它需要独立容器提供管理.
 */
@Configuration
public class WebSocketConfig {
    @Bean
    ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
