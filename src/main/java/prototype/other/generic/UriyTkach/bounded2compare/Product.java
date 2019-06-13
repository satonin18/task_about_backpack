package prototype.other.generic.UriyTkach.bounded2compare;

public abstract class Product<T> implements Comparable<Product>{
    double price;

    abstract
    void subCompare(T o);

    @Override
    public int compareTo(Product o) {
        return 0;
    }

    //�������� ���������� � ���������� �� ������ ����������!!!
//    @RealOverride by Compilator
//    public int compareTo(Object o) {
//        return toString().compareTo(o.toString());
//    }
}
