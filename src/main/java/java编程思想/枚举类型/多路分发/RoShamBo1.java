//: enumerated/RoShamBo1.java
// Demonstration of multiple dispatching.
package java编程思想.枚举类型.多路分发;

import java.util.Random;

import static java编程思想.枚举类型.多路分发.Outcome.*;

interface Item {
    Outcome compete(Item it);

    Outcome eval(Paper p);

    Outcome eval(Scissors s);

    Outcome eval(Rock r);
}

class Paper implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper p) {
        return DRAW;
    }

    public Outcome eval(Scissors s) {
        return WIN;
    }

    public Outcome eval(Rock r) {
        return LOSE;
    }

    public String toString() {
        return "Paper";
    }
}

class Scissors implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper p) {
        return LOSE;
    }

    public Outcome eval(Scissors s) {
        return DRAW;
    }

    public Outcome eval(Rock r) {
        return WIN;
    }

    public String toString() {
        return "Scissors";
    }
}

class Rock implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper p) {
        return WIN;
    }

    public Outcome eval(Scissors s) {
        return LOSE;
    }

    public Outcome eval(Rock r) {
        return DRAW;
    }

    public String toString() {
        return "Rock";
    }
}

/**
 * 当你 要 处理 多种 交互 类型 时， 程序 可能 会变 得 相当 杂乱。 举例 来说， 如果 一个 系统 要 分析 和 执行 数学 表达式。
 * 我们 可能 会 声明 Number. plus（ Number）、 Number. multiple（ Number） 等等， 其中 Number 是 各种 数字 对象 的 超 类。
 * 然而， 当你 声明 a. plus（ b） 时， 你 并不 知道 a 或 b 的 确切 类型， 那 你 如何 能 让 它们 正确地 交互 呢？
 * 你 可能 从未 思考 过 这个 问题 的 答案。 Java 只 支持 单 路 分发。
 * 也就是说， 如果 要 执行 的 操作 包含 了 不止 一个 类型 未知 的 对象 时， 那么 Java 的 动态 绑 定 机制 只能 处理 其中 一个 的 类型。
 * 这就 无法 解决 我们 上面 提到 的 问题。 所以， 你 必须 自己 来 判定 其他 的 类型， 从而 实现 自己的 动态 绑 定 行为。
 * 解决 上面 问题 的 办法 就是 多路 分发（ 在那 个 例子 中， 只有 两个 分发， 一般 称之为 两路 分发）。
 * 多 态 只能 发生 在 方法 调用 时， 所以， 如果 你想 使用 两路 分发， 那么 就必须 有两 个 方法 调用：
 * 第一个 方法 调用 决定 第一个 未知 类型， 第二个 方法 调用 决定 第二个 未知 的 类型。 要 利用 多路 分发，
 * 程序员 必须 为 每一个 类型 提供 一个 实际 的 方法 调用， 如果 你要 处理 两个 不同 的 类型 体系，
 * 就 需要 为 每个 类型 体系 执行 一个 方法 调用。 一般而言， 程序员 需要 有 设定 好的 某种 配置，
 * 以便 一个 方法 调用 能够 引出 更多 的 方法 调用， 从而 能够 在 这个 过程中 处理 多种 类型。
 * 为了 达到 这种 效果， 我们 需要 与 多个 方法 一同 工作： 因为 每个 分发 都 需要 一个 方法 调用。
 * 在下 面的 例子 中（ 实现 了“ 石头、 剪刀、 布” 游戏， 也称 为 RoShamBo） 对应 的 方法 是 compete() 和 eval()，
 * 二者 都是 同一个 类型 的 成员， 它们 可以 产生 三种 Outcome 实例 中的 一个 作为 结果[ 4]：
 */
public class RoShamBo1 {
    static final int SIZE = 20;
    private static Random rand = new Random(47);

    public static Item newItem() {
        switch (rand.nextInt(3)) {
            default:
            case 0:
                return new Scissors();
            case 1:
                return new Paper();
            case 2:
                return new Rock();
        }
    }

    public static void match(Item a, Item b) {
        System.out.println(
                a + " vs. " + b + ": " + a.compete(b));
    }

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++)
            match(newItem(), newItem());
    }
} /* Output:	
Rock vs. Rock: DRAW
Paper vs. Rock: WIN
Paper vs. Rock: WIN
Paper vs. Rock: WIN
Scissors vs. Paper: WIN
Scissors vs. Scissors: DRAW
Scissors vs. Paper: WIN
Rock vs. Paper: LOSE
Paper vs. Paper: DRAW
Rock vs. Paper: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Rock vs. Scissors: WIN
Rock vs. Paper: LOSE
Paper vs. Rock: WIN
Scissors vs. Paper: WIN
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
Paper vs. Scissors: LOSE
*///:~
