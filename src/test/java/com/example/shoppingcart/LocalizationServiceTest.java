package com.example.shoppingcart;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalizationServiceTest {
    private static Connection h2Conn;
    private MockedStatic<DatabaseConnection> mockedStatic;

    @BeforeAll
    static void initDatabase() throws SQLException {
        h2Conn = DriverManager.getConnection("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;NON_KEYWORDS=VALUE");
        try (Statement stmt = h2Conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS localization_strings");
            stmt.execute("CREATE TABLE localization_strings (" +
                    "id INT PRIMARY KEY, " +
                    "`key` VARCHAR(255), " +
                    "value VARCHAR(255), " +
                    "language VARCHAR(10))");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (1, 'language.label', 'Language', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (2, 'item.count.prompt', 'Number of items:', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (3, 'confirm.button', 'Confirm', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (4, 'calculate.button', 'Calculate', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (5, 'result.ready', 'Ready', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (6, 'item.prefix', 'Item {0}', 'en')");
            stmt.execute("INSERT INTO localization_strings (id, `key`, value, language) VALUES (7, 'cart.total', 'Total: {0}', 'en')");
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
    void testLoadLanguageAndGetMessage() {
        LocalizationService service = new LocalizationService();
        service.loadLanguage("en");
        assertEquals("Language", service.getMessage("language.label"));
        assertEquals("Number of items:", service.getMessage("item.count.prompt"));
        assertEquals("Confirm", service.getMessage("confirm.button"));
        assertEquals("Calculate", service.getMessage("calculate.button"));
        assertEquals("Ready", service.getMessage("result.ready"));
    }

    @Test
    void testUnknownKeyReturnsPlaceholder() {
        LocalizationService service = new LocalizationService();
        service.loadLanguage("en");
        assertEquals("!unknown_key!", service.getMessage("unknown_key"));
    }


}