package 数组;

import java.util.Arrays;

public class 参数传递机制 {
    public static void main(String[] args) {
        int x = 10;//基本类型->值传递
        System.out.println(x + "--" + System.identityHashCode(x)); //10--1163157884
        chang(x);

        System.out.println('*'*20);
        System.out.println("*******");

        int[] arr = new int[]{99, 100};//引用类型->指针传递
        System.out.println(Arrays.toString(arr) + "--" + System.identityHashCode(arr));
        chang(arr);
    }

    private static void chang(int[] arr) {
        System.out.println(Arrays.toString(arr));
        arr[1] = 99;
        System.out.println(Arrays.toString(arr) + "--" + System.identityHashCode(arr));//20--460141958
    }

    private static void chang(int x) {
        System.out.println(x);
        x = 20;
        System.out.println(x + "--" + System.identityHashCode(x));//20--460141958
    }


}
