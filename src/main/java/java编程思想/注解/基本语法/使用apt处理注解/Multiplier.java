//: annotations/Multiplier.java
// APT-based annotation processing.
package java编程思想.注解.基本语法.使用apt处理注解;

/**
 * java编程思想.注解.基本语法.使用apt处理注解.ExtractInterface 的 RetentionPolicy是SOURCE，因为当我们从一个使用了该注解的类中抽取出接口之后，没有必要再保留这些注解信息。下面的类有一个公共方法，我们将会把它抽取到一个有用接口中：
 */
@ExtractInterface("IMultiplier")
public class Multiplier {
    public int multiply(int x, int y) {
        int total = 0;
        for (int i = 0; i < x; i++)
            total = add(total, y);
        return total;
    }

    private int add(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        Multiplier m = new Multiplier();
        System.out.println("11*16 = " + m.multiply(11, 16));
    }
} /* Output:
11*16 = 176
*///:~
