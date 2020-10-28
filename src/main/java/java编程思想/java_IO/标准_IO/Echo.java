package java编程思想.java_IO.标准_IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 按照标准I/O模型，Java提供了System.in、System.out和System.err。在整本书里，我们已经看到了怎样用System.out将数据写出到标准输出，其中System.out已经事先被包装成了printStream对象。System.err同样也是PrintStream，但System.in却是一个没有被包装过的未经加工的InputStream。这意味尽管我们可以立即使用System.out和System.err，但是在读取System.in之前必须对其进行包装。通常我们会用readLine()一次一行地读取输入，为此，我们将System.in包装成BufferedReader来使用这要求我们必须用InputStreamReader把System.in转换成Reader。下面这个例子将直接回显你所输入的每一行。
 * <p>
 * 使用异常规范是因为readLine()会抛出IOException。注意，System.in和大多数流一样，通常应该对它进行缓冲。
 */
public class Echo {
    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = stdin.readLine()) != null && s.length() != 0)
            System.out.println(s);
    }
}
