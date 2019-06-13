package prototype.other.generic;

import java.util.ArrayList;

// <? extends ...>=varLevel
// READ all         //(var type change level Extended!)
// WRITE ONLY null //because Extend-Level can be up real object

// <? super   ...>=constLevel
// READ ONLY Object!
// WRITE all

public class Golovach{
    public static void main(String[] args) throws Exception {
        //1 Ex
        // BAD MASIV TYPE EXTENDS (concret->universal type)
        Object oi = new Integer(5); //because so cast type LIFE!!!
        Object[] oiMas = new Integer[10];
        //java.lang.ArrayStoreException: java.lang.String
        // NOT CHECK COMPILATOR!!! ONLY RunTime!!!
        //oiMas[0] = "String";

        //2 Ex
        //LIST GENERIC(started before 5 version) IS BETTER (but not the most best)
        //COMPILATOR SEE ERROR(concret->universal type) (IT is yet better)!
        //ArrayList<Object> ioList = new ArrayList<Integer>();
//--------------------------------------------------------------------------------------------------------------
        //3.1 extends
        ArrayList<Integer> tempRefer = new ArrayList<Integer>();tempRefer.add(311);//test1 for take value[0]
        ArrayList<? extends Integer> xExtendIn = tempRefer;//= new ArrayList<Integer>(); BUT with value[0]
        //xExtendIn.add(7);
        xExtendIn.add(null);
        Integer iexI = xExtendIn.get(0); // i take T!
        System.out.println(xExtendIn);

        ArrayList<Integer> tempRefer2 = new ArrayList<Integer>();tempRefer2.add(312);//test2 for take value[0]
        ArrayList<? extends Object> xExtend0b = tempRefer2;//= new ArrayList<Integer>(); BUT with value[0]
        //xExtend0b.add(7);
        xExtend0b.add(null);
        Object iexO = xExtend0b.get(0);// Can Be WARNING!! if not true USE!!!
        System.out.println(xExtend0b);

        //COMPILATOR SEE ERROR(Integer != String)
        //ArrayList<? extends String> xExtendStr = new ArrayList<Integer>();
//----------------------------------------------------------------------------------------------------------------
        //3.2 super
        //not working, need reference real Object
        //ArrayList<? super Object> xSuperOb = new ArrayList<Integer>();

        ArrayList<? super Integer> xSuperIn = new ArrayList<Integer>();
        xSuperIn.add(320);
        xSuperIn.add(null);
        //xSuperIn.add("String");
        //xSuperIn.add(new Object());
        //xSuperIn.add(new Double(3.14));
        //COMPILATOR SAYS:"I dont know concretn type what is <? super Integer>"
        Integer isupI =(Integer) xSuperIn.get(0);//because return Object
        System.out.println(xSuperIn);

        System.out.println("//----------------------------------------------------------");

        String[] masStr = new String[10];
        Integer[] masInt = new Integer[10];
        //masS story null and because not ERROR
        copyObj(masInt, masStr);// BAD MASIV TYPE EXTENDS (concret->universal type)
        System.out.println("First f(x) = End sucsesfull");
        masInt[0] = 666;
        System.out.println("Second f(x) = watch Exception on next line =)");
        copyObj(masInt, masStr); // NOT CHECK COMPILATOR!!! ONLY in RunTime will be ERROR!!!

        System.out.println("//----------------------------------------------------------");

        //5.1 work!(auto and self)
        ArrayList<Integer> integers = new ArrayList<Integer>();
        ArrayList<Object> objects = new ArrayList<Object>();
        integers.add(0, new Integer(1)); objects.add(0, new Object());
        // I <T> in method NOT write //COMPILATOR autoWrite self
        copyGeneric(integers, objects); //Work!!! T=Int // Int <= T < Object
        // OR I write
        Golovach.<Integer>copyGeneric(integers, objects); //Work!!!

        //5.2 no work!
        ArrayList<String> strings = new ArrayList<String>();
        //ERROR ON LEVEL COMPILATOR // I <T> in method NOT write
        //copyGeneric(integers, strings); //NO WORK T=? // Int <= T < String
        //-----------------------------------------------------------------------------------------------
    }
    //Generic on level-METHOD
    public static<T>
    void copyGeneric(ArrayList<? extends T> objFrom, ArrayList<? super T> objTo) throws Exception {
        //if(objFrom.size() != objTo.size()) throw new Exception("size no equals");
        try {
            for (int i = 0; i < objFrom.size(); i++) {
                objTo.add(objFrom.get(i));
            }
        } catch (Exception e){
            e.printStackTrace(System.out);
            return;
        }
    }
    public static void copyObj(Object[] oFrom, Object[] oTo) throws Exception {
        if(oFrom.length != oTo.length) throw new Exception("lenhgt no equals");
        try {
            for (int i = 0; i < oFrom.length; i++) {
                oTo[i] = oFrom[i];
            }
        } catch (Exception e){
            e.printStackTrace(System.out);
            return;
        }
    }
}
