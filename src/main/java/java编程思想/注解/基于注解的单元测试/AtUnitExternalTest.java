//: annotations/AtUnitExternalTest.java
// Creating non-embedded tests.
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.net.mindview.atunit.Test;

/**
 * 如果 你 熟悉 JUnit， 你会 注意到@ Unit 的 输出 带有 更多 的 信息。 我们 可以 看到 当前 正在 运行 的 测试， 因此 测试 中的 输出 更是 有用， 而且 在 最后， 它 还能 告诉 我们 导致 错误 的 类 和 测试。 程序员 并非 必须 将 测试 方法 嵌入 到 原本 的 类 中， 因为 有时候 这 根本 做不到。 要 生成 一个 非 嵌入 式 的 测试， 最简单 的 办法 就是 继承：
 */
public class AtUnitExternalTest extends AtUnitExample1 {
    @Test
    boolean _methodOne() {
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean _methodTwo() {
        return methodTwo() == 2;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitExternalTest");
    }
} /* Output:
annotations.AtUnitExternalTest
  . _methodOne
  . _methodTwo This is methodTwo

OK (2 tests)
*///:~
