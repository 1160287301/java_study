package juc.collections;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description : TODO      并发容器安全之List
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
public class ListDemo {
    /**
     * ArrayList:
     * 底层使用Object数组实现,初始化容量为10,动态扩容使用Arrays.copyof增长0.5倍 +１,线程不安全
     *
     * Vector:
     * 线程安全的ArrayList,底层方法使用synchronized保证线程安全,因此,效率低下,不建议使用
     *
     * LinkedList:
     * 底层使用双向链表实现,也是线程不安全的
     *
     *
     * 如何创建线程安全的 List:
     * 1:使用集合工具类 Collections 的 synchronizedList把普通的List转为线程安全的List,但是不建议使用,
     * 因为它底层是返回了Collections内部的一个List实现类,这个实现类的方法仍然使用synchronized,说白了,与Vector相似
     *
     * 2:使用写时复制类 CopyOnWriteArrayList,此类适合读多写少的场合,它的性能比Vector好的多,
     *   往一个容器添加元素的时候,不直接往当前的容器 Object[] 添加,而是先将当前容器 object[] 进行 copy,
     *   复制出一个新的容器 object[] newElements, 然后往新的容器 newElements 里面添加元素,添加完成之后,
     *   再将原容器的指针指向新的容器 setArray(newElements); 这样做的好处是可以对 CopyOnWrite 容器进行并发的读,
     *   而不需要加锁,因为当前容器不会添加任何元素. 所以 CopyOnWrite 容器也是一种读写分离的思想,读和写不同的容器
     *
     */

    //ArrayList线程不安全的demo
    public static void main(String[] args) throws Exception
    {
//        List<Integer> list = new ArrayList<>();
//        List<Integer> list = new Vector<>();
//        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        List<Integer> list = new CopyOnWriteArrayList<>();
        Runnable run = ()->{
            list.add(5);
            System.out.println(list);
        };

        //30个线程对list进行修改和读取操作
        for(int i = 0 ; i < 30; ++i)
        {
            new Thread(run).start();
        }
        /**
         *
         * 大概率可能会抛出 ConcurrentModificationException 异常
         *
         * 此异常是同时对集合进行修改时会抛出的异常
         */



    }
}
