package java编程思想.并发.新类库之中的构件;//: concurrency/CountDownLatchDemo.java

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.print;

// Performs some portion of a task:
class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException ex) {
            // Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        print(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

// Waits on the CountDownLatch:
class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            latch.await();
            print("Latch barrier passed for " + this);
        } catch (InterruptedException ex) {
            print(this + " interrupted");
        }
    }

    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

/**
 * 它被用来同步一个或多个任务，强制它们等待由其他任务执行的一组操作完成。你可以向CountDownLatch对象设置一个初始计数值，任何在这个对象上调用wait()的方法都将阻塞，直至这个计数值到达0。其他任务在结束其工作时，可以在该对象上调用countDown()来减小这个计数值。CountDownLatch被设计为只触发一次，计数值不能被重置。如果你需要能够重置计数值的版本，则可以使用CyclicBarrier。调用countDown()的任务在产生这个调用时并没有被阻塞，只有对await()的调用会被阻塞，直至计数值到达0。CountDownLatch的典型用法是将一个程序分为n个互相独立的可解决任务，并创建值为0的CountDownLatch。当每个任务完成时，都会在这个锁存器上调用countDown()。等待问题被解决的任务在这个锁存器上调用await()，将它们自己拦住，直至锁存器计数结束。下面是演示这种技术的一个框架示例：
 * <p>
 * TaskPortion将随机地休眠一段时间，以模拟这部分工作的完成，而WaitingTask表示系统中必须等待的部分，它要等待到问题的初始部分完成为止。所有任务都使用了在main()中定义的同一个单一的CountDownLatch。
 */
public class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object:
        CountDownLatch latch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++)
            exec.execute(new WaitingTask(latch));
        for (int i = 0; i < SIZE; i++)
            exec.execute(new TaskPortion(latch));
        print("Launched all tasks");
        exec.shutdown(); // Quit when all tasks complete
    }
} /* (Execute to see output) *///:~
