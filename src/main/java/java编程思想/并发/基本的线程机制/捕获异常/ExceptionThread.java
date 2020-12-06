package java编程思想.并发.基本的线程机制.捕获异常;//: concurrency/ExceptionThread.java
// {ThrowsException}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 由于线程的本质特性，使得你不能捕获从线程中逃逸的异常。一旦异常逃出任务的run()方法，它就会向外传播到控制台，除非你采取特殊的步骤捕获这种错误的异常。在JavaSE5之前，你可以使用线程组来捕获这些异常，但是有了JavaSE5，就可以用Executor来解决这个问题，因此你就不再需要了解有关线程组的任何知识了（除非要理解遗留代码，请查看可以从www.MindView.net下载的《ThinkinginJava（2ndEdition）》，以了解线程组的细节）。下面的任务总是会抛出一个异常，该异常会传播到其run()方法的外部，并且main()展示了当你运行它时所发生的事情：
 */
public class ExceptionThread implements Runnable {
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
} ///:~
