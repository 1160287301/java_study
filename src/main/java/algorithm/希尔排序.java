package algorithm;

/**
 * https://www.bilibili.com/video/BV1rE411g7rW?from=search&seid=7395911873342988440
 */
public class 希尔排序 extends Template {

    public static void sort() {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            //将数组变为h有序
            for (int i = h; i < N; i++) {
                //将a[i]插入到a[ih],a[i2*h],a[i3*h]...之中
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;

        }
    }


    public static void main(String[] args) {
        sort();
        show();
    }
}
