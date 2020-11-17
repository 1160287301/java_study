//: enumerated/EnumMaps.java
// Basics of EnumMaps.
package java编程思想.枚举类型.使用EnumMap;

import java编程思想.枚举类型.使用EnumSet代替标志.AlarmPoints;

import java.util.EnumMap;
import java.util.Map;

import static java编程思想.net.mindview.util.Print.print;
import static java编程思想.net.mindview.util.Print.printnb;
import static java编程思想.枚举类型.使用EnumSet代替标志.AlarmPoints.*;

interface Command {
    void action();
}

/**
 * EnumMap是一种特殊的Map，它要求其中的键（key）必须来自一个enum。由于enum本身的限制，所以EnumMap在内部可由数组实现。因此EnumMap的速度很快，我们可以放心地使用enum实例在EnumMap中进行查找操作。不过，我们只能将enum的实例作为键来调用put()方法，其他操作与使用一般的Map差不多。下面的例子演示了命令设计模式的用法。一般来说，命令模式首先需要一个只有单一方法的接口，然后从该接口实现具有各自不同的行为的多个子类。接下来，程序员就可以构造命令对象，并在需要的时候使用它们了：
 * 与EnumSet一样，enum实例定义时的次序决定了其在EnumMap中的顺序。main()方法的最后部分说明，enum的每个实例作为一个键，总是存在的。但是，如果你没有为这个键调用put()方法来存入相应的值的话，其对应的值就是null。与常量相关的方法（constantspecificmethods将在下一节中介绍）相比，EnumMap有一个优点，那EnumMap允许程序员改变值对象，而常量相关的方法在编译期就被固定了。稍后你会看到，在你有多种类型的enum，而且它们之间存在互操作的情况下，我们可以用EnumMap实现多路分发（multipledispatching）。
 */
public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> em =
                new EnumMap<AlarmPoints, Command>(AlarmPoints.class);
        em.put(KITCHEN, new Command() {
            public void action() {
                print("Kitchen fire!");
            }
        });
        em.put(BATHROOM, new Command() {
            public void action() {
                print("Bathroom alert!");
            }
        });
        for (Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
            printnb(e.getKey() + ": ");
            e.getValue().action();
        }
        try { // If there's no value for a particular key:
            em.get(UTILITY).action();
        } catch (Exception e) {
            print(e);
        }
    }
} /* Output:
BATHROOM: Bathroom alert!
KITCHEN: Kitchen fire!
java.lang.NullPointerException
*///:~
