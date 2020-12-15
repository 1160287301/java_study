package java编程思想.并发.死锁;//: concurrency/FixedDiningPhilosophers.java
// Dining philosophers without deadlock.
// {Args: 5 5 timeout}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 要修正死锁问题，你必须明白，当以下四个条件同时满足时，就会发生死锁：1）互斥条件。任务使用的资源中至少有一个是不能共享的。这里，一根Chopstick一次就只能被一个Philosopher使用。2）至少有一个任务它必须持有一个资源且正在等待获取一个当前被别的任务持有的资源。也就是说，要发生死锁，Philosopher必须拿着一根Chopstick并且等待另一根。3）资源不能被任务抢占，任务必须把资源释放当作普通事件。Philosopher很有礼貌，他们不会从其他Philosopher那里抢Chopstick。4）必须有循环等待，这时，一个任务等待其他任务所持有的资源，后者又在等待另一个任务所持有的资源，这样一直下去，直到有一个任务在等待第一个任务所持有的资源，使得大家都被锁住。在DeadlockingDiningPhilosophers.java中，因为每个Philosopher都试图先得到右边的Chopstick，然后得到左边的Chopstick，所以发生了循环等待。因为要发生死锁的话，所有这些条件必须全部满足；所以要防止死锁的话，只需破坏其中一个即可。在程序中，防止死锁最容易的方法是破坏第4个条件。有这个条件的原因是每个Philosopher都试图用特定的顺序拿Chopstick：先右后左。正因为如此，就可能会发生“每个人都拿着右边的Chopstick，并等待左边的Chopstick”的情况，这就是循环等待条件。然而，如果最后一个Philosopher被初始化成先拿左边的Chopstick，后拿右边的Chopstick，那么这个Philosopher将永远不会阻止其右边的Philosopher拿起他们的Chopstick。在本例中，这就可以防止循环等待。这只是问题的解决方法之一，也可以通过破坏其他条件来防止死锁
 */
public class FixedDiningPhilosophers {
    public static void main(String[] args) throws Exception {
        int ponder = 0;
        if (args.length > 0)
            ponder = Integer.parseInt(args[0]);
        int size = 5;
        if (args.length > 1)
            size = Integer.parseInt(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];
        for (int i = 0; i < size; i++)
            sticks[i] = new Chopstick();
        for (int i = 0; i < size; i++)
            if (i < (size - 1))
                exec.execute(new Philosopher(
                        sticks[i], sticks[i + 1], i, ponder));
            else
                exec.execute(new Philosopher(
                        sticks[0], sticks[i], i, ponder));
        if (args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
} /* (Execute to see output) *///:~
