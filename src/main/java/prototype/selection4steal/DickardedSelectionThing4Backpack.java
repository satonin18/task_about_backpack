package prototype.selection4steal;

import com.app.despoliation.threads.thief.Backpack;
import com.app.despoliation.Thing;
import com.app.despoliation.threads.thief.SelectionThing4Backpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DickardedSelectionThing4Backpack {

    private List<Thing> findOptimalThings4Backpack(List<Thing> things) throws Exception {
        SelectionThing4Backpack selection = new SelectionThing4Backpack();
//        return
        List<Thing> temp1 =
                selection.select(new Backpack(4_000).getWeightLimit(), things);
        int countPriceTemp1 =0;
        //System.out.println("Method 1");
        for (Thing th: temp1) {
            countPriceTemp1 += th.getPrice();
            //System.out.println(th);
        }

        DickardedSelectionThing4Backpack selection2 = new DickardedSelectionThing4Backpack();
//        return
        List<Thing> temp2 =
                selection2.select_ByN_permission_weigth(new Backpack(4_000).getWeightLimit(), things, 100);
        //System.out.println("Method 2");
        int countPriceTemp2 =0;
        for (Thing th: temp2) {
            countPriceTemp2 += th.getPrice();
            //System.out.println(th);
        }

        if(countPriceTemp1 != countPriceTemp2) {throw new Exception("electionThing4Backpack with Permission has fail");}
        else {
            System.out.println("OK");
            return temp1;
        }
    }

    public List<Thing> select_ByN_permission_weigth(int maxLimitedBackpack, List<Thing> things, int permission) throws Exception {
        if(permission <=0) throw new Exception("permission need >= 1");

        // TODO (more effective) SCALA COMPUTING CAN CHANGE, take only 100 грамм
        //final int MULTIPLIER_REAL_WEIGHT = 1; // no use, in REAL WEIGHT

        final int COLUMNS = maxLimitedBackpack;  // COLUMNS = from 0 to WEIGHT-1

        // MAX WEIGHT and LINES = MUST BE BOTH EQUALLY VAR (here INT)
        final int LINES = ((int) Math.ceil((double)things.size() / permission));  // THINGS = from 0 to lastThings
        System.out.println("2)="+things.size());

        if (LINES == 0 || COLUMNS == 0) {
            System.out.println("    EMPTY_LIST");
            return Collections.EMPTY_LIST;
        }
        List<List<Thing>> currentLine = new ArrayList<List<Thing>>(COLUMNS);
        List<List<Thing>> pastLine = null; //for first iter - going prototype.other algoritm

        for (int indexThing = 0; indexThing < LINES; indexThing++) {
            pastLine = currentLine;
            currentLine = new ArrayList<List<Thing>>(COLUMNS);
            Thing thisThing = things.get(indexThing);

            for (int indexColumn = 0; indexColumn < COLUMNS; indexColumn++) {
                List<Thing> thisCell = new ArrayList<Thing>(1); // for ecomomy memory
                currentLine.add(indexColumn, thisCell);
                List<Thing> sum_thisTh_and_free = new ArrayList<Thing>();//by index: 1-this, 0...1-free4backpack things

                int biasKoffWeight = (indexColumn+1)*permission;
                int differ = biasKoffWeight - thisThing.getWeight();//MULTIPLIER_REAL_WEIGHT;
                if( differ >= 0 ) {
                    sum_thisTh_and_free.add(0,thisThing);
                    if(indexThing >= 1 && differ > 0 ){
                        assert  (differ >= COLUMNS) : "KOFF not true for mas[][koff]";
                        sum_thisTh_and_free.addAll(pastLine.get(((int) Math.ceil((double)differ / permission))-1) );
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
