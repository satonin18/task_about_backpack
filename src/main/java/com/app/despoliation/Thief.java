package com.app.despoliation;

import com.app.despoliation.selection4steal.SelectionThing4Backpack;

import java.util.List;
import java.util.concurrent.Semaphore;

import static com.app.despoliation.Flat.getApartment;

public class Thief implements Runnable {
    private static int limitThief;
    private static int countThief;

    public static void setLimitThief(int CountThief) {
        Thief.limitThief = CountThief;
    }
//------------------------------------------------------------------------------
    private Backpack backpack;

    {
        if(Thief.limitThief == 0)  throw new LimitThiefException("No set limitThief");
        if(countThief > Thief.limitThief)  throw new LimitThiefException("limitThief = "+limitThief);
        countThief++;
    }
    class LimitThiefException extends Exception{
        public LimitThiefException(String message) {
            super(message);
        }
    };

    public Thief(Backpack backpack) throws LimitThiefException {
        this.backpack = backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public void run() {
        while(true) {
            try{
                Flat.getLock4thief().lock();

                System.out.print("-->");System.out.println(Thread.currentThread().getName());
                List<Thing> finded = findOptimalThings4Backpack( getApartment().getAll() );
                if(! finded.isEmpty()) { getApartment().removeAll(finded); }

            } catch (Exception e) {
                e.printStackTrace(System.err);
            } finally {
                System.out.print("<--");System.out.println(Thread.currentThread().getName());
                Flat.getLock4thief().unlock();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Thing> findOptimalThings4Backpack(List<Thing> things) {
        SelectionThing4Backpack selection = new SelectionThing4Backpack();
        return selection.select(backpack.getWeightLimit(), things);
    }
}

