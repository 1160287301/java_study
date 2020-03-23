package algorithm;

/**
 * 类比 斗地主给手里的牌, 插牌排序
 * https://www.bilibili.com/video/BV1St411w7X6?from=search&seid=12927083942325101325
 */
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
