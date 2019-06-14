package prototype.selection4steal;

public class PrototypeThing {
    private String title;
    private int weight;
    private int price;

    public PrototypeThing(String title, int weight, int price) {
        this.title = title;
        this.weight = weight;
        this.price = price;
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
