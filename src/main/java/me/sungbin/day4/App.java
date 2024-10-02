package me.sungbin.day4;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Item> items = Arrays.asList(new Item("햄버거", 10000), new Item("피자", 30000));
        Order order = new Order(items, "양성빈");

        System.out.println(order.validateOrder());
    }
}
