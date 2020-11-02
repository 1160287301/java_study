package java编程思想.java_IO.新IO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * 回过头看GetChannel.java这个程序就会发现，为了输出文件中的信息，我们必须每次只读取一个字节的数据，然后将每个byte类型强制转换成char类型。
 * 这种方法似乎有点原始——如果我们查看一下java.nio.CharBuffer这个类，将会发现它有一个toString()方法是这样定义的：
 * “返回一个包含缓冲器中所有字符的字符串。”既然ByteBuffer可以看作是具有asCharBuffer()方法的CharBuffer，那么为什么不用它呢？
 * 正如下面的输出语句中第一行所见，这种方法并不能解决问题
 */
public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        // 写文件
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes()));
        fc.close();
        // 添加到文件末尾
        fc = new RandomAccessFile("data.txt", "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap(" some more".getBytes()));
        fc.close();
        // 读文件
        fc = new FileInputStream("data.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        // 输出乱码
        System.out.println(buff.asCharBuffer());
        // 使用系统默认的编码来解码
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + ": " + Charset.forName(encoding).decode(buff));
        // 或者先编码
        fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes("UTF-16BE")));
        fc.close();
        fc = new FileInputStream("data2.txt").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());

        // 使用 charbuffer
        fc = new FileOutputStream("data2.txt").getChannel();
        buff = ByteBuffer.allocate(48);
        buff.asCharBuffer().put("some text asCharBuffer");
        fc.write(buff);
        fc.close();
        // 读取
        fc = new FileInputStream("data2.txt").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}

/**
 * 缓冲器容纳的是普通的字节，为了把它们转换成字符，我们要么在输入它们的时候对其进行编码（这样，它们输出时才具有意义），要么在将其从缓冲器输出时对它们进行解码。
 * 可以使用java.nio.charset.Charset类实现这些功能，该类提供了把数据编码成多种不同类型的字符集的工具
 */
class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Iterator<String> it = charsets.keySet().iterator();
        while (it.hasNext()) {
            String csName = it.next();
            System.out.print(csName);
            Iterator<String> aliases = charsets.get(csName).aliases().iterator();
            if (aliases.hasNext())
                System.out.print(": ");
            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if (aliases.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

    }
}