package java编程思想.并发.新类库之中的构件;//: concurrency/Fat.java
// Objects that are expensive to create.

/**
 * 为了创建一个使用 pool 的示例，我们可以使用Fat，这是一种创建代价高昂的对象类型，因为它的构造器运行起来很耗时：
 */
public class Fat {
    private volatile double d; // Prevent optimization
    private static int counter = 0;
    private final int id = counter++;

    public Fat() {
        // Expensive, interruptible operation:
        for (int i = 1; i < 10000; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }

    public void operation() {
        System.out.println(this);
    }

    public String toString() {
        return "Fat id: " + id;
    }
} ///:~
