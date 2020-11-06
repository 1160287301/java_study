package java编程思想.java_IO.新IO;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 视图缓冲器（viewbuffer）可以让我们通过某个特定的基本数据类型的视窗查看其底层的ByteBuffer。
 * ByteBuffer依然是实际存储数据的地方，“支持”着前面的视图，因此，对视图的任何修改都会映射成为对ByteBuffer中数据的修改。
 * 正如我们在上一示例看到的那样，这使我们可以很方便地向ByteBuffer插入数据。
 * 视图还允许我们从ByteBuffer一次一个地（与ByteBuffer所支持的方式相同）或者成批地（放入数组中）读取基本类型值。
 * 在下面这个例子中，通过IntBuffer操纵ByteBuffer中的int型数据
 */
public class IntBufferDemo {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = bb.asIntBuffer();
        ib.put(new int[]{11, 42, 47, 99, 143, 811, 1016});
        System.out.println(ib.get(3));
        ib.put(3, 1811);
        ib.flip();
        while (ib.hasRemaining()) {
            int i = ib.get();
            System.out.println(i);
        }
    }
}