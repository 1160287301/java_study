//: annotations/AtUnitExample1.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.注解.基于注解的单元测试.实现unit.Test;

/**
 * 使用@ Unit 进行 测试 的 类 必须 定义 在 某个 包 中（ 即 必须 包括 packae 声明）。 @Test 注解 被 置于 methodOneTest()、 m2()、 m3() failureTest() 以及 anotherDisappointment() 方法 之前， 它 告诉@ Unit 将 这些 方法 作为 单元 测试 来 运行。 同时，@ Test 将 验证 并 确保 这些 方法 没有 参数， 并且 返回 值 是 boolean 或 void。 程序员 编写 单元 测试 时， 唯一 需 要做 的 就是 决定 测试 是 成功 还是 失败，（ 对于 返回 值 为 boolean 的 方法） 应该 返回 ture 还是 false。
 */
public class AtUnitExample1 {
    public String methodOne() {
        return "This is methodOne";
    }

    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }

    @Test
    boolean methodOneTest() {
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean m2() {
        return methodTwo() == 2;
    }

    @Test
    private boolean m3() {
        return true;
    }

    // Shows output for failure:
    @Test
    boolean failureTest() {
        return false;
    }

    @Test
    boolean anotherDisappointment() {
        return false;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java java编程思想.注解.基于注解的单元测试.AtUnitExample1");
    }
} /* Output:
annotations.AtUnitExample1
  . methodOneTest
  . m2 This is methodTwo

  . m3
  . failureTest (failed)
  . anotherDisappointment (failed)
(5 tests)

>>> 2 FAILURES <<<
  annotations.AtUnitExample1: failureTest
  annotations.AtUnitExample1: anotherDisappointment
*///:~
