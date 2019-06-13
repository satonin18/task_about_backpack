/* //Вызов статического метода
Измени статический метод в классе GenericStatic так, чтобы он стал дженериком.
Пример вызова дан в методе main.
Требования:
1. Метод someStaticMethod в классе GenericStatic должен быть изменен в соответствии с условием задачи.
2. Метод someStaticMethod должен быть статическим.
3. Метод someStaticMethod должен быть публичным.
4. Метод someStaticMethod должен выводить данные на экран.
*/
package prototype.other.generic.genericMethod;

import java.lang.Number;

// LAST STRING THE DIFICAULT-est!!!
// class-level type parameters T... canNOT be used in a static context (static method).
//And finally Static method always needs explicit <T> declaration;
// It wont derive from class level Class<T>. This is because of Class level T is bound with instance.
//---------------------------------------------------------------------------------------------------------------
class GenericCheck {
    public static void main(String[] args) { Number number = null;
        number = StaticF.someStaticMethod(new Integer(3));
//        number = StaticF.<?>someStaticMethod(new Integer(3));
//        number = StaticF.<Object>someStaticMethod(new Integer(3));
        number = StaticF.<Number>someStaticMethod(new Integer(3));

        // can be not write <Number> up line //Compilator Auto Write Type from argument Type
        Integer iAutoTypeGeneric = StaticF.someStaticMethod(new Integer(3));

        number = ClazzStaticF.someStaticMethod(new Integer(3));
        number = ClazzStaticF.<Number>someStaticMethod(new Integer(3));

        //STATIC CLASS WITHOUT <F> = NOT LIFE!!!!
        //STATIC CLASS WITH <F> BUT IS LEFT! what type is not egual type (return or argument) = NOT LIFE!!!!
        //---------------------------------------------------------------------------------------
        number = new F().someStaticMethod(new Integer(3));
        number = new F().<Number>someStaticMethod(new Integer(3));
//        number = new F<Number>().someStaticMethod(new Integer(3));
//        number = new F<Number>().<Number>someStaticMethod(new Integer(3));

        // CLEAR GENERIC IF no init generic objectLevel!
//        number = new Clazz().someStaticMethod(new Integer(3));
//        number = new Clazz().<Number>someStaticMethod(new Integer(3));
        number = new Clazz<Number>().someStaticMethod(new Integer(3));
        number = new Clazz<Number>().<Number>someStaticMethod(new Integer(3));

        // CLEAR GENERIC IF no init generic objectLevel!
//        number = new Clazz2F().someStaticMethod(new Integer(3));
//        number = new Clazz2F().<Number>someStaticMethod(new Integer(3));
        number = new Clazz2F<Number>().someStaticMethod(new Integer(3));
        number = new Clazz2F<Number>().<Number>someStaticMethod(new Integer(3));

        // CLEAR GENERIC IF no init generic objectLevel!
//        number = new Clazz2FArg2().someStaticMethod(new Integer(3));
//        number = new Clazz2FArg2().<Number>someStaticMethod(new Integer(3));
        number = new Clazz2FArg2<Number>().someStaticMethod(new Integer(3));
        number = new Clazz2FArg2<Number>().<Number>someStaticMethod(new Integer(3));
    }
}
