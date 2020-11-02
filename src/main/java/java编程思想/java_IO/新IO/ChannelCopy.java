package java编程思想.java_IO.新IO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 一旦调用read()来告知FileChannel向ByteBuffer存储字节，就必须调用缓冲器上的flip()，让它做好让别人读取字节的准备
 * （是的，这似乎有一点拙劣，但是请记住，它是很拙劣的，但却适用于获取最大速度）。如果我们打算使用缓冲器执行进一步的read()操作，
 * 我们也必须得调用clear()来为每个read()做好准备。这在下面这个简单文件复制程序中可以看到
 */
public class ChannelCopy {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        FileChannel in = new FileInputStream("data.txt").getChannel(),
                out = new FileOutputStream("dataCopy.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        while (in.read(buff) != -1) {
            buff.flip();
            out.write(buff);
            buff.clear();
        }

    }

}

/**
 * 然而，上面那个程序并不是处理此类操作的理想方式。特殊方法transferTo()和transferFrom()则允许我们将一个通道和另一个通道直接相连
 */
class TransferTo {
    public static void main(String[] args) throws Exception {
        FileChannel in = new FileInputStream("data.txt").getChannel(),
                out = new FileOutputStream("dataTransferTo.txt").getChannel();
        in.transferTo(0, in.size(), out);

    }
}