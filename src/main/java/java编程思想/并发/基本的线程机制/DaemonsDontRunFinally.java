package java编程思想.并发.基本的线程机制;//: concurrency/DaemonsDontRunFinally.java
// Daemon threads don't run the finally clause

import java.util.concurrent.TimeUnit;

import static java编程思想.net.mindview.util.Print.print;

class ADaemon implements Runnable {
    public void run() {
        try {
            print("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            print("Exiting via InterruptedException");
        } finally {
            print("This should always run?");
        }
    }
}

/**
 * 你应该意识到后台进程在不执行finally子句的情况下就会终止其run()方法：
 * 当你运行这个程序时，你将看到finally子句就不会执行，但是如果你注释掉对setDaemon()的调用，就会看到finally子句将会执行。这种行为是正确的，即便你基于前面对finally给出的承诺，并不希望出现这种行为，但情况仍将如此。当最后一个非后台线程终止时，后台线程会“突然”终止。因此一旦main()退出，JVM就会立即关闭所有的后台进程，而不会有任何你希望出现的确认形式。因为你不能以优雅的方式来关闭后台线程，所以它们几乎不是一种好的思想。非后台的Executor通常是一种更好的方式，因为Executor控制的所有任务可以同时被关闭。正如你将要在本章稍后看到的，在这种情况下，关闭将以有序的方式执行。
 */
public class DaemonsDontRunFinally {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
    }
} /* Output:
Starting ADaemon
*///:~
