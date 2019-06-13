package prototype.other.generic.genericMethod;

public class StaticF { //01
    public static<T> T someStaticMethod(T genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}
//� ����������������� ������ ��������� ������������ generic � ����������� ���������;
class ClazzStaticF<T2> { //02
    public static<T> T someStaticMethod(T /*T2*/ genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

//no have inform BUT CAN!!
class ClazzStatic2F2<T2> { //03
    public static<T2> T2 someStaticMethod(T2 /*T2*/ genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

//STATIC CLASS WITHOUT <F> = NOT LIFE!!!!
//STATIC CLASS WITH <F> BUT IS LEFT! what type is not egual type (return or argument) = NOT LIFE!!!!
//
// NOT WORK !!!
//class StaticClazz2Arg2F<T2> { //04
//    public static<T> T someStaticMethod(T2 genericObject) {
//        return null;
//    }
//}
// NOT WORK !!!
//class StaticClazz<T> { //05
//    public static T someStaticMethod(T genericObject) {
//        System.out.println(genericObject);
//        return genericObject;
//    }
//}
// NOT WORK !!!
//class StaticClazz2F<T2> { //06
//    public static<T> T2 someStaticMethod(T2 genericObject) {
//        System.out.println(genericObject);
//        return genericObject;
//    }
//}
// NOT WORK !!!
//class StaticClazz2Return2F<T2> { //07
//    public static<T> T2 someStaticMethod(T genericObject) {
//        return null;
//    }
//}
//---------------------------------------------------------------
class F{ //1
    public<T> T someStaticMethod(T genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

class Clazz<T> { //2
    public T someStaticMethod(T genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

class Clazz2F<T2> { //3
    public<T> T someStaticMethod(T /*T2*/genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

class Clazz2FArg2<T2> { //4
    public<T> T2 someStaticMethod(T2 genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}

//no have inform BUT CAN!!
class Clazz2F2Arg2<T2> { //0
    public<T2> T2 someStaticMethod(T2 genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}