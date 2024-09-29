package com.unimater.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.unimater.annotations.HttpRequestHandler;
import com.unimater.dao.GenericDAOImpl;
import com.unimater.helper.HttpHandlerHelper;
import com.unimater.model.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class AbstractResourceHandler<T extends Entity, DAO extends GenericDAOImpl<T>> implements HttpHandler {

    protected final DAO dao;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public AbstractResourceHandler(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(HttpRequestHandler.class)) {
                HttpRequestHandler httpMethod = m.getAnnotation(HttpRequestHandler.class);
                if (httpMethod.value().equalsIgnoreCase(method)) {
                    try {
                        m.invoke(this, exchange);
                        return;
                    } catch (Exception e) {
                        HttpHandlerHelper.sendResponse(exchange, "Internal Server Error", 500, objectMapper);
                        return;
                    }
                }
            }
        }

        HttpHandlerHelper.handleMethodNotAllowed(exchange, objectMapper);
    }

    @HttpRequestHandler("GET")
    public void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathSegments = path.split("/");

        if (pathSegments.length != 3) {
            HttpHandlerHelper.sendResponse(exchange, "Invalid request", 400, objectMapper);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(pathSegments[2]);
        } catch (NumberFormatException e) {
            HttpHandlerHelper.sendResponse(exchange, "Invalid ID format", 400, objectMapper);
            return;
        }

        T entity = dao.getById(id);
        if (entity == null) {
            HttpHandlerHelper.sendResponse(exchange, "Resource not found", 404, objectMapper);
        } else {
            HttpHandlerHelper.sendResponse(exchange, entity, 200, objectMapper);
        }
    }

    @HttpRequestHandler("POST")
    public void handlePost(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        try {
            T entity = objectMapper.readValue(inputStream, getEntityClass());
            upsert(entity);
            HttpHandlerHelper.sendResponse(exchange, "Resource created successfully", 201, objectMapper);
        } catch (IOException e) {
            HttpHandlerHelper.sendResponse(exchange, "Failed to parse request body", 400, objectMapper);
        } catch (Exception exception) {
            HttpHandlerHelper.sendResponse(exchange, "Failed to create resource", 500, objectMapper);
        }
    }

    @HttpRequestHandler("PUT")
    public void handlePut(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        try {
            T entity = objectMapper.readValue(inputStream, getEntityClass());
            upsert(entity);
            HttpHandlerHelper.sendResponse(exchange, "Resource updated successfully", 200, objectMapper);
        } catch (IOException e) {
            HttpHandlerHelper.sendResponse(exchange, "Failed to parse request body", 400, objectMapper);
        } catch (Exception exception) {
            HttpHandlerHelper.sendResponse(exchange, "Failed to update resource", 500, objectMapper);
        }
    }

    @HttpRequestHandler("DELETE")
    public void handleDelete(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = HttpHandlerHelper.parseQuery(query);
        String id = params.get("id");

        if (id == null) {
            HttpHandlerHelper.sendResponse(exchange, "ID parameter is required", 400, objectMapper);
            return;
        }

        try {
            delete(Integer.parseInt(id));
            HttpHandlerHelper.sendResponse(exchange, "Resource deleted successfully", 200, objectMapper);
        } catch (NumberFormatException e) {
            HttpHandlerHelper.sendResponse(exchange, "Invalid ID format", 400, objectMapper);
        } catch (Exception exception) {
            HttpHandlerHelper.sendResponse(exchange, "Failed to delete resource", 500, objectMapper);
        }
    }

    protected abstract void upsert(T entity);

    protected abstract void delete(int id);

    protected abstract Class<T> getEntityClass();

}
