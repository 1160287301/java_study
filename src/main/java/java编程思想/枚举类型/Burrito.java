//: enumerated/Burrito.java
package java编程思想.枚举类型;


import static java编程思想.枚举类型.Spiciness.*;

/**
 * 使用 static import 能够 将 enum 实例 的 标识符 带入 当前 的 命名 空间， 所以 无需 再用 enum 类型 来 修饰 enum 实例。
 * 这是 一个 好的 想法 吗？ 或者 还是 显 式 地 修饰 enum 实例 更好？ 这要 看 代码 的 复杂 程度 了。
 * 编译器 可以 确保 你 使 用的 是 正确 的 类型， 所以 唯一 需要 担心 的 是， 使用 静态 导入 会不会 导致 你的 代码 令人 难以 理解。
 * 多数 情况下， 使用 static import 还是 有 好处 的， 不过， 程序员 还是 应该 对 具体 情况 进行 具体 分析。
 * 注意， 在 定义 enum 的 同一个 文件 中， 这种 技巧 无法 使用； 如果 是在 默认 包(文件中不加package关键字, 默认包是java.lang) 中 定义 enum， 这种 技巧 也 无法 使用
 */
public class Burrito {
    Spiciness degree;

    public Burrito(Spiciness degree) {
        this.degree = degree;
    }

    public String toString() {
        return "Burrito is " + degree;
    }

    public static void main(String[] args) {
        System.out.println(new Burrito(NOT));
        System.out.println(new Burrito(MEDIUM));
        System.out.println(new Burrito(HOT));
    }
} /* Output:
Burrito is NOT
Burrito is MEDIUM
Burrito is HOT
*///:~
