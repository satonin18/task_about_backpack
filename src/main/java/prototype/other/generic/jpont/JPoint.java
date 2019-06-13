package prototype.other.generic.jpont;

import java.lang.Number;
import java.util.ArrayList;
import java.util.List;

//  <? extends Object> = <?>

//https://www.youtube.com/watch?v=_0c9Fd9FacU
public class JPoint {
    public static void main(String[] args) throws Exception {

    }
}

class JPointExtend {
    public static void main(String[] args) throws Exception {
        //Compilator ������� ���� � ���������� ��������� �� ��� ����� ������ � LEFT PART �� ����������( ��� REFERENCE)
        // ��� ����� ����������
        List<? extends Number> numbers ;//= ????
        // ��� ����� ��������� //ANY TYPE <? extends Number>
        numbers = new ArrayList<Number>();
        numbers = new ArrayList<Integer>();
        numbers = new ArrayList<Long>();
        numbers = new ArrayList<Double>();
    }
                        //ANY TYPE <? extends Number>//EX:<Float>
    public void processtExtend(List<? extends Number> numbers) {
        //Compilator ERROR
        //numbers.add(234L);//not for Float
        numbers.add(null);
        Number number = numbers.get(0);
    }
}
class JPointSuper {
    public static void main(String[] args) throws Exception {
        //Compilator ������� ���� � ���������� ��������� �� ��� ����� ������ � LEFT PART �� ����������( ��� REFERENCE)
        // ��� ����� ����������
        List<? super Number> numbers ; //=????
        // ��� ����� ���������  //ANY TYPE <? super Number>
        numbers = new ArrayList<Object>();
        numbers = new ArrayList<Number>();
    }
                      //ANY TYPE <? super Number>
    public void processSuper(List<? super Number> numbers) {
        numbers.add(234L);
        numbers.add(100D);
        numbers.add(null);
        //numbers.add(new Number() {...MANY TEXT...});
        //Compilator ERROR
        //numbers.add(new Object()); //not for Number
    }
}


