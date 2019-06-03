package com.app.despoliation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

import static com.app.despoliation.Flat.getApartment;

public class Master implements Runnable {
    private static int limitMaster;
    private static int countMaster;

    public static void setLimitMaster(int limitMaster) {
        Master.limitMaster = limitMaster;
    }
//------------------------------------------------------------------------------
    private List<Thing> things;

    {
        if(Master.limitMaster == 0)  throw new LimitThiefException("get limit count Thiefs="+Master.limitMaster+" ,use static method setLimitMaster() for set more number");
        if(countMaster > Master.limitMaster)  throw new LimitThiefException("get limit count Thiefs="+Master.limitMaster+" ,use static method setLimitMaster() for set more number");
        countMaster++;
    }
    class LimitThiefException extends Exception{
        public LimitThiefException(String message) {
            super(message);
        }
    };

    public Master() throws LimitThiefException {
        this.things = new ArrayList<Thing>();
    }

    public Master(List<Thing> things)  throws LimitThiefException{
        this.things = things;
    }
    public Master(Thing... things)  throws LimitThiefException{
        this.things = new ArrayList<Thing>(
                Arrays.asList(things)
        );
    }


 /*   public synchronized void addThings(List<Thing> things) {
        this.things.addAll(things);
        System.out.println("Master: addThings");
    }*/

    public void run() {
        while (true){
            try{
                Flat.getLock4master().lock();

                System.out.println("+in " + Thread.currentThread().getName());
                while (things.isEmpty()) {
                    return;
                }
                getApartment().add(
                        things.remove(0)
                );
            }catch (Exception e) {
                e.printStackTrace(System.err);
            } finally {
                System.out.println("-out " + Thread.currentThread().getName());
                Flat.getLock4master().unlock();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
