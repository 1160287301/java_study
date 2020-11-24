//: annotations/AtUnitExample2.java
// Assertions and exceptions can be used in @Tests.
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.注解.基于注解的单元测试.实现unit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Unit 中 并没有 JUnit 里 的 特殊 的 assert 方法， 不过@ Test 方法 仍然 允许 程序员 返回 void（ 如果 你 还是 想用 ture 或 false 的 话， 你 仍然 可 以用 boolean 作为 方法 返回 值 类型）， 这是@ Test 方法 的 第二 种 形式。 在 这种 情况下， 要 表示 测试 成功， 可以 使用 Java 的 assert 语句。 Java 的 断言 机制 一般 要求 程序员 在 java 命令 行中 加上- ea 标志， 不过@ Unit 已经 自动 打 开了 该 功能。 而要 表示 测试 失 败的 话， 你 甚至 可以 使用 异常。@ Unit 的 设计 目标 之一 就是 尽可能 少地 添加 额外 的 语法， 而 Java 的 assert 和 异常 对于 报告 错误 而言， 已经 足够 了。 一个 失败 的 assert 或 从 测试 方法 中 抛出 异常， 都将 被 看作 一个 失败 的 测试， 但是@ Unit 并不 会 就在 这个 失败 的 测试 上 打住， 它 会 继续 运行， 直到 所有 的 测试 都 运行 完毕。 下面 是一 个 示例 程序：
 */
public class AtUnitExample2 {
    public String methodOne() {
        return "This is methodOne";
    }

    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }

    @Test
    void assertExample() {
        assert methodOne().equals("This is methodOne");
    }

    @Test
    void assertFailureExample() {
        assert 1 == 2 : "What a surprise!";
    }

    @Test
    void exceptionExample() throws IOException {
        new FileInputStream("nofile.txt"); // Throws
    }

    @Test
    boolean assertAndReturn() {
        // Assertion with message:
        assert methodTwo() == 2 : "methodTwo must equal 2";
        return methodOne().equals("This is methodOne");
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitExample2");
    }
} /* Output:
annotations.AtUnitExample2
  . assertExample
  . assertFailureExample java.lang.AssertionError: What a surprise!
(failed)
  . exceptionExample java.io.FileNotFoundException: nofile.txt (The system cannot find the file specified)
(failed)
  . assertAndReturn This is methodTwo

(4 tests)

>>> 2 FAILURES <<<
  annotations.AtUnitExample2: assertFailureExample
  annotations.AtUnitExample2: exceptionExample
*///:~
