//: annotations/StackLStringTest.java
// Applying @Unit to generics.
package java编程思想.注解.基于注解的单元测试.将unit用于泛型;

import java编程思想.net.mindview.util.OSExecute;
import java编程思想.注解.基于注解的单元测试.实现unit.Test;

public class StackLStringTest extends StackL<String> {
    @Test
    void _push() {
        push("one");
        assert top().equals("one");
        push("two");
        assert top().equals("two");
    }

    @Test
    void _pop() {
        push("one");
        push("two");
        assert pop().equals("two");
        assert pop().equals("one");
    }

    @Test
    void _top() {
        push("A");
        push("B");
        assert top().equals("B");
        assert top().equals("B");
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit StackLStringTest");
    }
} /* Output:
annotations.StackLStringTest
  . _push
  . _pop
  . _top
OK (3 tests)
*///:~
