package algorithm;

public class Template {
    public static Comparable[] a = {7, 4, 6, 5, 8, 1, 2, 9, 3};

    public static void sort(){};

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void show() {
        //在单行中打印数组
        for (Comparable comparable : a) {
            System.out.print(comparable + ",");
        }
    }

    public static boolean isSorted(Comparable[] a) {
        //测试数组元素是否有序
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

}
