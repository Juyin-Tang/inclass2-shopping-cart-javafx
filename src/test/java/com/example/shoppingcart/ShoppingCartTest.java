package com.example.shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ShoppingCartTest {
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void testAddItemAndCalculateTotal() {
        cart.addItem(10.0, 2);   // 20.0
        cart.addItem(5.5, 3);    // 16.5
        assertEquals(36.5, cart.calculateTotal(), 0.001);
    }

    @Test
    void testEmptyCartTotal() {
        assertEquals(0.0, cart.calculateTotal(), 0.001);
    }

    @Test
    void testSingleItemTotal() {
        cart.addItem(7.25, 4);   // 29.0
        assertEquals(29.0, cart.calculateTotal(), 0.001);
    }

    @Test
    void testItemTotalCalculation() {
        ShoppingCart.Item item = new ShoppingCart.Item(3.5, 3);
        assertEquals(10.5, item.getTotal(), 0.001);
    }

    @Test
    void testClearCart() {
        cart.addItem(1.0, 1);
        cart.clear();
        assertEquals(0.0, cart.calculateTotal(), 0.001);
    }



}