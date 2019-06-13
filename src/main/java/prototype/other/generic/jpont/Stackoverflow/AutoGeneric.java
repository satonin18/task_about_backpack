package prototype.other.generic.jpont.Stackoverflow;

import java.util.*;

public class AutoGeneric {
    public static void main(String[] args) {
        // auto type = class Anonim extend String
        //                          implements List<Integer>{}
        String s =  newList(); // why does this line compile? // in RunTime = ClassCastException
        System.out.println(s);
    }
    private static <T extends List<Integer>> T newList() {
        return (T) new ArrayList<Integer>();
    }
}

class SelfWrite {
    public static <X extends String & List<Integer>>  void main(String[] args) {
        String s =  SelfWrite.<X>newList();
        System.out.println(s);
    }
    private static <T extends List<Integer>> T newList() {
        return (T) new ArrayList<Integer>();
    }
}

class WithError<T extends List<Integer>> {
    public void run() {
//        String s = newList(); // this doesn't compile anymore
//        System.out.println(s);
    }
    private T newList() {
        return (T) new ArrayList<Integer>();
    }
}