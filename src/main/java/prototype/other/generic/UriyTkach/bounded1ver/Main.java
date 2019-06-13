package prototype.other.generic.UriyTkach.bounded1ver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 02.02.2019.
 */
public class Main {
    public static void main(String[] args) {
        List<Camera> cameras = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        findDifferent(cameras, new Camera());
        findDifferent(cameras, new Phone());
        findDifferent(cameras, new Product());
        findDifferent(products, new Product());

        findEgual(cameras, new Camera());
        //findEgual( cameras, new Phone() );
        //findEgual( cameras, new Product() );
        findEgual( products, new Product() );

        findWithAtributExt(cameras, new Camera());
        findWithAtributExt(cameras, new Phone());
        findWithAtributExt(cameras, new Product());
        findWithAtributExt(products, new Product());

    }
    public static
    boolean findDifferent(List<? extends Product> all,
                          Product p) {
        for (Product sp: all) {
            if (sp.equals(p)) return true;
        }
            return false;
    }

    public static <T extends Product>
    boolean findEgual( List<T> all, T p) {
        for (Product sp: all) {
            if (sp.equals(p)) return true;
        }
        return false;
    }

    public static <T extends Product>
    boolean findWithAtributExt(List<? extends Product> all, T p) {
        for (Product sp: all) {
            if (sp.equals(p)) return true;
        }
        return false;
    }

}
