//: concurrency/ExplicitCriticalSection.java
// Using explicit Lock objects to create critical sections.
package java编程思想.并发.共享受限资源;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Synchronize the entire method:
class ExplicitPairManager1 extends PairManager {
    private Lock lock = new ReentrantLock();

    public synchronized void increment() {
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }
}

// Use a critical section:
class ExplicitPairManager2 extends PairManager {
    private Lock lock = new ReentrantLock();

    public void increment() {
        Pair temp;
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}

/**
 * 这里复用了CriticalSection.java的绝大部分，并创建了新的使用显式的Lock对象的PairManager类型。ExplicitPairManager2展示了如何使用Lock对象来创建临界区，而对store()的调用则在这个临界区的外部。
 */
public class ExplicitCriticalSection {
    public static void main(String[] args) throws Exception {
        PairManager
                pman1 = new ExplicitPairManager1(),
                pman2 = new ExplicitPairManager2();
        CriticalSection.testApproaches(pman1, pman2);
    }
} /* Output: (Sample)
pm1: Pair: x: 15, y: 15 checkCounter = 174035
pm2: Pair: x: 16, y: 16 checkCounter = 2608588
*///:~
