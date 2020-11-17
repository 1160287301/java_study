package java编程思想.枚举类型.常量相关方法;//: enumerated/CarWash.java

import java.util.EnumSet;

import static java编程思想.net.mindview.util.Print.print;

/**
 * 再看 一个 更 有趣 的 关于 洗车 的 例子。 每个 顾客 在 洗车 时， 都有 一个 选择 菜单， 每个 选择 对应 一个 不同 的 动作。 可以 将 一个 常量 相关 的 方法 关联 到 一个 选择 上， 再 使用 一个 EnumSet 来 保存 客户 的 选择：
 * 与 使用 匿名 内 部类 相比较， 定义 常量 相关 方法 的 语法 更 高效、 简洁。 这个 例子 也 展示 了 EnumSet 了 一些 特性。 因为 它是 一个 集合， 所以 对于 同一个 元素 而言， 只能 出现 一次， 因此 对 同一个 参数 重复 地 调用 add() 方法 会被 忽略 掉（ 这是 正确 的 行为， 因为 一个 bit 位 开关 只能“ 打开” 一次）。 同样 地， 向 EnumSet 添加 enum 实例 的 顺序 并不 重要， 因为 其 输出 的 次序 决 定于 enum 实例 定义 时 的 次序。
 */
public class CarWash {
    public enum Cycle {
        UNDERBODY {
            void action() {
                print("Spraying the underbody");
            }
        },
        WHEELWASH {
            void action() {
                print("Washing the wheels");
            }
        },
        PREWASH {
            void action() {
                print("Loosening the dirt");
            }
        },
        BASIC {
            void action() {
                print("The basic wash");
            }
        },
        HOTWAX {
            void action() {
                print("Applying hot wax");
            }
        },
        RINSE {
            void action() {
                print("Rinsing");
            }
        },
        BLOWDRY {
            void action() {
                print("Blowing dry");
            }
        };

        abstract void action();
    }

    EnumSet<Cycle> cycles =
            EnumSet.of(Cycle.BASIC, Cycle.RINSE);

    public void add(Cycle cycle) {
        cycles.add(cycle);
    }

    public void washCar() {
        for (Cycle c : cycles)
            c.action();
    }

    public String toString() {
        return cycles.toString();
    }

    public static void main(String[] args) {
        CarWash wash = new CarWash();
        print(wash);
        wash.washCar();
        // Order of addition is unimportant:
        wash.add(Cycle.BLOWDRY);
        wash.add(Cycle.BLOWDRY); // Duplicates ignored
        wash.add(Cycle.RINSE);
        wash.add(Cycle.HOTWAX);
        print(wash);
        wash.washCar();
    }
} /* Output:
[BASIC, RINSE]
The basic wash
Rinsing
[BASIC, HOTWAX, RINSE, BLOWDRY]
The basic wash
Applying hot wax
Rinsing
Blowing dry
*///:~
