package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleType implements Entity {

    private int id;
    private String description;

    public SaleType(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
    }

    public SaleType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public SaleType() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        return new SaleType(rs);
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, getDescription());
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "SaleType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
