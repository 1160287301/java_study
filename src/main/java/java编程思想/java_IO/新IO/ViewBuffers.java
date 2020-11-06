package java编程思想.java_IO.新IO;

import java.nio.*;

/**
 * 先用重载后的put()方法存储一个整数数组。接着get()和put()方法调用直接访问底层ByteBuffer中的某个整数位置。
 * 注意，这些通过直接与ByteBuffer对话访问绝对位置的方式也同样适用于基本类型。
 * 一旦底层的ByteBuffer通过视图缓冲器填满了整数或其他基本类型时，就可以直接被写到通道中了。
 * 正像从通道中读取那样容易，然后使用视图缓冲器可以把任何数据都转化成某一特定的基本类型。
 * 在下面的例子中，通过在同一个ByteBuffer上建立不同的视图缓冲器，将同一字节序列翻译成了short、int、float、long和double类型的数据
 */
public class ViewBuffers {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
        bb.rewind();
        System.out.print("ByteBuffer: ");
        while (bb.hasRemaining())
            System.out.print(bb.position() + " -> " + bb.get() + " , ");
        System.out.println();
        CharBuffer cb = ((ByteBuffer) bb.rewind()).asCharBuffer();
        System.out.print("CharBuffer: ");
        while (cb.hasRemaining())
            System.out.print(cb.position() + " -> " + cb.get() + " , ");
        System.out.println();
        FloatBuffer fb = ((ByteBuffer) bb.rewind()).asFloatBuffer();
        System.out.print("FloatBuffer: ");
        while (fb.hasRemaining())
            System.out.print(fb.position() + " -> " + fb.get() + " , ");
        System.out.println();
        IntBuffer ib = ((ByteBuffer) bb.rewind()).asIntBuffer();
        System.out.print("IntBuffer: ");
        while (ib.hasRemaining())
            System.out.print(ib.position() + " -> " + ib.get() + " , ");
        System.out.println();
        LongBuffer lb = ((ByteBuffer) bb.rewind()).asLongBuffer();
        System.out.print("LongBuffer: ");
        while (lb.hasRemaining())
            System.out.print(lb.position() + " -> " + lb.get() + " , ");
        System.out.println();
        ShortBuffer sb = ((ByteBuffer) bb.rewind()).asShortBuffer();
        System.out.print("ShortBuffer: ");
        while (sb.hasRemaining())
            System.out.print(sb.position() + " -> " + sb.get() + " , ");
        System.out.println();
        DoubleBuffer db = ((ByteBuffer) bb.rewind()).asDoubleBuffer();
        System.out.print("DoubleBuffer: ");
        while (db.hasRemaining())
            System.out.print(db.position() + " -> " + db.get() + " , ");
    }
}
