package com.unimater.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.unimater.annotations.HttpRequestHandler;
import com.unimater.dao.SaleTypeDAO;
import com.unimater.model.SaleType;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class SaleHandler extends AbstractResourceHandler<SaleType, SaleTypeDAO> {

    public SaleHandler(Connection connection) {
        super(new SaleTypeDAO(connection));
    }


    @Override
    protected void upsert(SaleType entity) {
        dao.upsert(entity);
    }

    @Override
    protected void delete(int id) {
        dao.delete(id);
    }

    @Override
    protected Class<SaleType> getEntityClass() {
        return SaleType.class;
    }

    @Override
    @HttpRequestHandler("GET")
    public void handleGet(HttpExchange exchange) throws IOException {
        super.handleGet(exchange);
    }

    @Override
    @HttpRequestHandler("POST")
    public void handlePost(HttpExchange exchange) throws IOException {
        super.handlePost(exchange);
    }

    @Override
    @HttpRequestHandler("PUT")
    public void handlePut(HttpExchange exchange) throws IOException {
        super.handlePut(exchange);
    }

    @Override
    @HttpRequestHandler("DELETE")
    public void handleDelete(HttpExchange exchange) throws IOException {
        super.handleDelete(exchange);
    }

}
