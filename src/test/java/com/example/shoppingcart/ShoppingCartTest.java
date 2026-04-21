package com.example.shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void testAddItemAndCalculateTotal() {
        cart.addItem(1.5, 2);
        cart.addItem(2.0, 3);
        assertEquals(9.0, cart.calculateTotal(), 1e-9);
    }

    @Test
    void testClear() {
        cart.addItem(10.0, 1);
        assertFalse(cart.getItems().isEmpty());
        cart.clear();
        assertTrue(cart.getItems().isEmpty());
        assertEquals(0.0, cart.calculateTotal(), 1e-9);
    }

    @Test
    void testEmptyCart() {
        assertEquals(0.0, cart.calculateTotal(), 1e-9);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testItemTotal() {
        cart.addItem(3.0, 4);
        ShoppingCart.Item item = cart.getItems().get(0);
        assertEquals(3.0, item.getPrice(), 1e-9);
        assertEquals(4, item.getQuantity());
        assertEquals(12.0, item.getTotal(), 1e-9);
    }
}