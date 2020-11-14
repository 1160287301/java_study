package java编程思想.java_IO.枚举类型;//: enumerated/UpcastEnum.java
// No values() method if you upcast an enum

enum Search {HITHER, YON}

/**
 * 由于 values() 方法 是由 编译器 插入 到 enum 定义 中的 static 方法， 所以， 如果 你将 enum 实例 向上 转型 为 Enum，
 * 那么 values() 方法 就不 可 访问 了。 不过， 在 Class 中有 一个 getEnumConstants() 方法， 所以 即便 Enum 接口 中 没有 values() 方法，
 * 我们 仍然 可以 通过 Class 对象 取得 所有 enum 实例：
 */
public class UpcastEnum {
    public static void main(String[] args) {
        Search[] vals = Search.values();
        Enum e = Search.HITHER; // Upcast
        // e.values(); // No values() in Enum
        for (Enum en : e.getClass().getEnumConstants())
            System.out.println(en);
    }
} /* Output:
HITHER
YON
*///:~

/**
 * 因为 getEnumConstants() 是 Class 上 的 方法， 所以 你 甚至 可以 对不 是 枚举 的 类 调用 此 方法：
 * 只不过， 此时 该 方法 返回 null， 所以 当你 试图 使用 其 返回 的 结果 时会 发生 异常。
 */
class NonEnum {
    public static void main(String[] args) {
        Class<Integer> intClass = Integer.class;
        try {
            for (Object en : intClass.getEnumConstants())
                System.out.println(en);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
} /* Output:
java.lang.NullPointerException
*///:~