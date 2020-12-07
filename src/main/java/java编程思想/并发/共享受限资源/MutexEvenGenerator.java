package java编程思想.并发.共享受限资源;//: concurrency/MutexEvenGenerator.java
// Preventing thread collisions with mutexes.
// {RunByHand}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MutexEvenGenerator添加了一个被互斥调用的锁，并使用lock()和unlock()方法在next()内部创建了临界资源。当你在使用Lock对象时，将这里所示的惯用法内部化是很重要的：紧接着的对lock()的调用，你必须放置在finally子句中带有unlock()的tryfinally语句中。注意，return语句必须在try子句中出现，以确保unlock()不会过早发生，从而将数据暴露给了第二个任务。尽管tryfinally所需的代码比synchronized关键字要多，但是这也代表了显式的Lock对象的优点之一。如果在使用synchronized关键字时，某些事物失败了，那么就会抛出一个异常。但是你没有机会去做任何清理工作，以维护系统使其处于良好状态。有了显式的Lock对象，你就可以使用finally子句将系统维护在正确的状态了。大体上，当你使用synchronized关键字时，需要写的代码量更少，并且用户错误出现的可能性也会降低，因此通常只有在解决特殊问题时，才使用显式的Lock对象。
 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    private Lock lock = new ReentrantLock();

    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            Thread.yield(); // Cause failure faster
            ++currentEvenValue;
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
} ///:~
