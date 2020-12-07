package java编程思想.并发.终结任务;//: concurrency/Interrupting.java
// Interrupting a blocked thread.

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.print;


class SleepBlocked implements Runnable {
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            print("InterruptedException");
        }
        print("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream in;

    public IOBlocked(InputStream is) {
        in = is;
    }

    public void run() {
        try {
            print("Waiting for read():");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                print("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        print("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable {
    public synchronized void f() {
        while (true) // Never releases lock
            Thread.yield();
    }

    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f(); // Lock acquired by this thread
            }
        }.start();
    }

    public void run() {
        print("Trying to call f()");
        f();
        print("Exiting SynchronizedBlocked.run()");
    }
}

/**
 * 上面的每个任务都表示了一种不同类型的阻塞。SleepBlock是可中断的阻塞示例，而IOBlocked和SynchronizedBlocked是不可中断的阻塞示例[17]。这个程序证明I/O和在synchronized块上的等待是不可中断的，但是通过浏览代码，你也可以预见到这一点——无论是I/O还是尝试调用synchronized方法，都不需要任何InterruptedException处理器。前两个类很简单直观：在第一个类中run()方法调用了sleep()，而在第二个类中调用了read()。但是，为了演示SynchronizedBlock，我们必须首先获取锁。这是通过在构造器中创建匿名的Thread类的实例来实现的，这个匿名Thread类的对象通过调用f()获取了对象锁（这个线程必须有别于为SynchronizedBlock驱动run()的线程，因为一个线程可以多次获得某个对象锁）。由于f()永远都不返回，因此这个锁永远不会释放，而SynchronizedBlock.run()在试图调用f()，并阻塞以等待这个锁被释放。从输出中可以看到，你能够中断对sleep()的调用（或者任何要求抛出InterruptedException的调用）。但是，你不能中断正在试图获取synchronized锁或者试图执行I/O操作的线程。这有点令人烦恼，特别是在创建执行I/O的任务时，因为这意味着I/O具有锁住你的多线程程序的潜在可能。特别是对于基于Web的程序，这更是关乎利害。
 */
public class Interrupting {
    private static ExecutorService exec =
            Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        print("Interrupting " + r.getClass().getName());
        f.cancel(true); // Interrupts if running
        print("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        print("Aborting with System.exit(0)");
        System.exit(0); // ... since last 2 interrupts failed
    }
} /* Output: (95% match)
Interrupting SleepBlocked
InterruptedException
Exiting SleepBlocked.run()
Interrupt sent to SleepBlocked
Waiting for read():
Interrupting IOBlocked
Interrupt sent to IOBlocked
Trying to call f()
Interrupting SynchronizedBlocked
Interrupt sent to SynchronizedBlocked
Aborting with System.exit(0)
*///:~
