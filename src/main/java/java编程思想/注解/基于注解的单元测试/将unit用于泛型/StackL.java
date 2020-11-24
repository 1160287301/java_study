package java编程思想.注解.基于注解的单元测试.将unit用于泛型;

//: enumerated/RoShamBo.java
// Common tools for RoShamBo examples.

import java.util.LinkedList;

/**
 * @param <T> 泛 型 为@ Unit 出了 一个 难题， 因为 我们 不可能“ 泛泛 地 测试”。 我们 必须 针对 某个 特定 类型 的 参数 或 参数 集 才能 进行 测试。 解决 的 办法 很 简单： 让 测试 类 继承 自 泛 型 类 的 一个 特定 版本 即可。 下面 是一 个 堆栈 的 例子：
 */
public class StackL<T> {
    private LinkedList<T> list = new LinkedList<T>();

    public void push(T v) {
        list.addFirst(v);
    }

    public T top() {
        return list.getFirst();
    }

    public T pop() {
        return list.removeFirst();
    }
} ///:~
