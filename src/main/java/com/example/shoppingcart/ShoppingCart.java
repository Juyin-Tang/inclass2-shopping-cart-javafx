package com.example.shoppingcart;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {
    private List<Item> items = new ArrayList<>();


    public static class Item {
        private double price;
        private int quantity;

        public Item(double price, int quantity) {
            this.price = price;
            this.quantity = quantity;
        }

        public double getTotal() {
            return price * quantity;
        }

        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
    }


    public void addItem(double price, int quantity) {
        items.add(new Item(price, quantity));
    }


    public double calculateTotal() {
        return items.stream().mapToDouble(Item::getTotal).sum();
    }


    public List<Item> getItems() {
        return items;
    }


    public void clear() {
        items.clear();
    }
}