package prototype.selection4steal;

import com.app.despoliation.threads.thief.Backpack;

import java.util.*;

// TODO MAX WEIGHT IN INT VAR
// TODO SCALA COMPUTING CAN CHANGE, take only 100 грамм
public class Prototype2WithThings {
    static List<PrototypeThing> things;
    static Backpack backpack;
    static {
        things = new ArrayList<PrototypeThing>(
                Arrays.asList(new PrototypeThing[]{
                        new PrototypeThing("guitar", 1_000, 1500),
                        new PrototypeThing("magnetophon", 4_000, 3000),
                        new PrototypeThing("notebook", 3_000, 2000),
                        new PrototypeThing("iphone", 1_000, 2000),
                })
        );
        things.addAll( Arrays.asList(new PrototypeThing[]{
                new PrototypeThing("", 1_000,10_000),
                new PrototypeThing("", 2_000,9_000),
                new PrototypeThing("", 3_000,8_000),
                new PrototypeThing("", 4_000,7_000),
                new PrototypeThing("", 5_000,6_000),
                new PrototypeThing("", 6_000,5_000),
                new PrototypeThing("", 7_000,4_000),
                new PrototypeThing("", 8_000,3_000),
                new PrototypeThing("", 9_000,2_000),
                new PrototypeThing("", 10_000,1_000),

                new PrototypeThing("", 1_000,11_000),
                new PrototypeThing("", 2_000,19_000),
                new PrototypeThing("", 3_000,18_000),
                new PrototypeThing("", 4_000,17_000),
                new PrototypeThing("", 5_000,16_000),
                new PrototypeThing("", 6_000,11_000),
                new PrototypeThing("", 7_000,14_000),
                new PrototypeThing("", 8_000,13_000),
                new PrototypeThing("", 9_000,12_000),
                new PrototypeThing("", 10_000,11_000),

                new PrototypeThing("", 1_000,30_000),
                new PrototypeThing("", 2_000,39_000),
                new PrototypeThing("", 3_000,38_000),
                new PrototypeThing("", 4_000,37_000),
                new PrototypeThing("", 5_000,36_000),
                new PrototypeThing("", 6_000,35_000),
                new PrototypeThing("", 7_000,34_000),
                new PrototypeThing("", 8_000,33_000),
                new PrototypeThing("", 9_000,32_000),
                new PrototypeThing("", 10_000,31_000),

        }));
        backpack = new Backpack(4_000);
    }
    static int maxLimited = backpack.getWeightLimit();
    static final int MULTIPLIER_REAL_WEIGHT = 1; // in REAL WEIGHT
    static final int COLUMNS = maxLimited;  // COLUMNS = from 0 to WEIGHT-1
    static final int LINES = things.size();  // THINGS = from 0 to lastThings
    static int result = 0; // in REAL WEIGHT

    public static void main(String[] args) throws Exception {
        if (LINES == 0 || COLUMNS == 0) {
            System.out.println(result);  System.exit(0);
        }
        //int[][] mas  = new int[LINES][COLUMNS];
        List<List<PrototypeThing>> currentLine = new ArrayList<List<PrototypeThing>>(COLUMNS);
        List<List<PrototypeThing>> pastLine = null; //for first iter - going prototype.other algoritm
        //ITER ---------------------------------------------------------
        for (int indexThing = 0; indexThing < LINES; indexThing++) {
            pastLine = currentLine;
            currentLine = new ArrayList<List<PrototypeThing>>(COLUMNS);
            PrototypeThing thisThing = things.get(indexThing);

            for (int indexColumn = 0; indexColumn < COLUMNS; indexColumn++) {
                List<PrototypeThing> thisCell = new ArrayList<PrototypeThing>(1); // for ecomomy memory
                currentLine.add(indexColumn, thisCell);

                //int sum_ThisTh_and_free = 0;
                List<PrototypeThing> sum_thisTh_and_free = new ArrayList<PrototypeThing>();//by index: 1-this, 0...1-free4backpack things

                int biasKoffWeight = indexColumn+1;
                //int differ = biasKoffWeight - thisThing.getWeight()/MULTIPLIER_REAL_WEIGHT;
                int differ = biasKoffWeight - thisThing.getWeight();
                if( differ >= 0 ) {

                    //sum_ThisTh_and_free = thisThing.getPrice();
                    sum_thisTh_and_free.add(0,thisThing);

                    if(indexThing >= 1){
                        if( differ > 0 ) {
                            assert  (differ >= COLUMNS) : "KOFF not true for mas[][koff]";

                            //sum_ThisTh_and_free += mas[indexThing-1][differ-1];  // TODO NO FIRST
                            sum_thisTh_and_free.addAll(pastLine.get(differ-1) );
                        }
                    }
                }
                if(indexThing >= 1){
                        //mas[indexThing][indexColumn]
                        //          = Math.max(mas[indexThing - 1][indexColumn], sum_ThisTh_and_free);
                        thisCell.addAll(
                                getListWithMaxPrice(sum_thisTh_and_free, pastLine.get(indexColumn))
                        );
                } else {
                        //mas[indexThing][indexColumn] = sum_ThisTh_and_free;
                        thisCell.addAll(sum_thisTh_and_free);
                 }
                // TODO new Comparator<>())
                 //System.out.print(thisCell + "\t");

            }
            //System.out.println();
        }

        System.out.println();
        //result = mas[LINES-1][COLUMNS-1];
        List<PrototypeThing> cell = currentLine.get(COLUMNS-1);
        //PrototypeThing maxPrice = getThing(cell);
//        System.out.println(result);
        System.out.println("Max Limit Backpack: "+backpack.getWeightLimit());
        System.out.println("Thief stole next things: ");
        for (PrototypeThing th : cell) {
            System.out.println(th);
        }
    }

    private static List<PrototypeThing> getListWithMaxPrice(List<PrototypeThing> list1, List<PrototypeThing> list2) {
        PrototypeThing maxPrice = null;
        //if ( ! list.isEmpty()) { /*NOP*/ }
        //else { /*NOP*/ }
        int price1=0;
        for (PrototypeThing th : list1) {
            price1+=th.getPrice();
        }
        int price2=0;
        for (PrototypeThing th : list2) {
            price2+=th.getPrice();
        }

        return (price1 > price2) ? list1 : list2;
    }

}
