package prototype.semaphore;

import java.util.concurrent.Semaphore;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        Semaphore sem = new Semaphore(10); // 1 разрешение
        CommonResource res = new CommonResource();
        Thread tr1 = new Thread(new CountThread(res, sem, "CountThread 1"));
        Thread tr2 = new Thread(new CountThread(res, sem, "CountThread 2"));
        Thread tr3 = new Thread(new CountThread(res, sem, "CountThread 3"));
        Thread tr4 = new Thread(new CountThread(res, sem, "CountThread 4"));
        Thread tr5 = new Thread(new CountThread(res, sem, "CountThread 5"));
        Thread tr6 = new Thread(new CountThread(res, sem, "CountThread 6"));
        Thread tr7 = new Thread(new CountThread(res, sem, "CountThread 7"));

        tr1.start();
        tr2.start();
        tr3.start();
        tr4.start();
        tr5.start();
        tr6.start();
        tr7.start();

        tr1.join();
        tr2.join();
        tr3.join();
        tr4.join();
        tr5.join();
        tr6.join();
        tr7.join();

        System.out.println(res.x);
    }
}
class CommonResource{
    int x=0;
}

class CountThread implements Runnable{
    CommonResource res;
    Semaphore sem;
    String name;
    CountThread(CommonResource res, Semaphore sem, String name){
        this.res=res;
        this.sem=sem;
        this.name=name;
    }

    public void run(){
        try{
            System.out.println(name + " ожидает разрешение");
            sem.acquire();
            System.out.println("+    " + name);
            res.x=1;
            for (int i = 1; i < 5; i++){
                System.out.println(this.name + ": " + res.x);
                res.x++;
                //System.out.println("    " + " sleep");
                //Thread.sleep(100);
            }
        }
        catch(InterruptedException e){System.out.println(e.getMessage());}
        System.out.println(name + " освобождает разрешение");
        sem.release();
    }
}