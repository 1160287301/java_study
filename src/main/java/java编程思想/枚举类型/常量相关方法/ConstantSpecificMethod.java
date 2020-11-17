package java编程思想.枚举类型.常量相关方法;

//: enumerated/ConstantSpecificMethod.java

import java.text.DateFormat;
import java.util.Date;

/**
 * Java 的 enum 有一个 非常 有趣 的 特性， 即 它 允许 程序员 为 enum 实例 编写 方法， 从而 为 每个 enum 实例 赋予 各自 不同 的 行为。 要 实现 常量 相关 的 方法， 你 需要 为 enum 定义 一个 或 多个 abstract 方法， 然后 为 每个 enum 实例 实现 该 抽象 方法。 参考 下面 的 例子：
 */
public enum ConstantSpecificMethod {
    DATE_TIME {
        String getInfo() {
            return
                    DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
    };

    abstract String getInfo();

    public static void main(String[] args) {
        for (ConstantSpecificMethod csm : values())
            System.out.println(csm.getInfo());
    }
} /* (Execute to see output) *///:~

