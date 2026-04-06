package com.example.shoppingcart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {
    private Map<String, String> messages = new HashMap<>();

    public void loadLanguage(String languageCode) {
        messages.clear();
        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, languageCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String key, Object... args) {
        String pattern = messages.get(key);
        if (pattern == null) return "!" + key + "!";
        return java.text.MessageFormat.format(pattern, args);
    }
}