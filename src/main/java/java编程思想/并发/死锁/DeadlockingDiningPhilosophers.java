package java编程思想.并发.死锁;//: concurrency/DeadlockingDiningPhilosophers.java
// Demonstrates how deadlock can be hidden in a program.
// {Args: 0 5 timeout}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 你会发现，如果Philosopher花在思考上的时间非常少，那么当他们想要进餐时，全都会在Chopstick上产生竞争，而死锁也就会更快地发生。第一个命令行参数可以调整ponder因子，从而影响每个Philosopher花费在思考上的时间长度。如果有许多Philosopher，或者他们花费很多时间去思考，那么尽管存在死锁的可能，但你可能永远也看不到死锁。值为0的命令行参数倾向于使死锁尽快发生。注意，Chopstick对象不需要内部标识符，它们是由在数组sticks中的位置来标识的。每个Philosopher构造器都会得到一个对左边和右边Chopstick对象的引用。除了最后一个Philosopher，其他所有的Philosopher都是通过将这个Philosopher定位于下一对Chopstick对象之间而被初始化的，而最后一个Philosopher右边的Chopstick是第0个Chopstick，这样这个循环表也就结束了。因为最后一个Philosopher坐在第一个Philosopher的右边，所以他们会共享第0个Chopstick。现在，所有的Philosopher都有可能希望进餐，从而等待其临近的Philosopher放下它们的Chopstick。这将使程序死锁。如果Philosopher花费更多的时间去思考而不是进餐（使用非0的ponder值，或者大量的Philosopher），那么他们请求共享资源（Chopstick）的可能性就会小许多，这样你就会确信该程序不会死锁，尽管它们并非如此。这个示例相当有趣，因为它演示了看起来可以正确运行，但实际上会死锁的程序。
 */
public class DeadlockingDiningPhilosophers {
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
            exec.execute(new Philosopher(
                    sticks[i], sticks[(i + 1) % size], i, ponder));
        if (args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
} /* (Execute to see output) *///:~
