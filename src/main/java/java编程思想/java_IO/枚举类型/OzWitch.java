package java编程思想.java_IO.枚举类型;//: enumerated/OzWitch.java
// The witches in the land of Oz.


/**
 * 除了 不能 继承 自 一个 enum 之外， 我们 基本上 可以 将 enum 看作 一个 常规 的 类。 也就是说， 我们 可以向 enum 中 添加 方法。
 * enum 甚至 可以 有 main() 方法。 一般来说， 我们 希望 每个 枚举 实例 能够 返回 对 自身 的 描述，
 * 而 不仅仅 只是 默认 的 toString() 实现， 这 只能 返回 枚举 实例 的 名字。 为此， 你 可以 提供 一个 构造 器，
 * 专门 负责 处理 这个 额外 的 信息， 然后 添加 一个 方法， 返回 这个 描述 信息。 看 一看 下面 的 示例：
 */
public enum OzWitch {
    // Instances must be defined first, before methods:
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North"),
    EAST("Wicked Witch of the East, wearer of the Ruby " +
            "Slippers, crushed by Dorothy's house"),
    // 注意， 如果 你 打算 定义 自己的 方法， 那么 必须 在 enum 实例 序列 的 最后 添加 一个 分号。
    // 同时， Java 要求 你 必须 先 定义 enum 实例。 如果 在 定义 enum 实例 之前 定义 了 任何 方法 或 属性，
    // 那么 在 编译 时 就会 得到 错误 信息。
    SOUTH("Good by inference, but missing");
    private String description;

    // Constructor must be package or private access:
    private OzWitch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void main(String[] args) {
        for (OzWitch witch : OzWitch.values())
            System.out.println(witch + ": " + witch.getDescription());
    }
} /* Output:
WEST: Miss Gulch, aka the Wicked Witch of the West
NORTH: Glinda, the Good Witch of the North
EAST: Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house
SOUTH: Good by inference, but missing
*///:~
