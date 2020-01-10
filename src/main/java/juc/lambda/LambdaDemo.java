package juc.lambda;

/**
 * 函数式编程 规定所使用的函数式接口里面只有一个方法 多了无法识别
 * default 和 static 方法可以
 */

@FunctionalInterface // 底层优化自动会加上这个注解
interface Foo {
//    public void foo();
    public int foo(int a, int b);
    public default int mul(int x, int y) {
        return x * y;
    }
    public static int devide(int x, int y) {
        return x / y;
    }
}

public class LambdaDemo {
    public static void main(String[] args) {
        // 常规操作
        Foo foo = new Foo() {
            @Override
            public int foo(int a, int b) {
                System.out.println("常规操作");
                return a + b;
            }

            @Override
            public int mul(int x, int y) {
                return 0;
            }
        };
        System.out.println(foo.foo(1, 2));
        // lambda 操作
        Foo foo1 = (int a, int b) -> {
            System.out.println("lambda 操作");
            return a + b;
        };
        System.out.println(foo1.foo(3, 4));
        System.out.println(foo1.mul(3, 5));
        System.out.println(Foo.devide(8, 2));
    }
}


