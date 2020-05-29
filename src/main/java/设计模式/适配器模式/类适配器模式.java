package 设计模式.适配器模式;

public class 类适配器模式 {
    /**
     * 使用继承实现适配器模式
     * 该模式有个缺点, 不符合最少字段原则, 明明只需要输出 5v 的方法就行了, 但还是可以调用输出 220v 的方法
     * @param args
     */
    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        adapter.output5v();
    }

    static class Adaptee{
        int output220v(){
            return 220;
        }
    }

    interface Target{
        int output5v();
    }

    static class Adapter extends Adaptee implements Target {
        @Override
        public int output5v() {
            int i = this.output220v();
            // 走了一系列操作, 将 220v 转换成 5v
            System.out.println(String.format("输入电压: %d v, 输出电压: %d v", i, 5));
            return 5;
        }
    }
}
