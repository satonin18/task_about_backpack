package com.app.despoliation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Owner implements Runnable {

    public void run() {
        try{
            Flat.getLock4owner().lock();
            System.out.println("+in " + Thread.currentThread().getName());

            for (int i=0; i<Main.NUMBER_THINGS_ON_ONE_OWNER; i++) {
                Flat.getApartment().add(
                        new Thing("thing#"+i+", which owns: "+Thread.currentThread().getName(), (int)(Math.random()*1_000), (int)(Math.random()*1_000) ) //  .random() call from several Threads
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
