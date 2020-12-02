package java编程思想.并发.基本的线程机制;//: concurrency/BasicThreads.java
// The most basic use of the Thread class.

/**
 * 将Runnable对象转变为工作任务的传统方式是把它提交给一个Thread构造器，下面的示例展示了如何使用Thread来驱动LiftOff对象：
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for LiftOff");
    }
} /* Output: (90% match)
Waiting for LiftOff
#0(9), #0(8), #0(7), #0(6), #0(5), #0(4), #0(3), #0(2), #0(1), #0(Liftoff!),
*///:~
