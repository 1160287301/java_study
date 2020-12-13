package java编程思想.并发.线程之间的协作;//: concurrency/NotifyVsNotifyAll.java

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Blocker {
    /**
     * Blocker.waitingCall()非常简单，以至于在本例中，你只需声明for（；；）而不是while（！Thread.interrupted()）就可以达到相同的效果，因为在本例中，由于异常而离开循环和通过检查interrupted()标志离开循环是没有任何区别的——在两种情况下都要执行相同的代码。但是，事实上，这个示例选择了检查interrupted()，因为存在着两种离开循环的方式。如果在以后的某个时刻，你决定要在循环中添加更多的代码，那么如果没有覆盖从这个循环中退出的这两条路径，就会产生引入错误的风险。
     */
    synchronized void waitingCall() {
        try {
            while (!Thread.interrupted()) {
                wait();
                System.out.print(Thread.currentThread() + " ");
            }
        } catch (InterruptedException e) {
            // OK to exit this way
        }
    }

    synchronized void prod() {
        notify();
    }

    synchronized void prodAll() {
        notifyAll();
    }
}

class Task implements Runnable {
    static Blocker blocker = new Blocker();

    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    // A separate Blocker object:
    static Blocker blocker = new Blocker();

    public void run() {
        blocker.waitingCall();
    }
}

/**
 * Task和Task2每个都有其自己的Blocker对象，因此每个Task对象都会在Task.blocker上阻塞，而每个Task2都会在Task2.blocker上阻塞。在main()中，java.util.Timer对象被设置为每4/10秒执行一次run()方法，而这个run()方法将经由“激励”方法交替地在Task.blocker上调用notify()和notifyAll()。从输出中你可以看到，即使存在Task2.blocker上阻塞的Task2对象，也没有任何在Task.blocker上的notify()或notifyAll()调用会导致Task2对象被唤醒。与此类似，在main()的结尾，调用了timer的cancel()，即使计时器被撤销了，前5个任务也依然在运行，并仍旧在它们对Task.blocker.waitingCall()的调用中被阻塞。对Task2.blocker.prodAll()的调用所产生的输出不包括任何在Task.blocker中的锁上等待的任务。如果你浏览Blocker中的prod()和prodAll()，就会发现这是有意义的。这些方法是synchronized的，这意味着它们将获取自身的锁，因此当它们调用notify()或notifyAll()时，只在这个锁上调用是符合逻辑的——因此，将只唤醒在等待这个特定锁的任务。Blocker.waitingCall()非常简单，以至于在本例中，你只需声明for（；；）而不是while（！Thread.interrupted()）就可以达到相同的效果，因为在本例中，由于异常而离开循环和通过检查interrupted()标志离开循环是没有任何区别的——在两种情况下都要执行相同的代码。但是，事实上，这个示例选择了检查interrupted()，因为存在着两种离开循环的方式。如果在以后的某个时刻，你决定要在循环中添加更多的代码，那么如果没有覆盖从这个循环中退出的这两条路径，就会产生引入错误的风险。
 */
public class NotifyVsNotifyAll {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            exec.execute(new Task());
        exec.execute(new Task2());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;

            public void run() {
                if (prod) {
                    System.out.print("\nnotify() ");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    System.out.print("\nnotifyAll() ");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400); // Run every .4 second
        TimeUnit.SECONDS.sleep(5); // Run for a while...
        timer.cancel();
        System.out.println("\nTimer canceled");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.print("Task2.blocker.prodAll() ");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("\nShutting down");
        exec.shutdownNow(); // Interrupt all tasks
    }
} /* Output: (Sample)
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-5,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-2,5,main]
notify() Thread[pool-1-thread-1,5,main]
notifyAll() Thread[pool-1-thread-1,5,main] Thread[pool-1-thread-2,5,main] Thread[pool-1-thread-3,5,main] Thread[pool-1-thread-4,5,main] Thread[pool-1-thread-5,5,main]
Timer canceled
Task2.blocker.prodAll() Thread[pool-1-thread-6,5,main]
Shutting down
*///:~
