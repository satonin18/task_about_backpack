package com.app.despoliation;

import com.app.despoliation.entities.Flat;
import com.app.despoliation.entities.Thing;
import com.app.despoliation.threads.thief.Backpack;
import com.app.despoliation.threads.thief.Thief;
import com.app.despoliation.threads.owner.Owner;
import com.app.despoliation.threads.SynchronousStartWrapper;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class Main {
    //private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass()); //String Full-Name
    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    private static int numberThingsOnOneOwner;

    private static int numberOwners;
    private static int numberThiefs;

    private static int rangePriceThings;
    private static int rangeWeightThings;
    private static int rangeBackpackLimitWeight;

    private static int numberThreads;
    private static long totalThingsInApp;

    static List<Thing> listThings_onStartApp = new ArrayList<>((int) totalThingsInApp);
    static List<Thing> listThings_onEndApp = new ArrayList<>((int) totalThingsInApp);

    private static List<Owner> listOwner = new ArrayList<>(numberOwners);
    private static List<Thief> listThief = new ArrayList<>(numberThiefs);
    public static CountDownLatch countDownLatch = new CountDownLatch(numberThreads);

    public static void main(String[] args) throws InterruptedException {
        initVar();

        initLists();

        List<Callable<Object>> listCallable = getWrapperList(); // getShuffledList()

        ExecutorService service = Executors.newCachedThreadPool(); //.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Object>> futures = service.invokeAll(listCallable);
        //todo take Exception Threads
        service.shutdown();

        log.log(Level.INFO,"++++++++++++++++++++++++++++++++++++++++++++++");
        printTotalThingsAfterRun(); //util.statistics
        checkEqualsAllThing();
    }

    public static void initVar() {
        initVarFromProperties();

        numberThreads = numberOwners + numberThiefs;
        totalThingsInApp = numberOwners * numberThingsOnOneOwner;
    }

    private static void initVarFromProperties(){
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(new File(PATH_TO_PROPERTIES))) {
            props.load(is);

            numberThingsOnOneOwner = Integer.parseInt(props.getProperty("numberThingsOnOneOwner", "5")); // for test =Integer.MAX_VALUE

            numberOwners = Integer.parseInt(props.getProperty("numberOwners", "5"));
            numberThiefs = Integer.parseInt(props.getProperty("numberThiefs", "5"));

            rangePriceThings = Integer.parseInt(props.getProperty("rangePriceThings", "1000"));
            rangeWeightThings = Integer.parseInt(props.getProperty("rangeWeightThings","20"));
            rangeBackpackLimitWeight = Integer.parseInt(props.getProperty("rangeBackpackLimitWeight", "20"));
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static List<Callable<Object>> getShuffledList() {
        List<Callable<Object>> listCallable = new ArrayList<>(numberThreads);
        listCallable.addAll(listThief);
        listCallable.addAll(listOwner);
        Collections.shuffle(listCallable);
        return listCallable;
    }

    private static List<Callable<Object>> getWrapperList() {
        List<Callable<Object>> listWrappers = new ArrayList<>(numberThreads);
        for (Owner owner: listOwner) {
            listWrappers.add(new SynchronousStartWrapper(owner));
        }
        for (Thief thief: listThief) {
            listWrappers.add(new SynchronousStartWrapper(thief));
        }
        return listWrappers;
    }

    private static void initLists() {
        for (int i = 0; i< numberOwners; i++) {
            String threadName = "owner#"+i;
            List<Thing> tempListThings = new ArrayList<>(numberThingsOnOneOwner);
            for (int iThing = 0; iThing< numberThingsOnOneOwner; iThing++) {
                Thing thing = new Thing.Builder()
                        .title("thing#"+iThing+", which owns: "+threadName)
                        .price( (int)(Math.random()* rangePriceThings))
                        .weight((int)(Math.random()* rangeWeightThings))
                        .build();
                tempListThings.add(
                        thing
                );
            }
            Owner owner = new Owner(tempListThings);
            listOwner.add(owner);

            listThings_onStartApp.addAll(tempListThings);
        }

        for (int i = 0; i < numberThiefs; i++) {
            Thief thief = new Thief(
                    new Backpack( (int) (Math.random() * rangeBackpackLimitWeight) )
            );
            listThief.add(thief);
        }
    }

    private static void printTotalThingsAfterRun() {
        int totalThingsAfterRun = 0;
        int tempCount =0;

        log.log(Level.INFO,"RESULT APP:");
        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Owner things :");

        for (Owner owner: listOwner) {
            tempCount += owner.getThings().size();
            log.log(Level.INFO,owner.getThings().size()
                    +" "+owner.getThings());
        }
        log.log(Level.INFO,"=>Total owner things: "+tempCount);

        totalThingsAfterRun += tempCount;
        tempCount=0;

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Thing in Backpack Thiefs:");
        for (Thief thief: listThief) {
            listThings_onEndApp.addAll(thief.getBackpack().getThings());
            tempCount += thief.getBackpack().getThings().size();
            log.log(Level.INFO,thief.getBackpack().getThings().size()
                    +" "+thief.getBackpack().getThings());
        }
        log.log(Level.INFO,"=>Total in Backpack Thiefs: "+tempCount);

        totalThingsAfterRun += tempCount;
        //tempCount=0;

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"=>Thing in Flat: "+ Flat.getInstance().size());

        listThings_onEndApp.addAll(Flat.getInstance().getAll());
        totalThingsAfterRun += Flat.getInstance().size();

        assert (totalThingsAfterRun != totalThingsInApp) : "no eguals thing after and before";

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Things were: "+ totalThingsInApp);
        log.log(Level.INFO,"New, total: "+totalThingsAfterRun);
    }

    private static void checkEqualsAllThing() {
        List<Thing> tempListThings = new ArrayList<Thing>(listThings_onEndApp);

        if( listThings_onStartApp.size() != listThings_onEndApp.size() ) {
            log.log(Level.ERROR,"NO EQUALS SIZE");
            throw new AssertionError("NO EQUALS SIZE");
        }

        for (Thing oldThing : listThings_onStartApp) {
            boolean oldThingFinded = false;

            for (int i = 0; i<tempListThings.size(); i++) {
                Thing thingAny = tempListThings.get(i);

                if( oldThing.equals(thingAny) ) {
                    oldThingFinded = true;
                    tempListThings.remove(i);
                    break;
                }
            }
            if( ! oldThingFinded) {
                log.log(Level.ERROR,"NO EQUALS THING");
                throw new AssertionError("NO EQUALS THING");
            }
        }
        log.log(Level.INFO,"THINGS on Start and on End - equally");
    }
}






















