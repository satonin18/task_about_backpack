package com.app.despoliation;

import java.util.List;

public class Owner implements Runnable {
    private List<Thing> things;

    public Owner(List<Thing> things) {
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void run() {
        try{
            Flat.getLock4owner().lock();
            System.out.println("+in " + Thread.currentThread().getName());

            while ( ! things.isEmpty()) {
                Flat.getApartment().add(
                        things.remove(things.size()-1)
                );
            }
        }catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            System.out.println("-out " + Thread.currentThread().getName());
            Flat.getLock4owner().unlock();
        }
    }
}
