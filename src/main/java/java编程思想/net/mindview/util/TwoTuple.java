//: net/mindview/util/TwoTuple.java
package java编程思想.net.mindview.util;

public class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        first = a;
        second = b;
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
} ///:~
