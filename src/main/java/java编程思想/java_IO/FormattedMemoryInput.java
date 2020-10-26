package java编程思想.java_IO;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * 格式化的内存输出
 */
public class FormattedMemoryInput {
    public static void main(String[] args) throws Exception {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(BufferedInputFile.read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java").getBytes()));
        try {
            while (true) {
                System.out.print((char) in.readByte());
            }
        } catch (Exception e) {
            System.err.println("end of stream");
        }


    }
}

/**
 * 如果我们从DataInputStream用readByte()一次一个字节地读取字符，那么任何字节的值都是合法的结果，因此返回值不能用来检测输入是否结束。
 * 相反，我们可以使用available()方法查看还有多少可供存取的字符。
 */
class TestEof {
    public static void main(String[] args) throws Exception {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(BufferedInputFile.read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java").getBytes()));
        while (in.available() != 0)
            System.out.print((char) in.readByte());
    }
}