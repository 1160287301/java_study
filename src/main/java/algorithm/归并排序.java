package algorithm;

/**
 * 分而治之思想
 * https://www.bilibili.com/video/BV1et411N7Ac?from=search&seid=17336327101598161138
 */
public class 归并排序 extends Template {
    private static Comparable[] aux; //归并所需的辅助数组

    public static void sort() {
        aux = new Comparable[a.length];//一次性分配空间sort(a,0,a.length1);
        sort(a, 0, a.length - 1);

    }

    public static void sort(Comparable[] a, int lo, int hi) {
        //将数组a[lo..hi]排序
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid); //将左半边排序
        sort(a, mid + 1, hi); //将右半边排序
        merge(a, lo, mid, hi); //归并结果（代码见“原地归并的抽象方法”）}
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        //将a[lo..mid]和a[mid+1..hi]归并
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            //将a[lo..hi]复制到aux[lo..hi]
            aux[k] = a[k];

        for (int k = lo; k <= hi; k++) {
            //归并回到a[lo..hi]
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }


    public static void main(String[] args) {
        sort();
        show();
    }

}
