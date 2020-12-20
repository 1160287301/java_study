package java编程思想.并发.性能调优.免锁容器;//: concurrency/Tester.java
// Framework to test performance of concurrency containers.


import java编程思想.net.mindview.util.Generated;
import java编程思想.net.mindview.util.RandomGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @param <C> abstract方法containerInitializer()返回将被测试的初始化后的容器，它被存储在testContainer域中。另一个abstract方法startReadersAndWriters()启动读取者和写入者任务，它们将读取和修改待测容器。不同的测试在运行时将具有数量变化的读取者和写入者，这样就可以观察到锁竞争（针对synchronized容器而言）和写入（针对免锁容器而言）的效果。我们向构造器提供了各种有关测试的信息（参数标识符应该是自解释的），然后它会调用runTest()方法repetitions次。runTest()将创建一个CountDownLatch（因此测试可以知道所有任何何时完成）、初始化容器，然后调用startReadersAndWriters()，并等待它们全部完成。每个Reader和Writer类都基于TestTask，它可以度量其抽象方法test()的执行时间，然后在一个synchronized块中调用putResults()去存储度量结果。
 */
public abstract class Tester<C> {
    static int testReps = 10;
    static int testCycles = 1000;
    static int containerSize = 1000;

    abstract C containerInitializer();

    abstract void startReadersAndWriters();

    C testContainer;
    String testId;
    int nReaders;
    int nWriters;
    volatile long readResult = 0;
    volatile long readTime = 0;
    volatile long writeTime = 0;
    CountDownLatch endLatch;
    static ExecutorService exec = Executors.newCachedThreadPool();
    Integer[] writeData;

    Tester(String testId, int nReaders, int nWriters) {
        this.testId = testId + " " +
                nReaders + "r " + nWriters + "w";
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        writeData = Generated.array(Integer.class, new RandomGenerator.Integer(), containerSize);
        for (int i = 0; i < testReps; i++) {
            runTest();
            readTime = 0;
            writeTime = 0;
        }
    }

    void runTest() {
        endLatch = new CountDownLatch(nReaders + nWriters);
        testContainer = containerInitializer();
        startReadersAndWriters();
        try {
            endLatch.await();
        } catch (InterruptedException ex) {
            System.out.println("endLatch interrupted");
        }
        System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
        if (readTime != 0 && writeTime != 0)
            System.out.printf("%-27s %14d\n", "readTime + writeTime =", readTime + writeTime);
    }

    abstract class TestTask implements Runnable {
        abstract void test();

        abstract void putResults();

        long duration;

        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;
            synchronized (Tester.this) {
                putResults();
            }
            endLatch.countDown();
        }
    }

    public static void initMain(String[] args) {
        if (args.length > 0)
            testReps = new Integer(args[0]);
        if (args.length > 1)
            testCycles = new Integer(args[1]);
        if (args.length > 2)
            containerSize = new Integer(args[2]);
        System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
    }
} ///:~
