//: net/mindview/atunit/AtUnitRemover.java
// Displays @Unit annotations in compiled class files. If
// first argument is "-r", @Unit annotations are removed.
// {Args: ..}
// {Requires: javassist.bytecode.ClassFile;
// You must install the Javassist library from
// http://sourceforge.net/projects/jboss/ }
package java编程思想.注解.基于注解的单元测试.移除测试代码;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import java编程思想.net.mindview.util.BinaryFile;
import java编程思想.net.mindview.util.ProcessFiles;
import java编程思想.注解.基于注解的单元测试.实现unit.ClassNameFinder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static java编程思想.net.mindview.util.Print.print;

/**
 * 对 许多 项目 而言， 在 发布 的 代码 中 是否 保留 测试 代码 并 没什么 区别（ 特别 是在 如果 你将 所有 的 测试 方法 都 声明 为 private 的 情况下， 如果 你 喜欢 就可以 这么 做）， 但是 在 有的 情况下， 我们 确实 希望 将 测试 代码 清 除掉， 精简 发布 的 程序， 或者 就是 不 希望 测试 代码 暴露 给 客户。 与 自己 动手 删除 测试 代码 相比， 这 需要 更 复杂 的 字节 码 工程。 不过 开源 的 Javassist 工具 类 库[ 12] 将 字节 码 工程 带入 了 一个 可行 的 领域。 下面 的 程序 接受 一个- r 标志 作为 其 第一个 参数； 如果 你 提供 了 该 标志， 那么 它 就会 删除 所有 的@ Test 注解， 如果 你 没有 提供 该 标记， 那 它 则 只会 打 印出@ Test 注解。 这里 同样 使用 ProcessFiles 来 遍历 你 选择 的 文件 和 目录：
 * ClassPool 是一 种 全景， 它 记录 了 你 正在 修改 的 系统 中的 所 有的 类， 并能 够 保证 所有 类 在 修改 后的 一致性。 你 必须 从 ClassPool 中 取得 每个 CtClass， 这与 使用 类 加载 器 和 Class. forName() 向 JVM 加载 类 的 方式 类似。 CtClass 包含 的 是 类 对象 的 字节 码， 你 可以 通过 它 取得 类 有关 的 信息， 并且 操作 类 中的 代码。 在这里， 我们 调用 getDeclaredMethods()（ 与 Java 的 反射 机制 一样）， 然后 从 每个 CtMethod 对象 中 取得 一个 MethodInfo 对象。 通过 该 对象， 我们 察看 其中 的 注解 信息。 如果 一个 方法 带有 net. mindview. atunit 包 中的 注解， 就 将 该 方法 删除 掉。 如 果类 被 修改 过了， 就用 新的 类 覆盖 原始 的 类 文件。 在 撰写 本书 时， Javassist 刚刚 加入 了“ 删除” 功能[ 13]， 同时 我们 发现， 删除@ TestProperty 域 比 删除 方法 复杂 得 多。 因为， 有些 静态 初始化 的 操作 可能 会 引用 这些 域， 所以 你 不能 简单 地 将其 删除。 因此 AtUnitRemover 的 当前 版本 只 删除@ Unit 方法。 不过， 你 应该 查看 一下 Javassist 网 站的 更新， 因为 删除 域 的 功能 以后 可能 也 将 实现。 与此同时， 对于 AtUnitExternalText. java 演示 的 外部 测试 方法， 可以 直接 删除 测试 代码 生成 的 类 文件， 从而 到达 删除 所有 测试 的 目的。
 */
public class AtUnitRemover
        implements ProcessFiles.Strategy {
    private static boolean remove = false;

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("-r")) {
            remove = true;
            String[] nargs = new String[args.length - 1];
            System.arraycopy(args, 1, nargs, 0, nargs.length);
            args = nargs;
        }
        new ProcessFiles(
                new AtUnitRemover(), "class").start(args);
    }

    public void process(File cFile) {
        boolean modified = false;
        try {
            String cName = ClassNameFinder.thisClass(
                    BinaryFile.read(cFile));
            if (!cName.contains("."))
                return; // Ignore unpackaged classes
            ClassPool cPool = ClassPool.getDefault();
            CtClass ctClass = cPool.get(cName);
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                MethodInfo mi = method.getMethodInfo();
                AnnotationsAttribute attr = (AnnotationsAttribute)
                        mi.getAttribute(AnnotationsAttribute.visibleTag);
                if (attr == null) continue;
                for (Annotation ann : attr.getAnnotations()) {
                    if (ann.getTypeName()
                            .startsWith("net.mindview.atunit")) {
                        print(ctClass.getName() + " Method: "
                                + mi.getName() + " " + ann);
                        if (remove) {
                            ctClass.removeMethod(method);
                            modified = true;
                        }
                    }
                }
            }
            // Fields are not removed in this version (see text).
            if (modified)
                ctClass.toBytecode(new DataOutputStream(
                        new FileOutputStream(cFile)));
            ctClass.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
} ///:~
