//: annotations/AtUnitComposition.java
// Creating non-embedded tests.
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.net.mindview.atunit.Test;

/**
 * 或者 你 还可以 使用 组合 的 方式 创建 非 嵌入 式 的 测试：
 */
public class AtUnitComposition {
    AtUnitExample1 testObject = new AtUnitExample1();

    @Test
    boolean _methodOne() {
        return
                testObject.methodOne().equals("This is methodOne");
    }

    @Test
    boolean _methodTwo() {
        return testObject.methodTwo() == 2;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitComposition");
    }
} /* Output:
annotations.AtUnitComposition
  . _methodOne
  . _methodTwo This is methodTwo

OK (2 tests)
*///:~
