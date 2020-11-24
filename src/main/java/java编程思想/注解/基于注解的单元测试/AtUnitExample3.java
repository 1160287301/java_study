//: annotations/AtUnitExample3.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.注解.基于注解的单元测试.实现unit.Test;
import java编程思想.注解.基于注解的单元测试.实现unit.TestObjectCreate;

/**
 * 对每一个单元测试而言，@Unit都会用默认的构造器，为该测试所属的类创建出一个新的实例。并在此新创建的对象上运行测试，然后丢弃该对象，以避免对其他测试产生副作用。如此创建对象导致我们依赖于类的默认构造器。如果你的类没有默认构造器，或者新对象需要复杂的构造过程，那么你可以创建一个static方法专门负责构造对象，然后用@TestObjectCreaet注解将该方法标记出来，就像这样：
 * 加入了@TestObjectCreaet注解的方法必须声明为static，且必须返回一个你正在测试的类型的对象，这一切都由@Unit负责确保成立。
 */
public class AtUnitExample3 {
    private int n;

    public AtUnitExample3(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public String methodOne() {
        return "This is methodOne";
    }

    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }

    @TestObjectCreate
    static AtUnitExample3 create() {
        return new AtUnitExample3(47);
    }

    @Test
    boolean initialization() {
        return n == 47;
    }

    @Test
    boolean methodOneTest() {
        return methodOne().equals("This is methodOne");
    }

    @Test
    boolean m2() {
        return methodTwo() == 2;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitExample3");
    }
} /* Output:
annotations.AtUnitExample3
  . initialization
  . methodOneTest
  . m2 This is methodTwo

OK (3 tests)
*///:~
