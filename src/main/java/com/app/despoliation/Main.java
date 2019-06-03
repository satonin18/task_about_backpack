package com.app.despoliation;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int NUMBER_THINGS = 100;

    private static final int NUMBER_THINGS_ON_ONE_MASTER = 10;

    private static final int NUMBER_MASTERS = 10;
    private static final int NUMBER_THIEFS = 10;

    private static List<Thread> listMasterThreads = new ArrayList<>();;
    private static List<Thread> listThiefThreads = new ArrayList<>();;

    public static void main(String[] args) throws InterruptedException, Master.LimitThiefException, Thief.LimitThiefException {
        //initListThings();
        //setLimitCountThreadsUse_StaticVar(NUMBER_MASTERS, NUMBER_THIEFS);

        //generThings();
        initThreadsMasters();
        initThreadsThiefs();

        //setDemonAllThreads();
        //startAllThreads();

        //Thread.sleep(500);
        //System.exit(0);
    }

//    private static void initListThings() {
////        for (int i = 0; i<NUMBER_THINGS; i++){
////            listThings.add(new Thing("", i*100,i*1_000));
////        }
//
//        listThings.add(new Thing[]{
//                        new Thing("guitar", 1_000, 1500),
//                        new Thing("magnetophon", 4_000, 3000),
//                        new Thing("notebook", 3_000, 2000),
//                        new Thing("iphone", 1_000, 2000),
//        });
//        listThings.add( new Thing[]{
//                new Thing("", 1_000,10_000),
//                new Thing("", 2_000,9_000),
//                new Thing("", 3_000,8_000),
//                new Thing("", 4_000,7_000),
//                new Thing("", 5_000,6_000),
//                new Thing("", 6_000,5_000),
//                new Thing("", 7_000,4_000),
//                new Thing("", 8_000,3_000),
//                new Thing("", 9_000,2_000),
//                new Thing("", 10_000,1_000),
//
//                new Thing("", 1_000,11_000),
//                new Thing("", 2_000,19_000),
//                new Thing("", 3_000,18_000),
//                new Thing("", 4_000,17_000),
//                new Thing("", 5_000,16_000),
//                new Thing("", 6_000,11_000),
//                new Thing("", 7_000,14_000),
//                new Thing("", 8_000,13_000),
//                new Thing("", 9_000,12_000),
//                new Thing("", 10_000,11_000),
//
//                new Thing("", 1_000,30_000),
//                new Thing("", 2_000,39_000),
//                new Thing("", 3_000,38_000),
//                new Thing("", 4_000,37_000),
//                new Thing("", 5_000,36_000),
//                new Thing("", 6_000,35_000),
//                new Thing("", 7_000,34_000),
//                new Thing("", 8_000,33_000),
//                new Thing("", 9_000,32_000),
//                new Thing("", 10_000,31_000),
//
//        });
//        listThings.add( new Thing[]{
//                new Thing("guitar", 1_000, 1500),
//                new Thing("magnetophon", 4_000, 3000),
//                new Thing("notebook", 3_000, 2000),
//                new Thing("iphone", 1_000, 2000)
//        });
//
//        listThings.add( new Thing[]{
//                new Thing("", 1_000,10_000),
//                new Thing("", 2_000,9_000),
//                new Thing("", 3_000,8_000),
//                new Thing("", 4_000,7_000),
//                new Thing("", 5_000,6_000),
//                new Thing("", 6_000,5_000),
//                new Thing("", 7_000,4_000),
//                new Thing("", 8_000,3_000),
//                new Thing("", 9_000,2_000),
//                new Thing("", 10_000,1_000),
//        });
//
//        listThings.add( new Thing[] {
//                new Thing("", 1_000,11_000),
//                new Thing("", 2_000,19_000),
//                new Thing("", 3_000,18_000),
//                new Thing("", 4_000,17_000),
//                new Thing("", 5_000,16_000),
//                new Thing("", 6_000,11_000),
//                new Thing("", 7_000,14_000),
//                new Thing("", 8_000,13_000),
//                new Thing("", 9_000,12_000),
//                new Thing("", 10_000,11_000),
//        });
//
//        listThings.add( new Thing[] {
//                new Thing("", 1_000,30_000),
//                new Thing("", 2_000,39_000),
//                new Thing("", 3_000,38_000),
//                new Thing("", 4_000,37_000),
//                new Thing("", 5_000,36_000),
//                new Thing("", 6_000,35_000),
//                new Thing("", 7_000,34_000),
//                new Thing("", 8_000,33_000),
//                new Thing("", 9_000,32_000),
//                new Thing("", 10_000,31_000),
//        });
//    }

    private static void generThings() {

    }

    private static void setLimitCountThreadsUse_StaticVar(int limitMaster, int limitThief) {
        Master.setLimitMaster(limitMaster);
        Thief.setLimitThief(limitThief);
    }

//    private static void setLimitCountThreadsUse_Semaphore(int limitMaster, int limitThief) {
//        Semaphore semMasterCount = new Semaphore(limitMaster); //Хозяев может быть 1..n.
//        Semaphore semThiefCount = new Semaphore(limitThief); // m разрешение
//        Master.setCountMasterSemaphore(semMasterCount);
//        Thief.setCountThiefSemaphore(semThiefCount);
//    }

//    private static void initSemophores_WithPermit4Master(int permit4Master) {
//        Flat.setNumberPermits_4_MasterSemaphore(permit4Master);
//    }

    private static void initThreadsMasters() throws Master.LimitThiefException, Thief.LimitThiefException {
        for (int i = 0; i< NUMBER_MASTERS; i++) {
            List<Thing> genListThings = new ArrayList<>(NUMBER_THINGS_ON_ONE_MASTER);
            for (int iTh = 0; iTh < genListThings.size(); i++){
                genListThings.add(new Thing("thing#"+iTh+" owner:master#"+i, (int)Math.random()*i,(int)Math.random()*i ) );
            }
            listMasterThreads.add( new Thread(
                    new Master(genListThings),
                    "master#"+i)
            );
        }
    }

    private static void initThreadsThiefs() throws Master.LimitThiefException, Thief.LimitThiefException {
        for (int i = 0; i< NUMBER_THIEFS; i++) {
            listThiefThreads.add( new Thread(
                    new Thief(new Backpack(i*1_000)),
                    "thief#"+i)
            );
        }
//        Thief thief1 = new Thief(new Backpack(3_000));
//        Thief thief2 = new Thief(new Backpack(6_000));
//        Thief thief3 = new Thief(new Backpack(7_000));
    }

    private static void setDemonAllThreads() {
        for (int i=0; i<listMasterThreads.size() ; i++) {
            listMasterThreads.get(i).setDaemon(true);
        }
        for (int i=0; i<listThiefThreads.size() ; i++) {
            listThiefThreads.get(i).setDaemon(true);
        }
    }

    private static void startAllThreads() {
        for (int i=0; i<listMasterThreads.size() ; i++) {
            listMasterThreads.get(i).start();
        }
        for (int i=0; i<listThiefThreads.size() ; i++) {
            listThiefThreads.get(i).start();
        }
    }

}






















