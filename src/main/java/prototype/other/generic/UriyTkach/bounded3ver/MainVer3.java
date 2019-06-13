package prototype.other.generic.UriyTkach.bounded3ver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class MainVer3 {
    //test
    public static void main(String[] args) throws Exception {
        Stream.of(Product.class.getDeclaredMethods())
                .forEach(System.out::println);
//        Method ml = Product.class.getMethod("compareTo", Product.class);
//        Method m2 = Product.class.getMethod("compareTo", Object.class);
//        System.out.println(ml.isSynthetic()); //false
//        System.out.println(m2.isSynthetic()); //Object//true-//it method was generated Compilator
        System.out.println("//------------------------------------------------");

        new Phone().subCompare(new Phone());
        //new Phone().subCompare(new Camera());

        new Phone().compareTo(new Phone());
//        new Phone().compareTo(new Camera()); //is good!

//        new Dummy().compareTo(new Dummy()); // BAD!!!

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Phone());
        products.add(new Phone());
        Collections.sort(products);
        System.out.println(products);
    }
}
