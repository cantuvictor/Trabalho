package com.unimater.dao;

import com.unimater.model.SaleType;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class SaleTypeDAO extends GenericDAOImpl<SaleType> {

    private final String TABLE_NAME = "sale_type";
    private final List<String> COLUMNS = Arrays.asList("description");

    public SaleTypeDAO(Connection connection) {
        super(SaleType::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
    }
}
