package com.app.despoliation.entities;

public class Thing {
    private String title;
    private int weight;
    private int price;

    public static class Builder {
        private String title;
        private int weight;
        private int price;

        public Builder title(String t) {
            this.title = t; return this;
        }
        public Builder weight(int w) {
            this.weight = w; return this;
        }
        public Builder price(int p) {
            this.price = p; return this;
        }

        public Thing build() {
            return new Thing(this);
        }
    }

    public Thing(Builder b) {
        setTitle(b.title);
        setWeight(b.weight);
        setPrice(b.price);
    }

    public void setTitle(String title) {
        this.title = (title == null) ? "" : title;
    }
    public void setWeight(int weight) {
        this.weight = (weight <= 0) ? 1 : weight;
    }
    public void setPrice(int price) {
        this.price = (price <= 0) ? 0 : price;
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

    @Override
    public String toString() {
        return "Thing{" +
                "title='" + title + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
