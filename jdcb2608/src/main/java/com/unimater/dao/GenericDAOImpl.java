package com.unimater.dao;


import com.unimater.model.Entity;
import com.unimater.model.ProductType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    protected Connection connection;
    protected String tableName;

    protected List<String> columns;
    private Supplier<T> supplier;


    public GenericDAOImpl(Supplier<T> supplier, Connection connection) {
        this.supplier = supplier;
        this.connection = connection;
    }

    @Override
    public List<T> getAll() {
        List<T> productTypes = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()) {
                T productType = (T) supplier.get().constructFromResultSet(rs);
                productTypes.add(productType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypes;
    }

    @Override
    public void upsert(T object) {
        try {
            if (object.getId() == 0) {
                // Inserção
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "
                        + tableName
                        + " ("
                        + columns.stream().collect(Collectors.joining(", "))
                        + ") VALUES ("
                        + columns.stream().map(item -> "?").collect(Collectors.joining(", "))
                        +")", Statement.RETURN_GENERATED_KEYS);
                pstmt = object.prepareStatement(pstmt);
                pstmt.executeUpdate();

                // Obtém o ID gerado
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    object.setId(generatedKeys.getInt(1));
                }
            } else {
                // Atualização
                PreparedStatement pstmt = connection.prepareStatement("UPDATE "
                        + tableName
                        + " SET "
                        + columns.stream().map(item -> item + " = ?").collect(Collectors.joining(", "))
                        + " WHERE id = ?");
                pstmt = object.prepareStatement(pstmt);
                pstmt.setInt(columns.size() + 1, object.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public T getById(int id) {
        T object = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                object = (T) supplier.get().constructFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement pstmt =
                    connection.prepareStatement("DELETE FROM "
                            + tableName
                            + " WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
