package java编程思想.java_IO.对象序列化;

import java.io.*;
import java.util.*;

class Data implements Serializable {
    private int n;
    public Data(int n) { this.n = n; }
    public String toString() { return Integer.toString(n); }
}

/**
 * 对象 序列 化 特别“ 聪明” 的 一个 地方 是它 不仅 保存 了 对象 的“ 全景 图”， 而且 能 追踪 对象 内 所 包含 的 所有 引用，
 * 并 保存 那些 对象； 接着 又能 对对 象 内 包含 的 每个 这样 的 引用 进行 追踪； 依此类推。 这种 情况 有时 被称为“ 对象 网”，
 * 单个 对象 可与 之 建立 连接， 而且 它 还 包含 了 对象 的 引用 数组 以及 成员 对象。 如果 必须 保持 一套 自己的 对象 序列 化 机制，
 * 那么 维护 那些 可追踪 到 所有 链接 的 代码 可能 会 显得 非常 麻烦。 然而， 由于 Java 的 对象 序列 化 似乎 找 不出 什么 缺点，
 * 所以 请 尽量 不要 自己 动手， 让 它 用 优化 的 算法 自动 维护 整个 对象 网。
 * 下面 这个 例子 通过 对 链接 的 对象 生成 一个 worm（ 蠕虫） 对 序列 化 机制 进行 了 测试。 每个 对象 都与 worm 中的 下一 段 链接，
 * 同时 又与 属于 不 同类（ Data） 的 对象 引用 数组 链接：
 *
 */
public class Worm implements Serializable {
    private static Random rand = new Random(47);
    private Data[] d = {
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10))
    };
    private Worm next;
    private char c;
    // Value of i == number of segments
    public Worm(int i, char x) {
        System.out.println("Worm constructor: " + i);
        c = x;
        if(--i > 0)
            next = new Worm(i, (char)(x + 1));
    }
    public Worm() {
        System.out.println("Default constructor");
    }
    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c);
        result.append("(");
        for(Data dat : d)
            result.append(dat);
        result.append(")");
        if(next != null)
            result.append(next);
        return result.toString();
    }
    public static void main(String[] args)
            throws ClassNotFoundException, IOException {
        Worm w = new Worm(6, 'a');
        System.out.println("w = " + w);
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("worm.out"));
        out.writeObject("Worm storage\n");
        out.writeObject(w);
        out.close(); // Also flushes output
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("worm.out"));
        String s = (String)in.readObject();
        Worm w2 = (Worm)in.readObject();
        System.out.println(s + "w2 = " + w2);
        ByteArrayOutputStream bout =
                new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(bout);
        out2.writeObject("Worm storage\n");
        out2.writeObject(w);
        out2.flush();
        ObjectInputStream in2 = new ObjectInputStream(
                new ByteArrayInputStream(bout.toByteArray()));
        s = (String)in2.readObject();
        Worm w3 = (Worm)in2.readObject();
        System.out.println(s + "w3 = " + w3);
    }
} /* Output:
Worm constructor: 6
Worm constructor: 5
Worm constructor: 4
Worm constructor: 3
Worm constructor: 2
Worm constructor: 1
w = :a(853):b(119):c(802):d(788):e(199):f(881)
Worm storage
w2 = :a(853):b(119):c(802):d(788):e(199):f(881)
Worm storage
w3 = :a(853):b(119):c(802):d(788):e(199):f(881)
*///:~