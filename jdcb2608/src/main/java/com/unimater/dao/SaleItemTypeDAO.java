package com.unimater.dao;

import com.unimater.model.SaleItemType;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class SaleItemTypeDAO extends GenericDAOImpl<SaleItemType> {

    private final String TABLE_NAME = "sale_item_type";
    private final List<String> COLUMNS = Arrays.asList("description");

    public SaleItemTypeDAO(Connection connection) {
        super(SaleItemType::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
    }
}
