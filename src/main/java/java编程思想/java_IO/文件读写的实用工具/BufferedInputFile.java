package java编程思想.java_IO.文件读写的实用工具;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 缓冲区输入文件
 */
public class BufferedInputFile {
    public static String read(String filename) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null)
            sb.append(s + "\n");
        in.close();
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java"));
    }
}
