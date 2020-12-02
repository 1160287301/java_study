package java编程思想.并发.基本的线程机制;

import java.util.concurrent.ThreadFactory;

/**
 * 这与普通的ThreadFactory的唯一差异就是它将后台状态全部设置为了true。你现在可以用一个新的DaemonThreadFactory作为参数传递给Executor.newCachedThreadPool()：
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
} ///:~