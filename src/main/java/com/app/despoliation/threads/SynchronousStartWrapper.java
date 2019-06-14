package com.app.despoliation.threads;

import com.app.despoliation.Main;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

//todo generic set after
public class SynchronousStartWrapper  implements Callable<Object> {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass()); //String Full-Name

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
//            logger.debug(count);
//
//            if(count == Main.NUMBER_THREADS){
//                logger.debug("*-*-*-*--*-*---*-*--*-**-**-*-*-*");
//                SynchronousStartWrapper.class.notifyAll();
//                logger.debug("I say everybody, notifyAll");
//
//            }else {
//                while (count != Main.NUMBER_THREADS){
//                    logger.debug("wait");
//                    SynchronousStartWrapper.class.wait();
//                    logger.debug("after wait");
//
//                }
//            }
//        }
        return this.callable.call();
    }
}
