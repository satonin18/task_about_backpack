package com.app.despoliation;

import java.util.Objects;

public class Thing {
    private int weight;
    private int price;
    private String title;

    public Thing(String title, int weight, int price) {
        this.title = title;
        this.weight = (weight <= 0) ? 1 : weight;
        this.price = price;
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

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "weight=" + weight +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }

}
