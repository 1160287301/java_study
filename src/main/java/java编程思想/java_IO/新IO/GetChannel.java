package java编程思想.java_IO.新IO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * JDK1.4的java.nio.*包中引入了新的JavaI/O类库，其目的在于提高速度。实际上，旧的I/O包已经使用nio重新实现过，以便充分利用这种速度提高，
 * 因此，即使我们不显式地用nio编写代码，也能从中受益。速度的提高在文件I/O和网络I/O中都有可能发生，我们在这里只研究前者[5]；
 * 对于后者，在《ThinkinginEnterpriseJava》中有论述。速度的提高来自于所使用的结构更接近于操作系统执行I/O的方式：通道和缓冲器。
 * 我们可以把它想像成一个煤矿，通道是一个包含煤层（数据）的矿藏，而缓冲器则是派送到矿藏的卡车。卡车载满煤炭而归，我们再从卡车上获得煤炭。
 * 也就是说，我们并没有直接和通道交互；我们只是和缓冲器交互，并把缓冲器派送到通道。通道要么从缓冲器获得数据，要么向缓冲器发送数据。
 * 唯一直接与通道交互的缓冲器是ByteBuffer——也就是说，可以存储未加工字节的缓冲器。
 * 当我们查询JDK文档中的java.nio.ByteBuffer时，会发现它是相当基础的类：通过告知分配多少存储空间来创建一个ByteBuffer对象，并且还有一个方法选择集，
 * 用于以原始的字节形式或基本数据类型输出和读取数据。但是，没办法输出或读取对象，即使是字符串对象也不行。这种处理虽然很低级，但却正好，
 * 因为这是大多数操作系统中更有效的映射方式。旧I/O类库中有三个类被修改了，用以产生FileChannel。
 * 这三个被修改的类是FileInputStream、FileOutputStream以及用于既读又写的RandomAccessFile。注意这些是字节操纵流，与低层的nio性质一致。
 * Reader和Writer这种字符模式类不能用于产生通道；但是java.nio.channels.Channels类提供了实用方法，用以在通道中产生Reader和Writer。
 * 下面的简单实例演示了上面三种类型的流，用以产生可写的、可读可写的及可读的通道。
 */
public class GetChannel {
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
        while (buff.hasRemaining()) {
            System.out.print((char) buff.get());
        }

    }
}
