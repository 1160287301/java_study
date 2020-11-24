//: net/mindview/atunit/ClassNameFinder.java
package java编程思想.注解.基于注解的单元测试.实现unit;

import java编程思想.net.mindview.util.BinaryFile;
import java编程思想.net.mindview.util.Directory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java编程思想.net.mindview.util.Print.print;

/**
 * AtUnit. java 必须 要 解决 一个 问题， 就是 当它 找到 类 文件 时， 实际 引 用的 类 名（ 含有 包） 并非 一定 就是 类 文件 的 名字。 为了 从中 解读 信息， 我们 必须 分析 该类 文件， 这 很重 要， 因为 这种 名字 不一致 的 情况 确实 可能 出现[ 10]。 所以， 当 找到 一个. class 文件 时， 第一 件事 情 就是 打开 该 文件， 读取 其二 进制 数据， 然后 将其 交给 ClassNameFinder. thisClass()。 从这 里 开始， 我们将 进入“ 字节 码 工程” 的 领域， 因为 我们 实际上 是在 分析 一个 类 文件 的 内容：
 * 虽然 无法 在这里 介绍 其中 所有 的 细节， 但 每个 类 文件 都 必须 遵循 一定 的 格式， 而我 已经 尽量 用 有意义 的 域 名字 来 表示 这些 从 ByteArrayInputStream 中 提出 取来 的 数据 片断。 通过 施加 在 输 入流 上 的 读 操作， 你能 看出 每个 信息 片 的 大小。 例如， 每个 类 文件 的 头 32 个 bit 总是 一个“ 神秘 的 数字” hex0xcafebabe[ 11]， 而 接下 来的 两个 short 值 是 版本 信息。 常量 池 包含 了 程序 中的 常量， 所以 这是 一个 可 变的 值。 接下 来的 short 告诉 我们 这个 常量 池 有 多大， 然后 我们 为 其 创建 一个 尺寸 合适 的 数组。 常量 池 中的 每一个 元素， 其 长度 可能 是一 个 固 定的 值， 也可能 是 可 变的 值， 因此 我们 必须 检查 每一个 常量 起始 的 标记， 然后 才能 知道 该 怎么 做， 这就 是 switch 语句 中的 工作。 我们 并不 打算 精确 地 分析 类 中的 所有 数据， 仅仅是 从 文件 的 起始 一步 一步 地走， 直到 取得 我们 所需 的 信息， 因此 你会 发现， 在 这个 过程中 我们 丢弃 了 大量 的 数据。 关于 类 的 信息 都 保存 在 classNameTable 和 offsetTable 中。 在读 完了 常量 池 之后， 就 找到了 this_ class 信息， 这是 offsetTable 中的 一个 坐标， 通过 它 能够 找到 一个 进入 classNameTable 的 坐标， 然后 就可以 得到 我们 所需 的 类 的 名字 了。
 */
public class ClassNameFinder {
    public static String thisClass(byte[] classBytes) {
        Map<Integer, Integer> offsetTable =
                new HashMap<Integer, Integer>();
        Map<Integer, String> classNameTable =
                new HashMap<Integer, String>();
        try {
            DataInputStream data = new DataInputStream(
                    new ByteArrayInputStream(classBytes));
            int magic = data.readInt();  // 0xcafebabe
            int minorVersion = data.readShort();
            int majorVersion = data.readShort();
            int constant_pool_count = data.readShort();
            int[] constant_pool = new int[constant_pool_count];
            for (int i = 1; i < constant_pool_count; i++) {
                int tag = data.read();
                int tableSize;
                switch (tag) {
                    case 1: // UTF
                        int length = data.readShort();
                        char[] bytes = new char[length];
                        for (int k = 0; k < bytes.length; k++)
                            bytes[k] = (char) data.read();
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5: // LONG
                    case 6: // DOUBLE
                        data.readLong(); // discard 8 bytes
                        i++; // Special skip necessary
                        break;
                    case 7: // CLASS
                        int offset = data.readShort();
                        offsetTable.put(i, offset);
                        break;
                    case 8: // STRING
                        data.readShort(); // discard 2 bytes
                        break;
                    case 3:  // INTEGER
                    case 4:  // FLOAT
                    case 9:  // FIELD_REF
                    case 10: // METHOD_REF
                    case 11: // INTERFACE_METHOD_REF
                    case 12: // NAME_AND_TYPE
                        data.readInt(); // discard 4 bytes;
                        break;
                    default:
                        throw new RuntimeException("Bad tag " + tag);
                }
            }
            short access_flags = data.readShort();
            int this_class = data.readShort();
            int super_class = data.readShort();
            return classNameTable.get(
                    offsetTable.get(this_class)).replace('/', '.');
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Demonstration:
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            for (String arg : args)
                print(thisClass(BinaryFile.read(new File(arg))));
        } else
            // Walk the entire tree:
            for (File klass : Directory.walk(".", ".*\\.class"))
                print(thisClass(BinaryFile.read(klass)));
    }
} ///:~
