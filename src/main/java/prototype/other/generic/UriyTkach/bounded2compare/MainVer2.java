package prototype.other.generic.UriyTkach.bounded2compare;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Created by HOME on 02.02.2019.
 */
public class MainVer2 {
    //test
    public static void main(String[] args) throws Exception {
        Stream.of(Product.class.getDeclaredMethods())
                .forEach(System.out::println);
        Method ml = Product.class.getMethod("compareTo", Product.class);
        Method m2 = Product.class.getMethod("compareTo", Object.class);
        System.out.println(ml.isSynthetic()); //false
        System.out.println(m2.isSynthetic()); //Object//true-//it method was generated Compilator
        System.out.println("//------------------------------------------------");

        new Phone().subCompare(new Phone());
        //new Phone().subCompare(new Camera());

        new Phone().compareTo(new Phone());
        new Phone().compareTo(new Camera()); // BAD!!!

        new Dummy().compareTo(new Dummy()); // BAD!!!

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Phone());
        products.add(new Phone());
        Collections.sort(products);
        System.out.println(products);
    }
}
