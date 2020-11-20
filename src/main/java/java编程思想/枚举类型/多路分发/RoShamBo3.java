//: enumerated/RoShamBo3.java
// Using constant-specific methods.
package java编程思想.枚举类型.多路分发;

import static java编程思想.枚举类型.多路分发.Outcome.*;

/**
 * 常量 相关 的 方法 允许 我们 为 每个 enum 实例 提供 方法 的 不同 实现， 这使 得 常量 相关 的 方法 似乎是 实现 多路 分发 的 完美 解决 方案。 不过， 通过 这种 方式， enum 实例 虽然 可以 具有 不同 的 行为， 但它 们 仍然 不是 类型， 不能 将其 作为 方法 签名 中的 参数 类型 来 使用。 最好 的 办法 是将 enum 用在 switch 语句 中， 见 下例：
 */
public enum RoShamBo3 implements Competitor<RoShamBo3> {
    PAPER {
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default: // To placate the compiler
                case PAPER:
                    return DRAW;
                case SCISSORS:
                    return LOSE;
                case ROCK:
                    return WIN;
            }
        }
    },
    SCISSORS {
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default:
                case PAPER:
                    return WIN;
                case SCISSORS:
                    return DRAW;
                case ROCK:
                    return LOSE;
            }
        }
    },
    ROCK {
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default:
                case PAPER:
                    return LOSE;
                case SCISSORS:
                    return WIN;
                case ROCK:
                    return DRAW;
            }
        }
    };

    public abstract Outcome compete(RoShamBo3 it);

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo3.class, 20);
    }
} /* Same output as RoShamBo2.java *///:~
