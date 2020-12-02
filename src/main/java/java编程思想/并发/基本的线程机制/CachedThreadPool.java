package java编程思想.并发.基本的线程机制;//: concurrency/CachedThreadPool.java

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 我们可以使用Executor来代替在MoreBasicThreads.java中显示地创建Thread对象。LiftOff对象知道如何运行具体的任务，与命令设计模式一样，它暴露了要执行的单一方法。ExecutorService（具有服务生命周期的Executor，例如关闭）知道如何构建恰当的上下文来执行Runnable对象。在下面的示例中，CachedThreadPool将为每个任务都创建一个线程。注意，ExecutorService对象是使用静态的Executor方法创建的，这个方法可以确定其Executor类型：
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            exec.execute(new LiftOff());
        exec.shutdown();
        System.out.println("test!!!!!!!!!!!!!!!!!!!!!");
    }
} /* Output: (Sample)
#0(9), #0(8), #1(9), #2(9), #3(9), #4(9), #0(7), #1(8), #2(8), #3(8), #4(8), #0(6), #1(7), #2(7), #3(7), #4(7), #0(5), #1(6), #2(6), #3(6), #4(6), #0(4), #1(5), #2(5), #3(5), #4(5), #0(3), #1(4), #2(4), #3(4), #4(4), #0(2), #1(3), #2(3), #3(3), #4(3), #0(1), #1(2), #2(2), #3(2), #4(2), #0(Liftoff!), #1(1), #2(1), #3(1), #4(1), #1(Liftoff!), #2(Liftoff!), #3(Liftoff!), #4(Liftoff!),
*///:~
