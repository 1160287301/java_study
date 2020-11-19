//: enumerated/Competitor.java
// Switching one enum on another.
package java编程思想.枚举类型.多路分发;

public interface Competitor<T extends Competitor<T>> {
    Outcome compete(T competitor);
} ///:~
