package com.example.shoppingcart;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    @Test
    void testConstructorAndGetters() {
        CartItem item = new CartItem(1, 2.5, 3, 7.5);
        assertEquals(1, item.getItemNumber());
        assertEquals(2.5, item.getPrice(), 1e-9);
        assertEquals(3, item.getQuantity());
        assertEquals(7.5, item.getSubtotal(), 1e-9);
    }

    @Test
    void testZeroValues() {
        CartItem item = new CartItem(0, 0.0, 0, 0.0);
        assertEquals(0, item.getItemNumber());
        assertEquals(0.0, item.getPrice(), 1e-9);
        assertEquals(0, item.getQuantity());
        assertEquals(0.0, item.getSubtotal(), 1e-9);
    }
}