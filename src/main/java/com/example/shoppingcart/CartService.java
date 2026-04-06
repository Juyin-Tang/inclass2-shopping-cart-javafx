package com.example.shoppingcart;

import java.sql.*;
import java.util.List;

public class CartService {
    public void saveCart(int totalItems, double totalCost, String language, List<CartItem> items) {
        Connection conn = null;
        PreparedStatement recordStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String recordSql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
            recordStmt = conn.prepareStatement(recordSql, Statement.RETURN_GENERATED_KEYS);
            recordStmt.setInt(1, totalItems);
            recordStmt.setDouble(2, totalCost);
            recordStmt.setString(3, language);
            recordStmt.executeUpdate();

            generatedKeys = recordStmt.getGeneratedKeys();
            int cartRecordId = -1;
            if (generatedKeys.next()) {
                cartRecordId = generatedKeys.getInt(1);
            }

            String itemSql = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
            itemStmt = conn.prepareStatement(itemSql);
            for (CartItem item : items) {
                itemStmt.setInt(1, cartRecordId);
                itemStmt.setInt(2, item.getItemNumber());
                itemStmt.setDouble(3, item.getPrice());
                itemStmt.setInt(4, item.getQuantity());
                itemStmt.setDouble(5, item.getSubtotal());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) {}
            try { if (recordStmt != null) recordStmt.close(); } catch (SQLException e) {}
            try { if (itemStmt != null) itemStmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}