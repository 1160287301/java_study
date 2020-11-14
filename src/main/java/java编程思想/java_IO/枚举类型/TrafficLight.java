package java编程思想.java_IO.枚举类型;//: enumerated/TrafficLight.java
// Enums in switch statements.

// Define an enum type:
enum Signal {
    GREEN, YELLOW, RED,
}

/**
 * 在 switch 中 使用 enum， 是 enum 提供 的 一项 非常 便利 的 功能。
 * 一般来说， 在 switch 中 只能 使用 整 数值， 而 枚举 实例 天生 就 具备 整 数值 的 次序，
 * 并且 可以 通过 ordinal() 方法 取得 其 次序（ 显然 编译器 帮我 们 做了 类似 的 工作），
 * 因此 我们 可以 在 switch 语句 中 使用 enum。 虽然 一般 情况下 我们 必须 使用 enum 类型 来 修饰 一个 enum 实例，
 * 但是 在 case 语句 中 却不 必 如此。 下面 的 例子 使用 enum 构造 了 一个 小型 状态 机：
 */
public class TrafficLight {
    Signal color = Signal.RED;

    public void change() {
        switch (color) {
            // Note that you don't have to say Signal.RED
            // in the case statement:
            case RED:
                color = Signal.GREEN;
                break;
            case GREEN:
                color = Signal.YELLOW;
                break;
            case YELLOW:
                color = Signal.RED;
                break;
        }
    }

    public String toString() {
        return "The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        for (int i = 0; i < 7; i++) {
            System.out.println(t);
            t.change();
        }
    }
} /* Output:
The traffic light is RED
The traffic light is GREEN
The traffic light is YELLOW
The traffic light is RED
The traffic light is GREEN
The traffic light is YELLOW
The traffic light is RED
*///:~
