//: enumerated/EnumSets.java
// Operations on EnumSets
package java编程思想.枚举类型.使用EnumSet代替标志;

import java.util.EnumSet;

import static java编程思想.枚举类型.使用EnumSet代替标志.AlarmPoints.*;

/**
 * 使用staticimport可以简化enum常量的使用。EnumSet的方法的名字都相当直观，你可以查阅JDK文档找到其完整详细的描述。如果仔细研究了EnumSet的文档，你还会发现一个有趣的地方：of()方法被重载了很多次，不但为可变数量参数进行了重载，而且为接收2至5个显式的参数的情况都进行了重载。这也从侧面表现了EnumSet对性能的关注。因为，其实只使用可变参数已经可以解决整个问题了，但是对比显式的参数，会有一点性能损失。采用现在这种设计，当你只使用2到5个参数调用of()方法时，你可以调用对应的重载过的方法（速度稍快一点），而当你使用一个参数或多过5个参数时，你调用的将是使用可变参数的of()方法。注意，如果你只使用一个参数，编译器并不会构造可变参数的数组，所以与调用只有一个参数的方法相比，也就不会有额外的性能损耗。
 */
public class EnumSets {
    public static void main(String[] args) {
        EnumSet<AlarmPoints> points =
                EnumSet.noneOf(AlarmPoints.class); // Empty set
        points.add(BATHROOM);
        System.out.println(points);
        points.addAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points = EnumSet.allOf(AlarmPoints.class);
        System.out.println(points);
        points.removeAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points.removeAll(EnumSet.range(OFFICE1, OFFICE4));
        System.out.println(points);
        points = EnumSet.complementOf(points);
        System.out.println(points);
    }
} /* Output:
[BATHROOM]
[STAIR1, STAIR2, BATHROOM, KITCHEN]
[LOBBY, OFFICE1, OFFICE2, OFFICE3, OFFICE4, BATHROOM, UTILITY]
[LOBBY, BATHROOM, UTILITY]
[STAIR1, STAIR2, OFFICE1, OFFICE2, OFFICE3, OFFICE4, KITCHEN]
*///:~
