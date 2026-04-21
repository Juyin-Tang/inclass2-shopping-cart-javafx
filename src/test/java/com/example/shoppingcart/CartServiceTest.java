package com.example.shoppingcart;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {
    private static Connection h2Conn;
    private MockedStatic<DatabaseConnection> mockedStatic;

    @BeforeAll
    static void initDatabase() throws SQLException {
        h2Conn = DriverManager.getConnection("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        try (Statement stmt = h2Conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS cart_items");
            stmt.execute("DROP TABLE IF EXISTS cart_records");
            stmt.execute("CREATE TABLE cart_records (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "total_items INT, " +
                    "total_cost DOUBLE, " +
                    "language VARCHAR(10))");
            stmt.execute("CREATE TABLE cart_items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "cart_record_id INT, " +
                    "item_number INT, " +
                    "price DOUBLE, " +
                    "quantity INT, " +
                    "subtotal DOUBLE)");
        }
    }

    @AfterAll
    static void closeDatabase() throws SQLException {
        if (h2Conn != null) h2Conn.close();
    }

    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(DatabaseConnection.class);
        mockedStatic.when(DatabaseConnection::getConnection).thenReturn(h2Conn);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    void testSaveCart() {
        CartService service = new CartService();
        List<CartItem> items = Arrays.asList(
                new CartItem(1, 10.0, 2, 20.0),
                new CartItem(2, 5.5, 3, 16.5)
        );
        assertDoesNotThrow(() -> service.saveCart(2, 36.5, "en", items));
    }

    @Test
    void testSaveCartEmptyItems() {
        CartService service = new CartService();
        assertDoesNotThrow(() -> service.saveCart(0, 0.0, "fi", List.of()));
    }
}