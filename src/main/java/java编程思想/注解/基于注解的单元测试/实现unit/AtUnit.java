//: net/mindview/atunit/AtUnit.java
// An annotation-based unit-test framework.
// {RunByHand}
package java编程思想.注解.基于注解的单元测试.实现unit;

import java编程思想.net.mindview.util.BinaryFile;
import java编程思想.net.mindview.util.ProcessFiles;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static java编程思想.net.mindview.util.Print.print;
import static java编程思想.net.mindview.util.Print.printnb;

/**
 * 要 实现 该 系统， 并 运行 测试， 我们 还需 使用 反射 机制 来 抽取 注解。 下面 这个 程序 通过 注解 中的 信息， 决定 如何 构造 测试 对象， 并在 测试 对象 上 运行 测试。 正是 由于 注解 的 帮助， 这个 程序 才 如此 短小 而 直接：
 * <p>
 * AtUnit. java 使用 了 net. mindview. util 中的 ProcessFiles 工具。 这个 类 还 实现 了 ProcessFiles. Strategy 接口， 该 接口 包含 process() 方法。 如此 一来， 便 可以 将 一个 AtUnit 实例 传给 ProcessFiles 的 构造 器。 ProcessFiles 构造 器 的 第二个 参数 告诉 ProcessFiles 查找 所有 扩展 名为 class 的 文件。 如果 你 没有 提供 命令行 参数， 这个 程序 会 遍历 当前 目录。 你也 可 以为 其 提供 多个 参数， 可以 是 类 文件（ 带有 或 不带. class 扩展名 都可）， 或者是 一些 目录。 由于@ Unit 将会 自动 找到 可 测试 的 类 和 方法， 所以 没有“ 套 件” 机制 的 必要[ 9]。
 */
public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<String>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) throws Exception {
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true); // Enable asserts
        new ProcessFiles(new AtUnit(), "class").start(args);
        if (failures == 0)
            print("OK (" + testsRun + " tests)");
        else {
            print("(" + testsRun + " tests)");
            print("\n>>> " + failures + " FAILURE" +
                    (failures > 1 ? "S" : "") + " <<<");
            for (String failed : failedTests)
                print("  " + failed);
        }
    }

    public void process(File cFile) {
        try {
            String cName = ClassNameFinder.thisClass(
                    BinaryFile.read(cFile));
            if (!cName.contains("."))
                return; // Ignore unpackaged classes
            testClass = Class.forName(cName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TestMethods testMethods = new TestMethods();
        Method creator = null;
        Method cleanup = null;
        for (Method m : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(m);
            if (creator == null)
                creator = checkForCreatorMethod(m);
            if (cleanup == null)
                cleanup = checkForCleanupMethod(m);
        }
        if (testMethods.size() > 0) {
            if (creator == null)
                try {
                    if (!Modifier.isPublic(testClass
                            .getDeclaredConstructor().getModifiers())) {
                        print("Error: " + testClass +
                                " default constructor must be public");
                        System.exit(1);
                    }
                } catch (NoSuchMethodException e) {
                    // Synthesized default constructor; OK
                }
            print(testClass.getName());
        }
        for (Method m : testMethods) {
            printnb("  . " + m.getName() + " ");
            try {
                Object testObject = createTestObject(creator);
                boolean success = false;
                try {
                    if (m.getReturnType().equals(boolean.class))
                        success = (Boolean) m.invoke(testObject);
                    else {
                        m.invoke(testObject);
                        success = true; // If no assert fails
                    }
                } catch (InvocationTargetException e) {
                    // Actual exception is inside e:
                    print(e.getCause());
                }
                print(success ? "" : "(failed)");
                testsRun++;
                if (!success) {
                    failures++;
                    failedTests.add(testClass.getName() +
                            ": " + m.getName());
                }
                if (cleanup != null)
                    cleanup.invoke(testObject, testObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method m) {
            if (m.getAnnotation(Test.class) == null)
                return;
            if (!(m.getReturnType().equals(boolean.class) ||
                    m.getReturnType().equals(void.class)))
                throw new RuntimeException("@Test method" +
                        " must return boolean or void");
            m.setAccessible(true); // In case it's private, etc.
            add(m);
        }
    }

    private static Method checkForCreatorMethod(Method m) {
        if (m.getAnnotation(TestObjectCreate.class) == null)
            return null;
        if (!m.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate " +
                    "must return instance of Class to be tested");
        /*
        * JAVA 反射机制中，Field的getModifiers()方法返回int类型值表示该字段的修饰符。
        其中，该修饰符是java.lang.reflect.Modifier的静态属性。
        对应表如下：
            PUBLIC: 1
            PRIVATE: 2
            PROTECTED: 4
            STATIC: 8
            FINAL: 16
            SYNCHRONIZED: 32
            VOLATILE: 64
            TRANSIENT: 128
            NATIVE: 256
            INTERFACE: 512
            ABSTRACT: 1024
            STRICT: 2048
        *
        *
        * */
        if ((m.getModifiers() &
                Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCreate " +
                    "must be static.");
        m.setAccessible(true);
        return m;
    }

    private static Method checkForCleanupMethod(Method m) {
        if (m.getAnnotation(TestObjectCleanup.class) == null)
            return null;
        if (!m.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectCleanup " +
                    "must return void");
        if ((m.getModifiers() &
                Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCleanup " +
                    "must be static.");
        if (m.getParameterTypes().length == 0 ||
                m.getParameterTypes()[0] != testClass)
            throw new RuntimeException("@TestObjectCleanup " +
                    "must take an argument of the tested type.");
        m.setAccessible(true);
        return m;
    }

    private static Object createTestObject(Method creator) {
        if (creator != null) {
            try {
                return creator.invoke(testClass);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't run " +
                        "@TestObject (creator) method.");
            }
        } else { // Use the default constructor:
            try {
                return testClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Couldn't create a " +
                        "test object. Try using a @TestObject method.");
            }
        }
    }
} ///:~
