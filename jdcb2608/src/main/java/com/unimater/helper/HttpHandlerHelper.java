package com.unimater.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpHandlerHelper {

    public static void handleMethodNotAllowed(HttpExchange exchange, ObjectMapper objectMapper) throws IOException {
        sendResponse(exchange, "Method not allowed", 405, objectMapper);
    }

    public static void sendResponse(HttpExchange exchange, Object response, int statusCode, ObjectMapper objectMapper) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(response);
        exchange.sendResponseHeaders(statusCode, jsonResponse.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(jsonResponse.getBytes());
        outputStream.close();
    }

    public static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 1) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

}
