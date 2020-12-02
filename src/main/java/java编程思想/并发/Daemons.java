package java编程思想.并发;//: concurrency/Daemons.java
// Daemon threads spawn other daemon threads.

import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.printnb;

class Daemon implements Runnable {
    private Thread[] t = new Thread[10];

    public void run() {
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            printnb("DaemonSpawn " + i + " started, ");
        }
        for (int i = 0; i < t.length; i++)
            printnb("t[" + i + "].isDaemon() = " +
                    t[i].isDaemon() + ", ");
        while (true)
            Thread.yield();
    }
}

class DaemonSpawn implements Runnable {
    public void run() {
        while (true)
            Thread.yield();
    }
}

/**
 * 可以通过调用isDaemon()方法来确定线程是否是一个后台线程。如果是一个后台线程，那么它创建的任何线程将被自动设置成后台线程，如下例所示：
 * Daemon线程被设置成了后台模式，然后派生出许多子线程，这些线程并没有被显式地设置为后台模式，不过它们的确是后台线程。接着，Daemon线程进入了无限循环，并在循环里调用yield()方法把控制权交给其他进程。
 */
public class Daemons {
    public static void main(String[] args) throws Exception {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);
        d.start();
        printnb("d.isDaemon() = " + d.isDaemon() + ", ");
        // Allow the daemon threads to
        // finish their startup processes:
        TimeUnit.SECONDS.sleep(1);
    }
} /* Output: (Sample)
d.isDaemon() = true, DaemonSpawn 0 started, DaemonSpawn 1 started, DaemonSpawn 2 started, DaemonSpawn 3 started, DaemonSpawn 4 started, DaemonSpawn 5 started, DaemonSpawn 6 started, DaemonSpawn 7 started, DaemonSpawn 8 started, DaemonSpawn 9 started, t[0].isDaemon() = true, t[1].isDaemon() = true, t[2].isDaemon() = true, t[3].isDaemon() = true, t[4].isDaemon() = true, t[5].isDaemon() = true, t[6].isDaemon() = true, t[7].isDaemon() = true, t[8].isDaemon() = true, t[9].isDaemon() = true,
*///:~
