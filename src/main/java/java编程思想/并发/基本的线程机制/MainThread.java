package java编程思想.并发.基本的线程机制;//: concurrency/MainThread.java

/**
 * 在下面的实例中，这个任务的run()不是由单独的线程驱动的，它是在main()中直接调用的（实际上，这里仍旧使用了线程，即总是分配给main()的那个线程）：
 */
public class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }
} /* Output:
#0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!),
*///:~
