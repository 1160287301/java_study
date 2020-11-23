//: annotations/HashSetTest.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.注解.定义注解.Test;

import java.util.HashSet;

/**
 * 下面 的 例子 使用 非 嵌入 式 的 测试， 并且 用到 了 断言， 它将 对 java. util. HashSet 执行 一些 简单 的 测试：
 */
public class HashSetTest {
    HashSet<String> testObject = new HashSet<String>();

    @Test
    void initialization() {
        assert testObject.isEmpty();
    }

    @Test
    void _contains() {
        testObject.add("one");
        assert testObject.contains("one");
    }

    @Test
    void _remove() {
        testObject.add("one");
        testObject.remove("one");
        assert testObject.isEmpty();
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit HashSetTest");
    }
} /* Output:
annotations.HashSetTest
  . initialization
  . _remove
  . _contains
OK (3 tests)
*///:~
