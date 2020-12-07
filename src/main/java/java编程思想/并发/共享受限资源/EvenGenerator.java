package java编程思想.并发.共享受限资源;//: concurrency/EvenGenerator.java
// When threads collide.

/**
 * 一个任务有可能在另一个任务执行第一个对currentEvenValue的递增操作之后，但是没有执行第二个操作之前，调用next()方法（即，代码中被注释为“Dangerpointhere！”的地方）。这将使这个值处于“不恰当”的状态。为了证明这是可能发生的，EvenChecker.test()创建了一组EvenChecker对象，以连续地读取并输出同一个EvenGenerator，并测试检查每个数值是否都是偶数。如果不是，就会报告错误，而程序也将关闭。这个程序最终将失败，因为各个EvenChecker任务在EvenGenerator处于“不恰当的”状态时，仍能够访问其中的信息。但是，根据你使用的特定的操作系统和其他实现细节，直到EvenCenerator完成多次循环之前，这个问题都不会被探测到。如果你希望更快地发现失败，可以尝试着将对yield()的调用放置到第一个和第二个递增操作之间。这只是并发程序的部分问题——如果失败的概率非常低，那么即使存在缺陷，它们也可能看起来是正确的。
 * 有一点很重要，那就是要注意到递增程序自身也需要多个步骤，并且在递增过程中任务可能会被线程机制挂起——也就是说，在Java中，递增不是原子性的操作。因此，如果不保护任务，即使单一的递增也不是安全的。
 */
public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;

    public int next() {
        ++currentEvenValue; // Danger point here!
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
} /* Output: (Sample)
Press Control-C to exit
89476993 not even!
89476993 not even!
*///:~
