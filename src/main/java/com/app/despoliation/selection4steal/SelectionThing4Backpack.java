package com.app.despoliation.selection4steal;

import com.app.despoliation.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SelectionThing4Backpack {
    public List<Thing> select(int maxLimitedBackpack, List<Thing> things) {
        // TODO (more effective) SCALA COMPUTING CAN CHANGE, take only 100 грамм
        //final int MULTIPLIER_REAL_WEIGHT = 1; // no use, in REAL WEIGHT

        final int COLUMNS = maxLimitedBackpack;  // COLUMNS = from 0 to WEIGHT-1

        // MAX WEIGHT and LINES = MUST BE BOTH EQUALLY VAR (here INT)
        final int LINES = things.size();  // THINGS = from 0 to lastThings

        if (LINES == 0 || COLUMNS == 0) {
            System.out.println("    EMPTY_LIST");
            return Collections.EMPTY_LIST;
        }
        List<List<Thing>> currentLine = new ArrayList<List<Thing>>(COLUMNS);
        List<List<Thing>> pastLine = null; //for first iter - going other algoritm

        for (int indexThing = 0; indexThing < LINES; indexThing++) {
            pastLine = currentLine;
            currentLine = new ArrayList<List<Thing>>(COLUMNS);
            Thing thisThing = things.get(indexThing);

            for (int indexColumn = 0; indexColumn < COLUMNS; indexColumn++) {
                List<Thing> thisCell = new ArrayList<Thing>(1); // for ecomomy memory
                currentLine.add(indexColumn, thisCell);
                List<Thing> sum_thisTh_and_free = new ArrayList<Thing>();//by index: 1-this, 0...1-free4backpack things

                int biasKoffWeight = indexColumn+1;
                int differ = biasKoffWeight - thisThing.getWeight();//MULTIPLIER_REAL_WEIGHT;
                if( differ >= 0 ) {
                    sum_thisTh_and_free.add(0,thisThing);
                    if(indexThing >= 1 && differ > 0 ){
                        assert  (differ >= COLUMNS) : "KOFF not true for mas[][koff]";
                        sum_thisTh_and_free.addAll(pastLine.get(differ-1) );
                    }
                }
                if(indexThing >= 1){
                    thisCell.addAll(
                            getListWithMaxPrice(sum_thisTh_and_free, pastLine.get(indexColumn))
                    );
                } else {
                    thisCell.addAll(sum_thisTh_and_free);
                }
                 //System.out.print(thisCell + "\t");
            }
            //System.out.println(currentLine.get(COLUMNS-1));
        }

        System.out.println();
        List<Thing> cell = currentLine.get(COLUMNS-1);
        System.out.println("    "+"Max Limit Backpack: "+ maxLimitedBackpack);
        System.out.println("    "+"Thief stole next things: ");
        if(cell.isEmpty()) {
            System.out.println("    "+"do not findED thing, which place in backpack");return Collections.EMPTY_LIST;}
        for (Thing th : cell) { System.out.println("    "+th);}
        System.out.println();
        return cell;
    }

    private  List<Thing> getListWithMaxPrice(List<Thing> list1, List<Thing> list2) {
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
