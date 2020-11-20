//: enumerated/RoShamBo5.java
// Multiple dispatching using an EnumMap of EnumMaps.
package java编程思想.枚举类型.多路分发;

import java.util.EnumMap;

import static java编程思想.枚举类型.多路分发.Outcome.*;

/**
 * 使用 EnumMap 能够 实现“ 真正 的” 两路 分发。 EnumMap 是 为 enum 专门 设计 的 一种 性能 非常 好的 特殊 Map。 由于 我们 的 目的 是 摸索 出 两种 未知 的 类型， 所以 可以 用 一个 EnumMap 的 EnumMap 来 实现 两路 分发：
 * 该 程序 在 一个 static 子句 中 初始化 EnumMap 对象， 具体 见表 格 似的 initRow() 方法 调用。 请注意 compete() 方法， 您可 以看 到， 在 一行 语句 中 发生了 两次 分发。
 */
enum RoShamBo5 implements Competitor<RoShamBo5> {
    PAPER, SCISSORS, ROCK;
    static EnumMap<RoShamBo5, EnumMap<RoShamBo5, Outcome>>
            table = new EnumMap<RoShamBo5,
            EnumMap<RoShamBo5, Outcome>>(RoShamBo5.class);

    static {
        for (RoShamBo5 it : RoShamBo5.values())
            table.put(it,
                    new EnumMap<RoShamBo5, Outcome>(RoShamBo5.class));
        initRow(PAPER, DRAW, LOSE, WIN);
        initRow(SCISSORS, WIN, DRAW, LOSE);
        initRow(ROCK, LOSE, WIN, DRAW);
    }

    static void initRow(RoShamBo5 it,
                        Outcome vPAPER, Outcome vSCISSORS, Outcome vROCK) {
        EnumMap<RoShamBo5, Outcome> row =
                RoShamBo5.table.get(it);
        row.put(RoShamBo5.PAPER, vPAPER);
        row.put(RoShamBo5.SCISSORS, vSCISSORS);
        row.put(RoShamBo5.ROCK, vROCK);
    }

    public Outcome compete(RoShamBo5 it) {
        return table.get(this).get(it);
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo5.class, 20);
    }
} /* Same output as RoShamBo2.java *///:~
