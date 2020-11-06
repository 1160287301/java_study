package java编程思想.java_IO.新IO;

import java.nio.ByteBuffer;

/**
 * 尽管ByteBuffer只能保存字节类型的数据，但是它具有可以从其所容纳的字节中产生出各种不同基本类型值的方法。下面这个例子展示了怎样使用这些方法来插入和抽取各种数值
 * 在分配一个ByteBuffer之后，可以通过检测它的值来查看缓冲器的分配方式是否将其内容自动置零——它确实是这样做了。这里一共检测了1024个值（由缓冲器的limit()决定），并且所有的值都是零。
 * 向ByteBuffer插入基本类型数据的最简单的方法是：利用asCharBuffer()、asShortBuffer()等获得该缓冲器上的视图，然后使用视图的put()方法。
 * 我们会发现此方法适用于所有基本数据类型。仅有一个小小的例外，即，使用ShortBuffer的put()方法时，需要进行类型转换（注意类型转换会截取或改变结果）。
 * 而其他所有的视图缓冲器在使用put()方法时，不需要进行类型转换
 */
public class GetData {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < bb.limit()) {
            if (bb.get() != 0)
                System.out.println("nonzero");
        }
        System.out.println("i = " + i);
        bb.rewind();
        bb.asCharBuffer().put("Howdy!");
        char c;
        while ((c = bb.getChar()) != 0)
            System.out.print(c + " ");
        System.out.println();
        bb.rewind();
        bb.asShortBuffer().put((short) 471142);
        System.out.println(bb.getShort());
        bb.rewind();
        bb.asIntBuffer().put(99471142);
        System.out.println(bb.getInt());
        bb.rewind();
        bb.asLongBuffer().put(99471142);
        System.out.println(bb.getLong());
        bb.rewind();
        bb.asFloatBuffer().put(99471142);
        System.out.println(bb.getFloat());
        bb.rewind();
        bb.asDoubleBuffer().put(99471142);
        System.out.println(bb.getDouble());
        bb.rewind();

    }

}
