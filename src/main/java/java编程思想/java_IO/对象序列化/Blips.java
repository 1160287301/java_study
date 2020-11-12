package java编程思想.java_IO.对象序列化;

import java.io.*;

class Blip1 implements Externalizable {
    public Blip1() {
        System.out.println("Blip1 Constructor");
    }
    public void writeExternal(ObjectOutput out)
            throws IOException {
        System.out.println("Blip1.writeExternal");
    }
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable {
    Blip2() {
        System.out.println("Blip2 Constructor");
    }
    public void writeExternal(ObjectOutput out)
            throws IOException {
        System.out.println("Blip2.writeExternal");
    }
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }
}

/**
 * 正如 大家 所 看到 的， 默认 的 序列 化 机制 并不 难操纵。 然而， 如果 有 特殊 的 需要 那又 该 怎么办 呢？
 * 例如， 也许 要 考虑 特殊 的 安全 问题， 而且 你 不 希望 对象 的 某一 部分 被 序列 化； 或者 一个 对象 被 还原 以后，
 * 某 子 对象 需要 重新 创建， 从而 不 必将 该 子 对象 序列 化。 在这 些 特殊 情况下，
 * 可通过 实现 Externalizable 接口—— 代替 实现 Serializable 接口—— 来 对 序列 化过 程 进行 控制。
 * 这个 Externalizable 接口 继承 了 Serializable 接口， 同时 增添 了 两个 方法： writeExternal() 和 readExternal()。
 * 这 两个 方法 会在 序列 化 和 反 序列 化 还原 的 过程中 被 自动 调用， 以便 执行 一些 特殊 操作。
 * 下面 这个 例子 展示 了 Externalizable 接口 方法 的 简单 实现。 注意 Blip1 和 Blip2 除了 细微 的 差别 之外，
 * 几乎 完全 一致（ 研究 一下 代码， 看看 你能 否 发现）：
 *
 */
public class Blips {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        System.out.println("Constructing objects:");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2();
        ObjectOutputStream o = new ObjectOutputStream(
                new FileOutputStream("Blips.out"));
        System.out.println("Saving objects:");
        o.writeObject(b1);
        o.writeObject(b2);
        o.close();
        // Now get them back:
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Blips.out"));
        System.out.println("Recovering b1:");
        b1 = (Blip1)in.readObject();
        // OOPS! Throws an exception:
//! System.out.println("Recovering b2:");
//! b2 = (Blip2)in.readObject();
    }
} /* Output:
Constructing objects:
Blip1 Constructor
Blip2 Constructor
Saving objects:
Blip1.writeExternal
Blip2.writeExternal
Recovering b1:
Blip1 Constructor
Blip1.readExternal
*///:~

