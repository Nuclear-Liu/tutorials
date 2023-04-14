package org.hui.websocket.server.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> maps = new HashMap<>();

        if (Objects.isNull(queryString) || queryString.equals("")) {
            return maps;
        }

        String[] kvs = queryString.split("&");
        String[] kvItem;
        for (String item : kvs) {
            kvItem = item.split("=");
            if (Objects.nonNull(kvItem) && kvItem.length == 2) {
                maps.put(kvItem[0], kvItem[1]);
            }
        }
        return maps;
    }
}
