package com.app.despoliation;

import com.app.despoliation.thief.Backpack;
import com.app.despoliation.thief.Thief;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static final int NUMBER_THINGS_ON_ONE_OWNER = 10;

    static final int NUMBER_OWNERS = 10;
    static final int NUMBER_THIEFS = 20;

    static final long TOTAL_THINGS_IN_APP = NUMBER_OWNERS * NUMBER_THINGS_ON_ONE_OWNER;

    private static List<Thread> listOwnerThreads = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thread> listThiefThreads = new ArrayList<>(NUMBER_THIEFS);

    private static List<Owner> listOwner = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thief> listThief = new ArrayList<>(NUMBER_THIEFS);

    public static void main(String[] args) throws InterruptedException {
        setListOwnerThreads();
        setListThiefThreads();

        //bad_StartAllThreads();
        //bad_joinAll();

        // + can: Callable<Object> c = Executors.callable(RunnableImpl);
        List<Callable<Object>> listCallable = new ArrayList<>(NUMBER_OWNERS + NUMBER_THIEFS);
        listCallable.addAll(listThief);
        listCallable.addAll(listOwner);

        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<Object>> futures = service.invokeAll(listCallable);
//        for (Future<Object> future : futures) {
//            try {
//                future.get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
        service.shutdown();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        printTotalThingsAfterRun();
    }

    private static void setListOwnerThreads() {
        for (int i = 0; i< NUMBER_OWNERS; i++) {
            String threadName = "owner#"+i;
            ArrayList<Thing> listThings = new ArrayList<Thing>(NUMBER_THINGS_ON_ONE_OWNER);
            for (int iTh = 0; iTh<NUMBER_THINGS_ON_ONE_OWNER; iTh++) {
                listThings.add(
                        new Thing("thing#"+iTh+", which owns: "+threadName, (int)(Math.random()*1_000), (int)(Math.random()*1_000) )
                );
            }
            Owner owner = new Owner(listThings);

            listOwner.add(owner);
        }
    }

    private static void setListThiefThreads() {
        for (int i = 0; i< NUMBER_THIEFS; i++) {
            Thief thief = new Thief(new Backpack((int) (Math.random() * 10_000)));

            listThief.add(thief);
        }
    }

    private static void bad_StartAllThreads() {
        for (int i=0; i< listOwnerThreads.size(); i++) {
            listOwnerThreads.get(i).start();
        }
        for (int i=0; i< listThiefThreads.size(); i++) {
            listThiefThreads.get(i).start();
        }
    }
    private static void bad_joinAll() throws InterruptedException {
        for (int i=0; i< listOwnerThreads.size(); i++) {
            listOwnerThreads.get(i).join();
        }
        for (int i=0; i< listThiefThreads.size(); i++) {
            listThiefThreads.get(i).join();
        }
    }

    private static void printTotalThingsAfterRun() {
        int totalThingsAfterRun = 0;
        int tempCount =0;

        System.out.println();
        System.out.println("RESULT APP:");
        System.out.println("----------------------------------------------------------");
        System.out.println("Owner things :");

        for (Owner owner: listOwner) {
            tempCount += owner.getThings().size();
            System.out.println(owner.getThings().size()
                    +" "+owner.getThings());
        }
        System.out.println("=>Total owner things: "+tempCount);

        totalThingsAfterRun += tempCount;
        tempCount=0;

        System.out.println("----------------------------------------------------------");
        System.out.println("Thing in Backpack Thiefs:");
        for (Thief thief: listThief) {
            tempCount += thief.getBackpack().getThings().size();
            System.out.println(thief.getBackpack().getThings().size()
                    +" "+thief.getBackpack().getThings());
        }
        System.out.println("=>Total in Backpack Thiefs: "+tempCount);

        totalThingsAfterRun += tempCount;
        //tempCount=0;

        System.out.println("----------------------------------------------------------");
        System.out.println("=>Thing in Flat: "+ Flat.getApartment().size());

        totalThingsAfterRun += Flat.getApartment().size();

        assert (totalThingsAfterRun != TOTAL_THINGS_IN_APP) : "no eguals thing after and before";

        System.out.println("----------------------------------------------------------");
        System.out.println("Total: "+totalThingsAfterRun);
    }

}






















