package java编程思想.枚举类型.常量相关方法;//: enumerated/NotClasses.java
// {Exec: javap -c LikeClasses}

import static java编程思想.net.mindview.util.Print.print;

enum LikeClasses {
    WINKEN {
        void behavior() {
            print("Behavior1");
        }
    },
    BLINKEN {
        void behavior() {
            print("Behavior2");
        }
    },
    NOD {
        void behavior() {
            print("Behavior3");
        }
    };

    abstract void behavior();
}

/**
 * 然而， enum 实例 与 类 的 相似 之处 也 仅限 于此 了。 我们 并不能 真的 将 enum 实例 作为 一个 类型 来 使用：
 * 在 方法 f1() 中， 编译器 不允许 我们将 一个 enum 实例 当作 class 类型。 如果 我们 分析 一下 编译器 生成 的 代码， 就 知道 这种 行为 也是 很 正常 的。 因为 每个 enum 元素 都是 一个 LikeClasses 类型 的 static final 实例。 同时， 由于 它们 是 static 实例， 无法 访问 外 部类 的 非 static 元素 或 方法， 所以 对于 内部 的 enum 的 实例 而言， 其 行为 与 一般 的 内部 类 并不 相同。
 */
public class NotClasses {
    public static void main(String[] args) {
        LikeClasses.BLINKEN.behavior();
    }
    // void f1(LikeClasses.WINKEN instance) {} // Nope

} /* Output:
Compiled from "NotClasses.java"
abstract class LikeClasses extends java.lang.Enum{
public static final LikeClasses WINKEN;

public static final LikeClasses BLINKEN;

public static final LikeClasses NOD;
...
*///:~
