package prototype.other.generic;

import java.util.ArrayList;

public class Covariance {
    //------------------------------------------------------------------------------
    //��-�������� (Object->Number)
    Object[] objectsMas = new Number[10];

    //��-�������� ArrayList<Object> != ArrayList<Number>
    //ArrayList<Object> objectsList = new ArrayList<Number>();
//--------------------------------------------------------------------------
    //��-��������
    ArrayList<? extends Object> objectsListExt = new ArrayList<Number>();

    //�����-��������
    ArrayList<? super Integer> objectsListSup = new ArrayList<Number>();
//----------------------------------------------------------------------------
}
