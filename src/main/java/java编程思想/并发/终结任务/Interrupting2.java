package java编程思想.并发.终结任务;//: concurrency/Interrupting2.java
// Interrupting a task blocked with a ReentrantLock.

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java编程思想.net.mindview.util.Print.print;

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        // Acquire it right away, to demonstrate interruption
        // of a task blocked on a ReentrantLock:
        lock.lock();
    }

    public void f() {
        try {
            // This will never be available to a second task
            lock.lockInterruptibly(); // Special call
            print("lock acquired in f()");
        } catch (InterruptedException e) {
            print("Interrupted from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable {
    BlockedMutex blocked = new BlockedMutex();

    public void run() {
        print("Waiting for f() in BlockedMutex");
        blocked.f();
        print("Broken out of blocked call");
    }
}

/**
 * BlockedMutex类有一个构造器，它要获取所创建对象上自身的Lock，并且从不释放这个锁。出于这个原因，如果你试图从第二个任务中调用f()（不同于创建这个BlockedMutex的任务），那么将会总是因Mutex不可获得而被阻塞。在Blocked2中，run()方法总是在调用blocked.f()的地方停止。当运行这个程序时，你将会看到，与I/O调用不同，interrupt()可以打断被互斥所阻塞的调用[19]。
 */
public class Interrupting2 {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();
    }
} /* Output:
Waiting for f() in BlockedMutex
Issuing t.interrupt()
Interrupted from lock acquisition in f()
Broken out of blocked call
*///:~
