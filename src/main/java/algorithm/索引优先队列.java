package algorithm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 它将多个已经有序的输入流归并成一个有序的输出流。
 * 无论输入流有多长，都可以将其全部读入并排序（并不是一次性读入内存的，我们将看到任何时刻队列中只存在每个输入流的一个元素而已
 */
public class 索引优先队列 {
    public static void merge(InputStream[] streams) throws IOException {
        int N = streams.length;
        // 为每个输入流关联一个整数
        IndexMinPQ<String> pq = new IndexMinPQ<>(N);
        // 从每个流中读取一个字符，因为每个流都已经有序，所以其中必然有最小元素
        for (int i = 0; i < N; i++) {
            int ch;
            if ((ch = streams[i].read()) != -1) {
                pq.insert(i, String.valueOf((char) ch));
            }
        }

        while (!pq.isEmpty()) {
            // 不断选出最小元素打印
            System.out.print(pq.min());
            // 关联这个整数的对象被删除，从关联该整数的剩余流中再读取一个字符，并加入到索引优先队列中
            int i = pq.delMin();
            int ch;
            if ((ch = streams[i].read()) != -1) {
                pq.insert(i, String.valueOf((char) ch));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        InputStream stream1 = new ByteArrayInputStream("ACHYZ".getBytes());
        InputStream stream2 = new ByteArrayInputStream("BCRXY".getBytes());
        InputStream stream3 = new ByteArrayInputStream("ADPQS".getBytes());
        InputStream[] streams = {stream1, stream2, stream3};
        try {
            merge(streams); // AABCCDHPQRSXYYZ
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


/**
 * 可以删除最小元素的IndexMinPQ
 */
class IndexMinPQ<Key extends Comparable<Key>> {
    private int N;
    private int[] pq; // 索引二叉堆，按照惯例从1开始
    private int[] qp; // 逆序，满足qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;

    public IndexMinPQ(int maxN) {
        // 可存放范围为[0, maxN]
        keys = (Key[]) new Comparable[maxN + 1];
        // 索引二叉堆,存放范围为[1, maxN]
        pq = new int[maxN + 1];
        // 可存放范围为[0, maxN]
        qp = new int[maxN + 1];
        // 刚开始没有关联任何整数，都设置为-1
        Arrays.fill(qp, -1);
    }

    // 针对是pq中的索引i、j，但是实际引用的是keys中对应的元素
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public void insert(int k, Key key) {
        if (!contains(k)) {
            N++;
            pq[N] = k;
            qp[k] = N;
            keys[k] = key;
            swim(N);
        }
    }

    // 给整数k重新关联一个对象
    public void replace(int k, Key key) {
        keys[k] = key;
        // 由于和k关联的新key可能大于原来的key（此时需要下沉），也有可能小于原来的key（此时需要上浮），为了简化代码，既上浮又下沉，就囊括了这两种可能。
        swim(qp[k]);
        sink(qp[k]);
    }

    // 返回最小元素
    public Key min() {
        return keys[pq[1]];
    }

    // 最小元素的关联整数
    public int minIndex() {
        return pq[1];
    }

    public int delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列已经为空，不能执行删除！");
        }
        int indexOfMin = pq[1];
        // 堆顶和最后一个元素交换
        swap(1, N--);
        sink(1);
        // 最后一个元素置为空
        keys[indexOfMin] = null;
        // 同时关联整数pq[N]在pq中的的索引设置为-1，表示还没有对象与该整数关联
        qp[indexOfMin] = -1;

        return indexOfMin;
    }

    public void delete(int k) {
        if (!contains(k)) {
            throw new NoSuchElementException("没有元素与" + k + "关联！");
        }
        // index为整数k在pq中的位置
        int index = qp[k];

        swap(index, N--);
        // 这里一定要先上浮下沉后再将元素置空，因为swim方法没有N的限制，在没有交换元素的情况下，即删除的就是pq中最后一个元素，如果先置空, 会在greater方法中引发空指针
        // 而sink方法有N的限制，先置空后置空都没有影响，2k <= N会限制它进入循环，避免了空指针
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }

    public Key keyOf(int k) {
        if (contains(k)) {
            return keys[k];
        }
        // 没有与k关联就返回null
        return null;
    }

    private void swap(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        // 还要更新qp
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        // k = 1说明当前元素浮到了根结点，它没有父结点可以比较，也不能上浮了，所以k <= 1时候推出循环
        while (k > 1 && greater(k / 2, k)) {
            swap(k / 2, k);
            // 上浮后，成为父结点，所以下标变成原父结点
            k = k / 2;
        }
    }

    private void sink(int k) {
        // 父结点的位置k最大值为 N/2,若k有左子结点无右子结点，那么2k = N；若两个子结点都有，那么2k + 1 = N
        // 有可能位置k只有左子结点，依然要比较，用2k + 1 <= N这个的条件不会执行比较，所以用2k <= N条件
        while (2 * k <= N) {
            int j = 2 * k;
            // 可以取j = N -1,greater(N -1, N);由于下标从1开始，所以pq[N]是有元素的
            if (j < N && greater(j, j + 1)) {
                // 右子结点比左子结点大 ，取右子结点的下标
                j++;
            }
            // 左子结点或者右子结点和父结点比较
            // 如果pq[k] >= pq[j]，即父结点大于等于较大子结点时，停止下沉
            if (!greater(k, j)) {
                break;
            }
            // 否则交换
            swap(k, j);
            // 下沉后，下标变成与之交换的元素下标
            k = j;
        }
    }

    public static void main(String[] args) {
        IndexMinPQ<String> indexMinPQ = new IndexMinPQ<>(20);
        indexMinPQ.insert(5, "E");
        indexMinPQ.insert(7, "G");
        indexMinPQ.insert(2, "B");
        indexMinPQ.insert(1, "A");
        if (indexMinPQ.contains(7)) {
            indexMinPQ.replace(7, "Z");
        }

        System.out.println(indexMinPQ.min()); // A
        System.out.println(indexMinPQ.delMin()); // 1
        System.out.println(indexMinPQ.delMin());// 2
        System.out.println(indexMinPQ.minIndex()); // 5
        System.out.println(indexMinPQ.keyOf(7)); // Z
        indexMinPQ.delete(7);

    }

}

/**
 * 可以删除最大元素的IndexMaxPQ
 */
class IndexMaxPQ<Key extends Comparable<Key>> {

    private int N;
    private int[] pq; // 索引二叉堆，按照惯例从1开始
    private int[] qp; // 逆序，满足qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;

    public IndexMaxPQ(int maxN) {
        // 可存放范围为[0, maxN]
        keys = (Key[]) new Comparable[maxN + 1];
        // 索引二叉堆,存放范围为[1, maxN]
        pq = new int[maxN + 1];
        // 可存放范围为[0, maxN]
        qp = new int[maxN + 1];
        // 刚开始没有关联任何整数，都设置为-1
        Arrays.fill(qp, -1);
    }

    // 针对是pq中的索引i、j，但是实际引用的是keys中对应的元素
    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public void insert(int k, Key key) {
        if (!contains(k)) {
            N++;
            pq[N] = k;
            qp[k] = N;
            keys[k] = key;
            swim(N);
        }
    }

    // 给整数k重新关联一个对象
    public void replace(int k, Key key) {
        keys[k] = key;
        // 由于和k关联的新key可能大于原来的key（此时需要下沉），也有可能小于原来的key（此时需要上浮），为了简化代码，既上浮又下沉，就囊括了这两种可能。
        swim(qp[k]);
        sink(qp[k]);
    }

    // 返回最小元素
    public Key max() {
        return keys[pq[1]];
    }

    // 最小元素的关联整数
    public int maxIndex() {
        return pq[1];
    }

    public int delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("队列已经为空，不能执行删除！");
        }
        int indexOfMax = pq[1];
        // 堆顶和最后一个元素交换
        swap(1, N--);
        sink(1);
        // 最后一个元素置为空
        keys[indexOfMax] = null;
        // 同时关联整数pq[N]在pq中的的索引设置为-1，表示还没有对象与该整数关联
        qp[indexOfMax] = -1;

        return indexOfMax;
    }

    public void delete(int k) {
        if (!contains(k)) {
            throw new NoSuchElementException("没有元素与" + k + "关联！");
        }
        // index为整数k在pq中的位置
        int index = qp[k];

        swap(index, N--);
        // 这里一定要先上浮下沉后再将元素置空，因为swim方法没有N的限制，在没有交换元素的情况下，即删除的就是pq中最后一个元素，如果先置空, 会在greater方法中引发空指针
        // 而sink方法有N的限制，先置空后置空都没有影响，2k <= N会限制它进入循环，避免了空指针
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }

    public Key keyOf(int k) {
        if (contains(k)) {
            return keys[k];
        }
        // 没有与k关联就返回null
        return null;
    }

    private void swap(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        // 还要更新qp
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        // k = 1说明当前元素浮到了根结点，它没有父结点可以比较，也不能上浮了，所以k <= 1时候推出循环
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            // 上浮后，成为父结点，所以下标变成原父结点
            k = k / 2;
        }
    }

    private void sink(int k) {
        // 父结点的位置k最大值为 N/2,若k有左子结点无右子结点，那么2k = N；若两个子结点都有，那么2k + 1 = N
        // 有可能位置k只有左子结点，依然要比较，用2k + 1 <= N这个的条件不会执行比较，所以用2k <= N条件
        while (2 * k <= N) {
            int j = 2 * k;
            // 可以取j = N -1,greater(N -1, N);由于下标从1开始，所以pq[N]是有元素的
            if (j < N && less(j, j + 1)) {
                // 右子结点比左子结点大 ，取右子结点的下标
                j++;
            }
            // 左子结点或者右子结点和父结点比较
            // 如果pq[k] >= pq[j]，即父结点大于等于较大子结点时，停止下沉
            if (!less(k, j)) {
                break;
            }
            // 否则交换
            swap(k, j);
            // 下沉后，下标变成与之交换的元素下标
            k = j;
        }
    }
}

