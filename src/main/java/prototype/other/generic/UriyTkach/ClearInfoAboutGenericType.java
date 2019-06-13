package prototype.other.generic.UriyTkach;

/**
 * Created by HOME on 02.02.2019.
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClearInfoAboutGenericType {
public static void main(String[] args) {
        //EX 1 // not Generic Object-Level
        SomeType someType = new SomeType(); List<String> list = Arrays.asList("not Generic Object-Level");
        //RUNTIME ERROR
        //someType.test(list);

        //EX 2 // Generic Method-Level
        SomeType someTypeMethod = new SomeType(); list.set(0, "ONLY Generic Method-Level, WITHOUT Generic Object-Level");
        //RUNTIME ERROR
        //someTypeMethod.<String>test(list);

//        ClassCastException!
//        ������ ��� SomeType ������ ��� generic ����(Object-Level).
//        � ���������� ������, ���: (type = CLEAR)
//        public void test(Collection collection)
//        public void test(List integerList)
        //----------------------------------------------------------------------------------
        //EX 3 // Generic Object-Level
        SomeType<?> someTypeObject = new SomeType<>(); list.set(0, "Generic Object-Level");
        //OK
        someTypeObject.test(list);

        //EX 4 // Generic Object/Method-Level
        SomeType<?> someTypeObjectMethod = new SomeType<>(); list.set(0, "Generic Object/Method-Level");
        //OK
        someTypeObjectMethod.<String>test(list);
        }
        }

class SomeType<T> {
    public <E> void test(Collection<E> collection) {
        for (E e : collection) {
            System.out.println(e);
        }
    }

    public void test(List<Integer> integerList) {
        for (Integer integer : integerList) {
            System.out.println(integer);
        }
    }
}