package com.unimater.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.unimater.annotations.HttpRequestHandler;
import com.unimater.dao.SaleItemTypeDAO;
import com.unimater.model.SaleItemType;

import java.io.IOException;
import java.sql.Connection;

public class SaleItemHandler extends AbstractResourceHandler<SaleItemType, SaleItemTypeDAO> {

    public SaleItemHandler(Connection connection) {
        super(new SaleItemTypeDAO(connection));
    }


    @Override
    protected void upsert(SaleItemType entity) {
        dao.upsert(entity);
    }

    @Override
    protected void delete(int id) {
        dao.delete(id);
    }

    @Override
    protected Class<SaleItemType> getEntityClass() {
        return SaleItemType.class;
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
