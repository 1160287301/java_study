package java编程思想.java_IO.preferences;

//: io/PreferencesDemo.java

import java.util.prefs.Preferences;


/**
 * Preferences API 与 对象 序列 化 相比， 前者 与 对象 持久性 更 密切， 因为 它可 以自 动 存储 和 读取 信息。
 * 不过， 它 只能 用于 小的、 受限 的 数据 集合—— 我们 只能 存储 基本 类型 和 字符串，
 * 并且 每个 字符串 的 存储 长度 不能 超过 8K（ 不是 很小， 但 我们 也 并不 想用 它来 创建 任何 重要的 东西）。
 * 顾名思义， Preferences API 用于 存储 和 读取 用户 的 偏好（ preferences） 以及 程序 配置 项 的 设置。
 * Preferences 是 一个 键- 值 集合（ 类似 映射）， 存储 在 一个 节点 层次 结构 中。
 * 尽管 节点 层次 结构 可用 来 创建 更为 复杂 的 结构， 但 通常 是 创建 以 你的 类 名 命名 的 单一 节点， 然后 将 信息 存储 于 其中。
 * 下面 是一 个 简单 的 例子：
 * <p>
 * 在 我们 运行 PreferencesDemo. java 时， 会 发现 每次 运行 程序 时， UsageCount 的 值 都会 增加 1， 但是 数据 存储 到 哪里 了 呢？
 * 在 程序 第一次 运行 之后， 并没有 出现 任何 本地 文件。
 * Preferences API 利用 合适 的 系统 资源 完成 了 这个 任务， 并且 这些 资源 会 随 操作系统 不同 而 不同。
 * 例如 在 Windows 里， 就 使用 注册 表（ 因为 它 已经 有“ 键值 对” 这样 的 节点 对 层次 结构 了）。
 * 但是 最重要的 一点 是， 它 已经 神奇 般 地 为我 们 存储 了 信息， 所以 我们 不必 担心 不同 的 操作系统 是 怎么 运作 的。
 */
public class PreferencesDemo {
    public static void main(String[] args) throws Exception {
        Preferences prefs = Preferences
                .userNodeForPackage(PreferencesDemo.class);
//        prefs.put("Location", "Oz");
//        prefs.put("Footwear", "Ruby Slippers");
//        prefs.putInt("Companions", 4);
//        prefs.putBoolean("Are there witches?", true);
//        int usageCount = prefs.getInt("UsageCount", 0);
//        usageCount++;
//        prefs.putInt("UsageCount", usageCount);
        prefs = Preferences
                .userNodeForPackage(PreferencesDemo.class);
        for (String key : prefs.keys())
            System.out.println(key + ": " + prefs.get(key, null));
        // You must always provide a default value:
        System.out.println("How many companions does Dorothy have? " +
                prefs.getInt("Companions", 0));
    }
} /* Output: (Sample)
Location: Oz
Footwear: Ruby Slippers
Companions: 4
Are there witches?: true
UsageCount: 53
How many companions does Dorothy have? 4
*///:~

