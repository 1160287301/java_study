package 设计模式.适配器模式;

public class 对象适配器模式 {

    /**
     * 通过组合的方式实现适配器模式
     * @param args
     */
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new Adapter(adaptee);
        target.output5v();
    }

    static class Adaptee{
        int output220v(){
            return 220;
        }
    }

    interface Target{
        int output5v();
    }

    static class Adapter implements Target{
        private Adaptee adaptee;

        public Adapter(Adaptee adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public int output5v() {
            int i = this.adaptee.output220v();
            // 走了一系列操作, 将 220v 转换成 5v
            System.out.println(String.format("输入电压: %d v, 输出电压: %d v", i, 5));
            return 5;
        }
    }

}
