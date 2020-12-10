//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package java编程思想.并发.线程之间的协作;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.print;
import static java编程思想.net.mindview.util.Print.printnb;


class Car {
    private boolean flag = false;

    public synchronized void producer() {
        flag = true; // Ready to buff
        notifyAll();
    }

    public synchronized void consumer() {
        flag = false; // Ready for another coat of wax
        notifyAll();
    }

    public synchronized void waitForProduce()
            throws InterruptedException {
        while (flag == false)
            wait();
    }

    public synchronized void waitForConsume()
            throws InterruptedException {
        while (flag == true)
            wait();
    }
}

class FlagOn implements Runnable {
    private Car car;

    public FlagOn(Car c) {
        car = c;
    }

    public void run() {
        try {
//            while (!Thread.interrupted()) {
            while (true) {
                printnb("produce! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.producer();
                car.waitForConsume();
            }
        } catch (InterruptedException e) {
            print("Exiting FlagOn interrupt");
        }
        print("Ending Wax On task");
    }
}

class FlagOff implements Runnable {
    private Car car;

    public FlagOff(Car c) {
        car = c;
    }

    public void run() {
        try {
//            while (!Thread.interrupted()) {
            while (true) {
                car.waitForProduce();
                printnb("consumer! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.consumer();
            }
        } catch (InterruptedException e) {
            print("Exiting FlagOff interrupt");
        }
        print("Ending Wax Off task");
    }
}

public class WaxOMatic {
    public static void main(String[] args) throws Exception {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new FlagOn(car));
        exec.execute(new FlagOff(car));
        TimeUnit.SECONDS.sleep(3); // Run for a while...
        exec.shutdownNow(); // Interrupt all tasks
    }
} /* Output: (95% match)
Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Exiting via interrupt
Ending Wax On task
Exiting via interrupt
Ending Wax Off task
*///:~
