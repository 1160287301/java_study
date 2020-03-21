package algorithm;

public class 插入排序 extends Template {
    public static void sort() {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            //将a[i]插入到a[i1]、a[i2]、a[i3]...之中
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }


    public static void main(String[] args) {
        sort();
        show();
    }
}
