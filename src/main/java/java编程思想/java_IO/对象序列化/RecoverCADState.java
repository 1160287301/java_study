package java编程思想.java_IO.对象序列化;//: io/RecoverCADState.java
// Restoring the state of the pretend CAD system.
// {RunFirst: StoreCADState}

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * 可以 看到， xPos、 yPos 以及 dim 的 值 都被 成功 地 保存 和 恢复 了， 但是 对 static 信息 的 读取 却 出现 了 问题。
 * 所有 读 回 的 颜色 应该 都是“ 3”， 但是 真实 情况 却 并非如此。 Circle 的 值 为 1（ 定义 为 RED），
 * 而 Square 的 值 为 0（ 记住， 它们 是在 构造 器 中 被 初始化 的）。 看上去 似乎 static 数据 根本 没有 被 序列 化！
 * 确实 如此—— 尽管 Class 类 是 Serializable 的， 但它 却不 能按 我们 所 期望 的 方式 运行。 所以 假如 想 序列 化 static 值，
 * 必须 自己 动手 去 实现。
 */
public class RecoverCADState {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("CADState.out"));
        // Read in the same order they were written:
        List<Class<? extends Shape>> shapeTypes =
                (List<Class<? extends Shape>>) in.readObject();
        Line.deserializeStaticState(in);
        List<Shape> shapes = (List<Shape>) in.readObject();
        System.out.println(shapes);
    }
} /* Output:
[class Circlecolor[1] xPos[58] yPos[55] dim[93]
, class Squarecolor[0] xPos[61] yPos[61] dim[29]
, class Linecolor[3] xPos[68] yPos[0] dim[22]
, class Circlecolor[1] xPos[7] yPos[88] dim[28]
, class Squarecolor[0] xPos[51] yPos[89] dim[9]
, class Linecolor[3] xPos[78] yPos[98] dim[61]
, class Circlecolor[1] xPos[20] yPos[58] dim[16]
, class Squarecolor[0] xPos[40] yPos[11] dim[22]
, class Linecolor[3] xPos[4] yPos[83] dim[6]
, class Circlecolor[1] xPos[75] yPos[10] dim[42]
]
*///:~
