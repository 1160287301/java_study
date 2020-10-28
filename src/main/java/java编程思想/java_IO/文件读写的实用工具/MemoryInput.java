package java编程思想.java_IO.文件读写的实用工具;

import java.io.StringReader;

/**
 * 从内存输入
 */
public class MemoryInput {
    public static void main(String[] args) throws Exception {
        StringReader in = new StringReader(BufferedInputFile.read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java"));
        int c;
        while ((c = in.read()) != -1)
            System.out.print((char) c);
    }
}
