package prototype.other.generic.jpont;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * Created by HOME on 02.02.2019.
 */
public class TestMyComparable {
    public static void main(String[] args) throws NoSuchMethodException {
        Stream.of(Person.class.getDeclaredMethods())
                .forEach(System.out::println);
        Method ml = Person.class.getMethod("compareTo", Person.class);
        Method m2 = Person.class.getMethod("compareTo", Object.class);
        System.out.println(ml.isSynthetic()); //false
        System.out.println(m2.isSynthetic()); //Object//true-//it method was generated Compilator
    }
}

class Person implements Comparable<Person> {
    @Override
    public int compareTo(Person o) {
        return 0;
    }
    //Compile ERORR: BOTH METHOD have same erasure
    //�������� ���������� � ���������� �� ������ ����������!!!

//    @RealOverride by Compilator
//    public int compareTo(Object o) {
//        return toString().compareTo(o.toString());
//    }
}