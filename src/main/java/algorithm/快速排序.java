package algorithm;

/**
 * https://www.bilibili.com/video/av62621532?from=search&seid=2424843519595553649&spm_id_from=333.788.b_636f6d6d656e74.32
 */
public class 快速排序 extends Template {
    public static void sort(Comparable[] a, int lo, int hi) {

        if (hi <= lo) return;
        int j = partition(a, lo, hi);//切分（请见“快速排序的切分”）
        sort(a, lo, j - 1);//将左半部分a[lo..j1]排序
        sort(a, j + 1, hi);//将右半部分a[j+1..hi]排序
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分为a[lo..i1],a[i],a[i+1..hi]
        int i = lo, j = hi + 1;//左右扫描指针
        Comparable v = a[lo];//切分元素
        while (true) {//扫描左右，检查扫描是否结束并交换元素
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);//将v=a[j]放入正确的位置
        return j;//a[lo..j1]<=a[j]<=a[j+1..hi]达成
    }


    public static void main(String[] args) {
        sort(a, 0, a.length - 1);
        show();
    }
}
