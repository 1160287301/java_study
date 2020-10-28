package java编程思想.java_IO.标准_IO;

import java.io.PrintWriter;

/**
 * System.out是一个PrintStream，而PrintStream是一个OutputStream。PrintWriter有一个可以接受OutputStream作为参数的构造器。
 * 因此，只要需要，就可以使用那个构造器把System.out转换成PrintWriter
 * <p>
 * 重要的是要使用有两个参数的PrintWriter的构造器，并将第二个参数设为true，以便开启自动清空功能；否则，你可能看不到输出。
 * <p>
 */
public class ChangeSystemOut {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("hello world!");
    }
}
