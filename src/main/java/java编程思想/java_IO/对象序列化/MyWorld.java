package java编程思想.java_IO.对象序列化;

//: io/MyWorld.java

import java.io.*;
import java.util.ArrayList;
import java.util.List;


class House implements Serializable {
}

class Animal implements Serializable {
    private String name;
    private House preferredHouse;

    Animal(String nm, House h) {
        name = nm;
        preferredHouse = h;
    }

    public String toString() {
        return name + "[" + super.toString() +
                "], " + preferredHouse + "\n";
    }
}

/**
 * 这里 有 一件 有趣 的 事： 我们 可以 通过 一个 字节数 组 来 使用 对象 序列 化，
 * 从而 实现 对 任何 可 Serializable 对象 的“ 深度 复制”（ deep copy）—— 深度 复制 意味着 我们 复制 的 是 整个 对象 网，
 * 而 不仅仅是 基本 对象 及其 引用。 复制 对 象将 在 本书 的 在 线 补充 材料 中进 行 深入 地 探讨。
 * 在这 个 例子 中， Animal 对象 包含 有 House 类型 的 字段。 在 main() 方法 中， 创建 了 一个 Animal 列表 并将 其 两次 序列 化，
 * 分别 送至 不同 的 流。 当 其 被 反 序列 化 还原 并被 打印 时， 我们 可以 看到 所示 的 执行 某 次 运行 后的 结果（ 每次 运行时，
 * 对象 将会 处在 不同 的 内存 地址）。 当然， 我们 期望 这些 反 序列 化 还原 后的 对象 地址 与 原来 的 地址 不同。 但 请注意，
 * 在 animals1 和 animals2 中 却 出现 了 相同 的 地址， 包括 二者 共享 的 那个 指向 House 对象 的 引用。 另一方面， 当 恢复 animals3 时，
 * 系统 无法 知道 另一个 流 内 的 对象 是 第一个 流 内 的 对象 的 别名， 因此 它 会 产生 出 完全 不同 的 对象 网。
 * 只要 将 任何 对象 序列 化 到 单 一流 中， 就可以 恢复 出 与我 们 写出 时 一样 的 对象 网， 并且 没有 任何 意外 重复 复制 出 的 对象。
 * 当然， 我们 可以 在 写出 第一个 对象 和 写出 最后 一个 对象 期间 改变 这些 对象 的 状态，
 * 但是 这是 我们 自己的 事； 无论 对象 在被 序列 化 时 处于 什么 状态（ 无论 它们 和 其他 对象 有 什么样 的 连接 关系），
 * 它们 都可以 被 写出。 如果 我们 想 保存 系统 状态， 最 安全 的 做法 是将 其 作为“ 原子” 操作 进行 序列 化。
 * 如果 我们 序列 化 了 某些 东西， 再去 做 其他 一些 工作， 再来 序列 化 更多 的 东西， 如此等等， 那么 将 无法 安全 地保 存 系统 状态。
 * 取而代之 的 是， 将 构成 系统 状态 的 所有 对象 都 置入 单一 容器 内， 并在 一个 操作 中将 该 容器 直接 写出。
 * 然后 同样 只需 一次 方法 调用， 即可 以 将其 恢复。
 */
public class MyWorld {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal("Bosco the dog", house));
        animals.add(new Animal("Ralph the hamster", house));
        animals.add(new Animal("Molly the cat", house));
        System.out.println("animals: " + animals);
        ByteArrayOutputStream buf1 =
                new ByteArrayOutputStream();
        ObjectOutputStream o1 = new ObjectOutputStream(buf1);
        o1.writeObject(animals);
        o1.writeObject(animals); // Write a 2nd set
        // Write to a different stream:
        ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
        ObjectOutputStream o2 = new ObjectOutputStream(buf2);
        o2.writeObject(animals);
        // Now get them back:
        ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buf1.toByteArray()));
        ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buf2.toByteArray()));
        List
                animals1 = (List) in1.readObject(),
                animals2 = (List) in1.readObject(),
                animals3 = (List) in2.readObject();
        System.out.println("animals1: " + animals1);
        System.out.println("animals2: " + animals2);
        System.out.println("animals3: " + animals3);
    }
} /* Output: (Sample)
animals: [Bosco the dog[Animal@addbf1], House@42e816
, Ralph the hamster[Animal@9304b1], House@42e816
, Molly the cat[Animal@190d11], House@42e816
]
animals1: [Bosco the dog[Animal@de6f34], House@156ee8e
, Ralph the hamster[Animal@47b480], House@156ee8e
, Molly the cat[Animal@19b49e6], House@156ee8e
]
animals2: [Bosco the dog[Animal@de6f34], House@156ee8e
, Ralph the hamster[Animal@47b480], House@156ee8e
, Molly the cat[Animal@19b49e6], House@156ee8e
]
animals3: [Bosco the dog[Animal@10d448], House@e0e1c6
, Ralph the hamster[Animal@6ca1c], House@e0e1c6
, Molly the cat[Animal@1bf216a], House@e0e1c6
]
*///:~

