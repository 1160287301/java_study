//: annotations/AtUnitExample5.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.net.mindview.atunit.Test;
import java编程思想.net.mindview.atunit.TestObjectCleanup;
import java编程思想.net.mindview.atunit.TestObjectCreate;
import java编程思想.net.mindview.atunit.TestProperty;

import java.io.IOException;
import java.io.PrintWriter;

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
