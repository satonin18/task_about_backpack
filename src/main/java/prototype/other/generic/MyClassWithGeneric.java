package prototype.other.generic;

class MyClassWithGeneric<T> {
    //Type declaration <T> already done at class level
    private  T myMethod(T a){
        return  a;
    }

    //<T> is overriding the T declared at Class level;
    //So There is no ClassCastException though a(=1=english little word) is not the type of T declared at MyClass<T>.
    private <T> T myMethod10(Object a){
        return (T) a;
    }

    //Runtime ClassCastException will be thrown if a(=1=english little word) is not the type T (MyClass<T>).
    private T myMethod1(Object a){
        return (T) a;
    }

    // No ClassCastException
    // MyClass<String> obj= new MyClass<String>();
    // obj.myMethod2(Integer.valueOf("1"));
    // Since type T is redefined at this method level.
    private <T> T myMethod2(T a){
        return  a;
    }

    // No ClassCastException for the below
    // MyClass<String> o= new MyClass<String>();
    // o.myMethod30(Integer.valueOf("1").getClass())
    // Since <T> is undefined within this method;
    // And MyClass<T> don't have impact here
    private <T> T myMethod30(Class a){
        return (T) a;
    }

    // ClassCastException for o.myMethod3(Integer.valueOf("1").getClass())
    // Should be o.myMethod3(String.valueOf("1").getClass())
    private T myMethod3(Class a){
        return (T) a;
    }

    // Class<T> a :: a is Class object of type T
    //<T> is overriding of class level type declaration;
    private <T>/*new*/
    Class<T>/*TfromClass*/
    myMethod4(Class<T>/*???*/ a){
        return a;
    }
}