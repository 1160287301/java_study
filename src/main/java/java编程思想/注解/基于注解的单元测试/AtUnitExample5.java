//: annotations/AtUnitExample5.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.net.mindview.atunit.Test;
import java编程思想.net.mindview.atunit.TestObjectCleanup;
import java编程思想.net.mindview.atunit.TestObjectCreate;
import java编程思想.net.mindview.atunit.TestProperty;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 如果你的测试对象需要执行某些初始化工作，并且使用完毕后还需要进行某些清理工作，那么可以选择使用static@TestObjectCleanup方法，当测试对象使用结束后，该方法会为你执行清理工作。在下面的例子中，@TestObjectCreate为每个测试对象打开了一个文件，因此必须在丢弃测试对象的时候关闭该文件：
 * 从输出中我们可以看到，清理方法会在每个测试结束后自动运行。
 */
public class AtUnitExample5 {
    private String text;

    public AtUnitExample5(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }

    @TestProperty
    static PrintWriter output;
    @TestProperty
    static int counter;

    @TestObjectCreate
    static AtUnitExample5 create() {
        String id = Integer.toString(counter++);
        try {
            output = new PrintWriter("Test" + id + ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new AtUnitExample5(id);
    }

    @TestObjectCleanup
    static void
    cleanup(AtUnitExample5 tobj) {
        System.out.println("Running cleanup");
        output.close();
    }

    @Test
    boolean test1() {
        output.print("test1");
        return true;
    }

    @Test
    boolean test2() {
        output.print("test2");
        return true;
    }

    @Test
    boolean test3() {
        output.print("test3");
        return true;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitExample5");
    }
} /* Output:
annotations.AtUnitExample5
  . test1
Running cleanup
  . test2
Running cleanup
  . test3
Running cleanup
OK (3 tests)
*///:~
