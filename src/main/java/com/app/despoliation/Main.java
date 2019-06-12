package com.app.despoliation;

import com.app.despoliation.thief.Backpack;
import com.app.despoliation.thief.Thief;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int NUMBER_THINGS_ON_ONE_OWNER = 10;

    static final int NUMBER_OWNERS = 10;
    static final int NUMBER_THIEFS = 20;

    static final long TOTAL_THINGS_IN_APP = NUMBER_OWNERS * NUMBER_THINGS_ON_ONE_OWNER;

    private static List<Thread> listOwnerThreads = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thread> listThiefThreads = new ArrayList<>(NUMBER_THIEFS);

    private static List<Owner> listOwner = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thief> listThief = new ArrayList<>(NUMBER_THIEFS);

    public static int totalThingsAfterRun = 0;

    public static void main(String[] args) throws InterruptedException {
        setListOwnerThreads();
        setListThiefThreads();

        bad_StartAllThreads();
        bad_joinAll();


        printTotalThingsAfterRun();

    }

    private static void setListOwnerThreads() {
        for (int i = 0; i< NUMBER_OWNERS; i++) {
            Owner owner = new Owner();

            listOwner.add(owner);
            listOwnerThreads.add(
                    new Thread(owner ,"owner#"+i)
            );
        }
    }

    private static void setListThiefThreads() {
        for (int i = 0; i< NUMBER_THIEFS; i++) {
            Thief thief = new Thief(new Backpack((int) (Math.random() * 10_000)));

            listThief.add(thief);
            listThiefThreads.add(
                    new Thread(thief, "thief#"+i )
            );
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
        System.out.println("----------------------------------------------------------");
        System.out.println("Backpack Thiefs:");
        for (Thief thief: listThief) {
            System.out.println(thief.getBackpack().getThings().size()
                    +" "+thief.getBackpack().getThings());
        }
        System.out.println();
        System.out.println("Flat: "+ Flat.getApartment().size());
        totalThingsAfterRun += Flat.getApartment().size();

        assert (totalThingsAfterRun != TOTAL_THINGS_IN_APP) : "no eguals thing after and before";

        System.out.println("----------------------------------------------------------");
        System.out.println("Total: "+totalThingsAfterRun);
    }

}






















