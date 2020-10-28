package java编程思想.java_IO.文件读写的实用工具;

import java.io.RandomAccessFile;

/**
 * 读写随机访问文件
 */
public class UsingRandomAccessFile {
    static String file = "rtest.java";

    static void display() throws Exception {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            System.out.println("value" + i + ": " + rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws Exception {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("the end of the file");
        rf.close();
        display();
        rf = new RandomAccessFile(file, "rw");
        /*
         * 因为double总是8字节长，所以为了用seek()查找第5个双精度值，你只需用5*8来产生查找位置。
         * */
        rf.seek(5 * 8);
        rf.writeDouble(47.001);
        rf.close();
        display();
    }

}
