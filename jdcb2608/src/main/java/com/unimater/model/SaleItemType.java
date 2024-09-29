package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleItemType implements Entity {

    private int id;
    private String description;

    public SaleItemType(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
    }

    public SaleItemType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public SaleItemType() {
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        return new SaleItemType(rs);
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, getDescription());
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "SaleItemType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}