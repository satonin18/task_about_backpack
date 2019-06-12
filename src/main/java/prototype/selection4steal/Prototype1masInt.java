package prototype.selection4steal;

import com.app.despoliation.thief.Backpack;
import com.app.despoliation.Thing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO MAX WEIGHT IN INT VAR
// TODO SCALA COMPUTING CAN CHANGE, take only 100 грамм
public class Prototype1masInt {
    static List<Thing> things;
    static Backpack backpack;
    static {
        things = new ArrayList<Thing>(
                Arrays.asList(new Thing[]{
                        new Thing("guitar", 1_000, 1500),
                        new Thing("magnetophon", 4_000, 3000),
                        new Thing("notebook", 3_000, 2000),
                        new Thing("iphone", 1_000, 2000),
                })
        );
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
        int[][] mas  = new int[LINES][COLUMNS];
        //ITER for FIRST------------------------------------------------------
//        for (int indexThing = 0; indexThing < 1; indexThing++) {
//            Thing first = things.get(indexThing);
//            for (int thisColumn = 0; thisColumn < COLUMNS; thisColumn++) {
//                int sym = 0;
//                int biasKoffWeight = thisColumn+1;
//                //int differ = biasKoffWeight - first.getWeight()/MULTIPLIER_REAL_WEIGHT;
//                int differ = biasKoffWeight - first.getWeight();
//                if( differ >= 0 ) {
//                    sym = first.getPrice();
//                }
//                mas[indexThing][thisColumn] = sym;
//                System.out.print(mas[indexThing][thisColumn] + "\t");
//            }
//            System.out.println();
//        }
        //ITER for OTHER LINE---------------------------------------------------------
        for (int indexThing = 0; indexThing < LINES; indexThing++) {
            Thing thisThis = things.get(indexThing);
            for (int thisColumn = 0; thisColumn < COLUMNS; thisColumn++) {
                int sum = 0;
                int biasKoffWeight = thisColumn+1;
                //int differ = biasKoffWeight - thisThis.getWeight()/MULTIPLIER_REAL_WEIGHT;
                int differ = biasKoffWeight - thisThis.getWeight();
                if( differ >= 0 ) {
                    sum = thisThis.getPrice();
                    if(indexThing != 0){
                        if( differ > 0 ) {
                            assert  (differ >= COLUMNS) : "KOFF not true for mas[][koff]";
                            sum += mas[indexThing-1][differ-1];  // TODO NO FIRST
                        }
                    }

                }
                if(indexThing != 0){
                    mas[indexThing][thisColumn] = Math.max(mas[indexThing - 1][thisColumn], sum);    // TODO NO FIRST
                } else {
                    mas[indexThing][thisColumn] = sum;
                }
                System.out.print(mas[indexThing][thisColumn] + "\t");
            }
            System.out.println();
        }

        System.out.println();
        result = mas[LINES-1][COLUMNS-1];
        System.out.println(result);
        Arrays.asList();

//            new ArrayList<Thing>();
//            Collections.max();


        //TODO Algoritm WORK
        //TODO SAFE OBJECT THING IN MASS
    }

/*
    public static int max(int i1, int i2) {
        return (i1 > i2) ? i1 : i2;
    }
*/
}
