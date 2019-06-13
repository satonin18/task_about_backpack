package com.app.despoliation;

import com.app.despoliation.Main;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

//todo generic set after
public class SynchronousStartWrapper  implements Callable<Object> {
    private Callable<Object> callable;
    private static int count;

    public SynchronousStartWrapper(Callable<Object> callable) {
        this.callable = callable;
    }

    @Override
    public Object call() throws Exception {
        //new approach
        Main.countDownLatch.countDown();

        // old approach
        // AFTER NO WORK !!!
//        synchronized (SynchronousStartWrapper.class){
//            count++;
//            if(count == Main.NUMBER_THREADS){
//                System.out.println(count);
//                System.out.println("*-*-*-*--*-*---*-*--*-**-**-*-*-*");
//                notifyAll();
//            }
//            while (count != Main.NUMBER_THREADS){
//                System.out.println("sggggggggggggggggggggg");
//                wait();
//            }
//        }

        return this.callable.call();
    }
}
