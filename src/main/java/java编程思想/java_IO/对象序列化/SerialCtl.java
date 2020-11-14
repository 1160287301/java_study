package java编程思想.java_IO.对象序列化;

//: io/SerialCtl.java
// Controlling serialization by adding your own
// writeObject() and readObject() methods.

import java.io.*;

/**
 * 在这 个 例子 中， 有一个 String 字段 是 普通 字段， 而 另一个 是 transient 字段， 用来 证明 非 transient 字段 由 defaultWriteObject() 方法 保存，
 * 而 transient 字段 必须 在 程序 中 明确 保存 和 恢复。 字段 是在 构造 器 内部 而 不是 在 定义 处 进行 初始化 的，
 * 以此 可以 证实 它们 在 反 序列 化 还原 期间 没有 被 一些 自动化 机制 初始化。
 */
public class SerialCtl implements Serializable {
    private String a;
    private transient String b;

    public SerialCtl(String aa, String bb) {
        a = "Not Transient: " + aa;
        b = "Transient: " + bb;
    }

    public String toString() {
        return a + "\n" + b;
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(b);
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        b = (String) stream.readObject();
    }

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        SerialCtl sc = new SerialCtl("Test1", "Test2");
        System.out.println("Before:\n" + sc);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(buf);
        o.writeObject(sc);
        // Now get it back:
        ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(buf.toByteArray()));
        SerialCtl sc2 = (SerialCtl) in.readObject();
        System.out.println("After:\n" + sc2);
    }
} /* Output:
Before:
Not Transient: Test1
Transient: Test2
After:
Not Transient: Test1
Transient: Test2
*///:~

