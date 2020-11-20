//: enumerated/RoShamBo4.java
package java编程思想.枚举类型.多路分发;

/**
 * 然而， RoShamBo3. java 还可以 压缩 简化 一下：
 * 其中， 具有 两个 参数 的 compete() 方法 执行 第二个 分发， 该 方法 执行 一系列 的 比较， 其 行为 类似 switch 语句。 这个 版本 的 程序 更 简短， 不过 却 比较 难理解。 对于 一个 大型 系统 而言， 难以 理解 的 代码 将 导致 整个 系统 不够 健壮。
 */
public enum RoShamBo4 implements Competitor<RoShamBo4> {
    ROCK {
        public Outcome compete(RoShamBo4 opponent) {
            return compete(SCISSORS, opponent);
        }
    },
    SCISSORS {
        public Outcome compete(RoShamBo4 opponent) {
            return compete(PAPER, opponent);
        }
    },
    PAPER {
        public Outcome compete(RoShamBo4 opponent) {
            return compete(ROCK, opponent);
        }
    };

    Outcome compete(RoShamBo4 loser, RoShamBo4 opponent) {
        return ((opponent == this) ? Outcome.DRAW
                : ((opponent == loser) ? Outcome.WIN
                : Outcome.LOSE));
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo4.class, 20);
    }
} /* Same output as RoShamBo2.java *///:~
