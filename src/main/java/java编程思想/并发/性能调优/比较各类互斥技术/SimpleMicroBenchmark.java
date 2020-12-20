package java编程思想.并发.性能调优.比较各类互斥技术;//: concurrency/SimpleMicroBenchmark.java
// The dangers of microbenchmarking.

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class Incrementable {
    protected long counter = 0;

    public abstract void increment();
}

class SynchronizingTest extends Incrementable {
    public synchronized void increment() {
        ++counter;
    }
}

class LockingTest extends Incrementable {
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            ++counter;
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 既然Java包括老式的synchronized关键字和JavaSE5中新的Lock和Atomic类，那么比较这些不同的方式，更多地理解它们各自的价值和适用范围，就会显得很有意义。比较天真的方式是在针对每种方式都执行一个简单的测试，就像下面这样：
 */
public class SimpleMicroBenchmark {
    static long test(Incrementable incr) {
        long start = System.nanoTime();
        for (long i = 0; i < 10000000L; i++)
            incr.increment();
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        long synchTime = test(new SynchronizingTest());
        long lockTime = test(new LockingTest());
        System.out.printf("synchronized: %1$10d\n", synchTime);
        System.out.printf("Lock:         %1$10d\n", lockTime);
        System.out.printf("Lock/synchronized = %1$.3f", (double) lockTime / (double) synchTime);
    }
} /* Output: (75% match)
synchronized:  244919117
Lock:          939098964
Lock/synchronized = 3.834
*///:~
