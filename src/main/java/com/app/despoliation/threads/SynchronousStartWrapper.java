package com.app.despoliation.threads;

import com.app.despoliation.Main;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

//todo generic set after
public class SynchronousStartWrapper  implements Callable<Object> {
    private Callable<Object> callable;
    private static int count; //no volatile, use synchronized

    public SynchronousStartWrapper(Callable<Object> callable) {
        this.callable = callable;
    }

    @Override
    public Object call() throws Exception {
        //new approach
        Main.countDownLatch.countDown();
        Main.countDownLatch.await();

        // old approach
        // BAD ORDER
//        synchronized (SynchronousStartWrapper.class){
//            count++;
//            System.out.println(count);
//
//            if(count == Main.NUMBER_THREADS){
//                System.out.println("*-*-*-*--*-*---*-*--*-**-**-*-*-*");
//                SynchronousStartWrapper.class.notifyAll();
//                System.out.println("I say everybody, notifyAll");
//
//            }else {
//                while (count != Main.NUMBER_THREADS){
//                    System.out.println("wait");
//                    SynchronousStartWrapper.class.wait();
//                    System.out.println("after wait");
//
//                }
//            }
//        }
        return this.callable.call();
    }
}
