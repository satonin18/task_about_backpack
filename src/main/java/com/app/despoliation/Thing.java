package com.app.despoliation;

import java.util.Objects;

public class Thing {
    private String title;
    private int weight;
    private int price;

    public Thing(String title, int weight, int price) {
        setTitle(title);
        setWeight(weight);
        setPrice(price);
    }

    public Thing(int weight, int price) {
        this("", weight, price);
    }

    public String getTitle() {
        return title;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWeight(int weight) {
        this.weight = (weight <= 0) ? 1 : weight;
    }

    public void setPrice(int price) {
        this.price = (price <= 0) ? 0 : price;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "title='" + title + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
