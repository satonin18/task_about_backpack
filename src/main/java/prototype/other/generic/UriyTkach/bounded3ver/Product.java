package prototype.other.generic.UriyTkach.bounded3ver;

//public abstract class Product implements Comparable { //Object
//public abstract class Product<T> implements Comparable<Product> {
//public abstract class Product<T> implements Comparable<T>{ //include String-Integer-... +//NOT->phone.compareTo(camera)
//public abstract class Product<T extends Product> implements Comparable<T>{ //WARNING ON down subVER//need insert Product
public abstract class Product<T extends Product<T>> implements Comparable<T>{ //���������� ���������� ����//� can init ONLY PARENT
    double price;

    abstract void subCompare(T o);

//    public int compareTo(Object o) { return 0; }
//    public int compareTo(Product o) { return 0; }
    @Override
    public int compareTo(T o) { return 0; }

    //�������� ���������� � ���������� �� ������ ����������!!!
//    @RealOverride by Compilator
//    public int compareTo(Object o) {
//        return toString().compareTo(o.toString());
//    }
}
