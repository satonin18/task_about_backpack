package com.app.despoliation;

import java.util.List;
import java.util.concurrent.Callable;

public class Owner implements Callable<Object> {
    private List<Thing> things;

    public Owner(List<Thing> things) {
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }

    @Override
    public Object call() throws Exception {
        try{
            Flat.getLock4owner().lock();
            System.out.println("+in Owner with name=" + Thread.currentThread().getName());

            while ( ! things.isEmpty()) {
                Flat.getApartment().add(
                        things.remove(things.size()-1)
                );
            }
        }catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            System.out.println("-out Owner with name=" + Thread.currentThread().getName());
            Flat.getLock4owner().unlock();
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
