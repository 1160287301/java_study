package java编程思想.并发.共享受限资源;//: concurrency/AtomicityTest.java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 该程序将找到奇数值并终止。尽管returni确实是原子性操作，但是缺少同步使得其数值可以在处于不稳定的中间状态时被读取。除此之外，由于i也不是volatile的，因此还存在可视性问题。getValue()和evenIncrement()必须是synchronized的
 */
public class AtomicityTest implements Runnable {
    private int i = 0;

    public int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    public void run() {
        while (true)
            evenIncrement();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while (true) {
            int val = at.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
} /* Output: (Sample)
191583767
*///:~
