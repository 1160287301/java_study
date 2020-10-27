package java编程思想.java_IO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 这个工具与TextFile类似，因为它简化了读取二进制文件的过程
 * 其中一个重载方法接受File参数，第二个重载方法接受表示文件名的String参数。这两个方法都返回产生的byte数组。
 * available()方法被用来产生恰当的数组尺寸，并且read()方法的特定的重载版本填充了这个数组。
 */
public class BinaryFile {

    public static byte[] read(File bFile) throws Exception {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(bFile));
        try {
            byte[] data = new byte[bf.available()];
            bf.read(data);
            return data;
        } finally {
            bf.close();
        }
    }

    public static byte[] read(String bFile) throws Exception {
        return read(new File(bFile).getAbsoluteFile());
    }
}
