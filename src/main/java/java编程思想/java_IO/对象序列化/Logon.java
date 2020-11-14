package java编程思想.java_IO.对象序列化;

//: io/Logon.java
// Demonstrates the "transient" keyword.

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 如果 我们 正在 操作 的 是 一个 Serializable 对象， 那么 所有 序列 化 操作 都会 自动 进行。
 * 为了 能够 予以 控制， 可 以用 transient（ 瞬时） 关键字 逐个 字段 地 关闭 序列 化，
 * 它的 意思是“ 不用 麻烦 你 保存 或 恢复 数据—— 我自己 会 处理 的”。
 * 例如， 假设 某个 Login 对象 保存 某个 特定 的 登录 会话 信息。 登录 的 合法性 通过 校验 之后， 我们 想把 数据 保存 下来， 但不 包括 密码。
 * 为做 到这 一点， 最简单 的 办法 是 实现 Serializable， 并将 password 字段 标志 为 transient。 下面 是 具体 的 代码：
 */
public class Logon implements Serializable {
    private Date date = new Date();
    private String username;
    private transient String password;

    public Logon(String name, String pwd) {
        username = name;
        password = pwd;
    }

    public String toString() {
        return "logon info: \n   username: " + username +
                "\n   date: " + date + "\n   password: " + password;
    }

    public static void main(String[] args) throws Exception {
        Logon a = new Logon("Hulk", "myLittlePony");
        System.out.println("logon a = " + a);
        ObjectOutputStream o = new ObjectOutputStream(
                new FileOutputStream("Logon.out"));
        o.writeObject(a);
        o.close();
        TimeUnit.SECONDS.sleep(1); // Delay
        // Now get them back:
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Logon.out"));
        System.out.println("Recovering object at " + new Date());
        a = (Logon) in.readObject();
        System.out.println("logon a = " + a);
    }
} /* Output: (Sample)
logon a = logon info:
   username: Hulk
   date: Sat Nov 19 15:03:26 MST 2005
   password: myLittlePony
Recovering object at Sat Nov 19 15:03:28 MST 2005
logon a = logon info:
   username: Hulk
   date: Sat Nov 19 15:03:26 MST 2005
   password: null
*///:~

