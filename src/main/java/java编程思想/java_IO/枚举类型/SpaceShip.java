package java编程思想.java_IO.枚举类型;

/**
 * 覆盖 toSring() 方法， 给我 们 提供 了 另一种 方式 来 为 枚举 实例 生成 不同 的 字符串 描述 信息。
 * 在下 面的 示例 中， 我们 使用 的 就是 实例 的 名字， 不过 我们 希望 改变 其 格式。
 * 覆盖 enum 的 toSring() 方法 与 覆盖 一般 类 的 方法 没有 区别：
 */
//: enumerated/SpaceShip.java
public enum SpaceShip {
    SCOUT, CARGO, TRANSPORT, CRUISER, BATTLESHIP, MOTHERSHIP;

    /**
     * toString() 方法 通过 调用 name() 方法 取得 SpaceShip 的 名字， 然后 将其 修改 为 只有 首 字母 大写 的 格式。
     *
     * @return
     */
    public String toString() {
        String id = name();
        String lower = id.substring(1).toLowerCase();
        return id.charAt(0) + lower;
    }

    public static void main(String[] args) {
        for (SpaceShip s : values()) {
            System.out.println(s);
        }
    }
} /* Output:
Scout
Cargo
Transport
Cruiser
Battleship
Mothership
*///:~
