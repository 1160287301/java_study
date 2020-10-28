package java编程思想.java_IO.文件读写的实用工具;

import java.io.*;

/**
 * 基本文件输出
 */
public class BasicFileOutput {
    static String file = "BasicFileOutput.out";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null)
            out.println(lineCount++ + ": " + s);
        out.close();

    }

}

/*
 * 文本文件输出的快捷方式JavaSE5在PrintWriter中添加了一个辅助构造器，使得你不必在每次希望创建文本文件并向其中写入时，都去执行所有的装饰工作。
 * 下面是用这种快捷方式重写的BasicFileOutput.java
 * */
class FileOutPutShortCut {
    static String file = "FileOutPutShortCut.out";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("/Users/h/PycharmProjects/java_study/src/main/java/algorithm/归并排序.java")));


        PrintWriter out = new PrintWriter(file);
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null)
            out.println(lineCount++ + ": " + s);
        out.close();

    }
}