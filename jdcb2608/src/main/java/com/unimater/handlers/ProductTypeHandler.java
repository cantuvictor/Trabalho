package com.unimater.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.unimater.annotations.HttpRequestHandler;
import com.unimater.dao.ProductTypeDAO;
import com.unimater.model.ProductType;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ProductTypeHandler extends AbstractResourceHandler<ProductType, ProductTypeDAO> {

    public ProductTypeHandler(Connection connection) {
        super(new ProductTypeDAO(connection));
    }


    @Override
    protected void upsert(ProductType productType) {
        dao.upsert(productType);
    }

    @Override
    protected void delete(int id) {
        dao.delete(id);
    }

    @Override
    protected Class<ProductType> getEntityClass() {
        return ProductType.class;
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
