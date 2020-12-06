package java编程思想.并发.基本的线程机制;//: concurrency/ResponsiveUI.java
// User interface responsiveness.
// {RunByHand}

class UnresponsiveUI {
    private volatile double d = 1;

    public UnresponsiveUI() throws Exception {
        while (d > 0)
            d = d + (Math.PI + Math.E) / d;
        System.in.read(); // Never gets here
    }
}

/**
 * UnresponsiveUI在一个无限的while循环里执行运算，显然程序不可能到达读取控制台输入的那一行（编译器被欺骗了，相信while的条件使得程序能到达读取控制台输入的那一行）。如果把建立UnresponsiveUI的那一行的注释解除掉再运行程序，那么要终止它的话，就只能杀死这个进程。要想让程序有响应，就得把计算程序放在run()方法中，这样它就能让出处理器给别的程序。当你按下“回车”键的时候，可以看到计算确实在作为后台程序运行，同时还在等待用户输入。
 */
public class ResponsiveUI extends Thread {
    private static volatile double d = 1;

    public ResponsiveUI() {
        setDaemon(true);
        start();
    }

    public void run() {
        while (true) {
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception {
        //! new UnresponsiveUI(); // Must kill this process
        new ResponsiveUI();
        System.in.read();
        System.out.println(d); // Shows progress
    }
} ///:~
