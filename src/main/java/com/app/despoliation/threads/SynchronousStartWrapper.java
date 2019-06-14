package com.app.despoliation.threads;

import com.app.despoliation.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class SynchronousStartWrapper  implements Callable<Object> {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private Callable<Object> callable;
    //private static int count; //no volatile, use synchronized

    public SynchronousStartWrapper(Callable<Object> callable) {
        this.callable = callable;
    }

    @Override
    public Object call() throws Exception {
        //new approach
        Main.countDownLatch.countDown();
        Main.countDownLatch.await();

        // old approach // BAD ORDER
//        synchronized (SynchronousStartWrapper.class){
//            count++;
//            logger.log(Level.DEBUG,count);
//
//            if(count == Main.NUMBER_THREADS){
//                logger.log(Level.DEBUG,"*-*-*-*--*-*---*-*--*-**-**-*-*-*");
//                SynchronousStartWrapper.class.notifyAll();
//                logger.log(Level.DEBUG,"I say everybody, notifyAll");
//
//            }else {
//                while (count != Main.NUMBER_THREADS){
//                    logger.log(Level.DEBUG,"wait");
//                    SynchronousStartWrapper.class.wait();
//                    logger.log(Level.DEBUG,"after wait");
//
//                }
//            }
//        }
        return this.callable.call();
    }
}
