package java编程思想.枚举类型.常量相关方法;//: enumerated/OverrideConstantSpecific.java

import static java编程思想.net.mindview.util.Print.print;
import static java编程思想.net.mindview.util.Print.printnb;

/**
 * 除了 实现 abstract 方法 以外， 程序员 是否 可以 覆盖 常量 相关 的 方法 呢？ 答案 是 肯定 的， 参考 下面 的 程序：
 */
public enum OverrideConstantSpecific {
    NUT, BOLT,
    WASHER {
        void f() {
            print("Overridden method");
        }
    };

    void f() {
        print("default behavior");
    }

    public static void main(String[] args) {
        for (OverrideConstantSpecific ocs : values()) {
            printnb(ocs + ": ");
            ocs.f();
        }
    }
} /* Output:
NUT: default behavior
BOLT: default behavior
WASHER: Overridden method
*///:~
