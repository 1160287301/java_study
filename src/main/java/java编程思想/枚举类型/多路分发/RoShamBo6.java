//: enumerated/RoShamBo6.java
// Enums using "tables" instead of multiple dispatch.
package java编程思想.枚举类型.多路分发;

import static java编程思想.枚举类型.多路分发.Outcome.*;

enum RoShamBo6 implements Competitor<RoShamBo6> {
    PAPER, SCISSORS, ROCK;
    private static Outcome[][] table = {
            {DRAW, LOSE, WIN}, // PAPER
            {WIN, DRAW, LOSE}, // SCISSORS
            {LOSE, WIN, DRAW}, // ROCK
    };

    public Outcome compete(RoShamBo6 other) {
        return table[this.ordinal()][other.ordinal()];
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo6.class, 20);
    }
} ///:~
