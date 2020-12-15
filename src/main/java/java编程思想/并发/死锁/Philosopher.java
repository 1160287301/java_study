package java编程思想.并发.死锁;//: concurrency/Philosopher.java
// A dining philosopher

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.print;


/**
 * 在Philosopher，run()中，每个Philosopher只是不断地思考和吃饭。如果PonderFactor不为0，则pause()方法会休眠（sleeps()）一段随机的时间。通过使用这种方式，你将看到Philosopher会在思考上花掉一段随机化的时间，然后尝试着获取（take()）右边和左边的Chopstick，随后在吃饭上再花掉一段随机化的时间，之后重复此过程。
 */
public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(47);

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) return;
        TimeUnit.MILLISECONDS.sleep(
                rand.nextInt(ponderFactor * 250));
    }

    public Philosopher(Chopstick left, Chopstick right,
                       int ident, int ponder) {
        this.left = left;
        this.right = right;
        id = ident;
        ponderFactor = ponder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                print(this + " " + "thinking");
                pause();
                // Philosopher becomes hungry
                print(this + " " + "grabbing right");
                right.take();
                print(this + " " + "grabbing left");
                left.take();
                print(this + " " + "eating");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            print(this + " " + "exiting via interrupt");
        }
    }

    public String toString() {
        return "Philosopher " + id;
    }
} ///:~
