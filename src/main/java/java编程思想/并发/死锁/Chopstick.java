package java编程思想.并发.死锁;//: concurrency/Chopstick.java
// Chopsticks for dining philosophers.

/**
 * 任何两个Philosopher都不能成功take()同一根筷子。另外，如果一根Chopstick已经被某个Philosopher获得，那么另一个Philosopher可以wait()，直至这根Chopstick的当前持有者调用drop()使其可用为止。当一个Philosopher任务调用take()时，这个Philosopher将等待，直至taken标志变为false（直至当前持有Chopstick的Philosopher释放它）。然后这个任务会将taken标志设置为true，以表示现在由新的Philosopher持有这根Chopstick。当这个Philosopher使用完这根Chopstick时，它会调用drop()来修改标志的状态，并notifyAll()所有其他的Philosopher，这些Philosopher中有些可能就在wait()这根Chopstick。
 */
public class Chopstick {
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();
        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
} ///:~
