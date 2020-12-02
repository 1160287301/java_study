package java编程思想.并发.基本的线程机制;//: concurrency/LiftOff.java
// Demonstration of the Runnable interface.

/**
 * 线程可以驱动任务，因此你需要一种描述任务的方式，这可以由Runnable接口来提供。要想定义任务，只需实现Runnable接口并编写run()方法，使得该任务可以执行你的命令。例如，下面的LiftOff任务将显示发射之前的倒计时：
 */
public class LiftOff implements Runnable {
    protected int countDown = 10; // Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" +
                (countDown > 0 ? countDown : "Liftoff!") + "), ";
    }

    /**
     * 在run()中对静态方法Thread.yield()的调用是对线程调度器（Java线程机制的一部分，可以将CPU从一个线程转移给另一个线程）的一种建议，
     * 它在声明：“我已经执行完生命周期中最重要的部分了，此刻正是切换给其他任务执行一段时间的大好时机。
     * ”这完全是选择性的，但是这里使用它是因为它会在这些示例中产生更加有趣的输出：你更有可能会看到任务换进换出的证据。
     */
    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            Thread.yield();
        }
    }
} ///:~
