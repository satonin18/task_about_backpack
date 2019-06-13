package com.app.despoliation;

import com.app.despoliation.threads.thief.Backpack;
import com.app.despoliation.threads.thief.Thief;
import com.app.despoliation.threads.owner.Owner;
import com.app.despoliation.threads.SynchronousStartWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static final int NUMBER_THINGS_ON_ONE_OWNER = 10;

    static final int NUMBER_OWNERS = 10;
    static final int NUMBER_THIEFS = 20;
    static final int NUMBER_THREADS = NUMBER_OWNERS + NUMBER_THIEFS;

    //can be small type data
    static final long TOTAL_THINGS_IN_APP = NUMBER_OWNERS * NUMBER_THINGS_ON_ONE_OWNER;

    private static List<Owner> listOwner = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thief> listThief = new ArrayList<>(NUMBER_THIEFS);
    public static CountDownLatch countDownLatch = new CountDownLatch(NUMBER_THREADS);

    public static void main(String[] args) throws InterruptedException {
        setListOwners();
        setListThiefs();

//        // "threads start (artificial) random"
//        List<Callable<Object>> listCallable = new ArrayList<>(NUMBER_THREADS);
//        listCallable.addAll(listThief);
//        listCallable.addAll(listOwner);
//        Collections.shuffle(listCallable);

        ArrayList<SynchronousStartWrapper> listWrappers = new ArrayList<>(NUMBER_THREADS);
        for (Owner owner: listOwner) {
            listWrappers.add(new SynchronousStartWrapper(owner));
        }
        for (Thief thief: listThief) {
            listWrappers.add(new SynchronousStartWrapper(thief));
        }
        Collections.shuffle(listWrappers);

        ExecutorService service = Executors.newCachedThreadPool();
        //ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Object>> futures = service.invokeAll(listWrappers);
        service.shutdown();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        printTotalThingsAfterRun();
    }

    private static void setListOwners() {
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

    private static void setListThiefs() {
        for (int i = 0; i< NUMBER_THIEFS; i++) {
            Thief thief = new Thief(new Backpack((int) (Math.random() * 10_000)));
            listThief.add(thief);
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
        System.out.println("Things were: "+TOTAL_THINGS_IN_APP);
        System.out.println("New, total: "+totalThingsAfterRun);
    }

}






















