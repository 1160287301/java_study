package java编程思想.并发.共享受限资源;//: concurrency/SyncObject.java
// Synchronizing on another object.


import static java编程思想.net.mindview.util.Print.print;

class DualSynch {
    private Object syncObject = new Object();

    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            print("f()");
            Thread.yield();
        }
    }

    public synchronized void h() {
        for (int i = 0; i < 5; i++) {
            print("h()");
            Thread.yield();
        }
    }

    public void g() {
        synchronized (syncObject) {
            System.out.println(this);
            for (int i = 0; i < 5; i++) {
                print("g()");
                Thread.yield();
            }
        }
    }

    public void i() {
        synchronized (syncObject) {
            System.out.println(this);
            for (int i = 0; i < 5; i++) {
                print("i()");
                Thread.yield();
            }
        }
    }
}

/**
 * 有时必须在另一个对象上同步，但是如果你要这么做，就必须确保所有相关的任务都是在同一个对象上同步的。下面的示例演示了两个任务可以同时进入同一个对象，只要这个对象上的方法是在不同的锁上同步的即可：
 */
public class SyncObject {
    public static void main(String[] args) {
        final DualSynch ds = new DualSynch();
        new Thread(() -> ds.g()).start();
        ds.i();
    }
} /* Output: (Sample)
g()
f()
g()
f()
g()
f()
g()
f()
g()
f()
*///:~
