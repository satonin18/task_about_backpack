package com.app.despoliation.threads.thief;

import com.app.despoliation.Thing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Алгоритм взят из книги (ниже) - на 216 стр (задача о рюкзаке - стр206-235)
 * Бхаргава А.
 * Грокаем алгоритмы. Иллюстрированное пособие для программистов и любопытствующих.
 * - СПб.: Питер, 2017. - 288 с. : ил. - (Серия «Библиотека программиста»).
 *
 */
public class SelectionThing4Backpack {
    public static List<Thing> select(int maxLimitedThing, List<Thing> things) {
        final int COLUMNS = maxLimitedThing;  // COLUMNS = from 0 to WEIGHT-1

        // TODO (ONLY FOR FUTURE CHANGES) need add check type on stage compilation App. VarS : MAX WEIGHT and LINES = MUST BE BOTH EQUALLY VAR (here INT)
        final int LINES = things.size();  // THINGS = from 0 to lastThing

        if (LINES == 0 || COLUMNS == 0) {
            //System.out.println("    EMPTY_LIST, NOTHING STEAL");
            return Collections.EMPTY_LIST;
        }
        List<List<Thing>> currentLine = null;
        List<List<Thing>> pastLine = null;

        for (int indexThing = 0; indexThing < LINES; indexThing++) {
            pastLine = currentLine;
            currentLine = new ArrayList<List<Thing>>(COLUMNS);
            Thing thisThing = things.get(indexThing);

            for (int indexColumn = 0; indexColumn < COLUMNS; indexColumn++) {
                List<Thing> thisCell = new ArrayList<Thing>(1); // for ecomomy memory
                currentLine.add(indexColumn, thisCell);
                List<Thing> sumOf_thisTh_and_free = new ArrayList<Thing>();

                int biasKoffWeight = indexColumn+1;
                int differ = biasKoffWeight - thisThing.getWeight();
                if( differ >= 0 ) {
                    sumOf_thisTh_and_free.add(thisThing);
                    if(indexThing >= 1 && differ > 0 ){
                        assert  (differ >= COLUMNS) : "KOFF not true for mas[][koff]";
                        sumOf_thisTh_and_free.addAll(pastLine.get(differ - 1));
                    }
                }
                if(indexThing >= 1){
                    thisCell.addAll(
                            getListWithMaxPrice(sumOf_thisTh_and_free, pastLine.get(indexColumn))
                    );
                } else {
                    thisCell.addAll(sumOf_thisTh_and_free);
                }
                 //System.out.print(thisCell + "\t");
            }
            //System.out.println(currentLine.get(COLUMNS-1));
        }
        //System.out.println();

        List<Thing> cell = currentLine.get(COLUMNS-1);
        if(cell.isEmpty()) {  return Collections.EMPTY_LIST;}
        return cell;
    }

    private static List<Thing> getListWithMaxPrice(List<Thing> list1, List<Thing> list2) {
        Thing maxPrice = null;
        int price1=0;
        for (Thing th : list1) {
            price1+=th.getPrice();
        }
        int price2=0;
        for (Thing th : list2) {
            price2+=th.getPrice();
        }
        return (price1 > price2) ? list1 : list2;
    }
}
