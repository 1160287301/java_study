package java编程思想.并发.新类库之中的构件;//: concurrency/ExchangerDemo.java


import java编程思想.net.mindview.util.BasicGenerator;
import java编程思想.net.mindview.util.Generator;

import java.util.List;
import java.util.concurrent.*;

class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
        exchanger = exchg;
        generator = gen;
        this.holder = holder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < ExchangerDemo.size; i++)
                    holder.add(generator.next());
                // Exchange full for empty:
                holder = exchanger.exchange(holder);
                System.out.println("ExchangerProducer.run()");
            }
        } catch (InterruptedException e) {
            // OK to terminate this way.
        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
        exchanger = ex;
        this.holder = holder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                System.out.println("ExchangerConsumer.run()");
                for (T x : holder) {
                    value = x; // Fetch out value
                    holder.remove(x); // OK for CopyOnWriteArrayList
                }
            }
        } catch (InterruptedException e) {
            // OK to terminate this way.
        }
        System.out.println("Final value: " + value);
    }
}

/**
 * Exchanger是在两个任务之间交换对象的栅栏。当这些任务进入栅栏时，它们各自拥有一个对象，当它们离开时，它们都拥有之前由对象持有的对象。Exchanger的典型应用场景是：一个任务在创建对象，这些对象的生产代价很高昂，而另一个任务在消费这些对象。通过这种方式，可以有更多的对象在被创建的同时被消费。为了演练Exchanger类，我们将创建生产者和消费者任务，它们经由泛型和Generator，可以工作于任何类型的对象，然后我们将它们应用于Fat类。ExchangerProducer和ExchangerConsumer使用一个List<T>作为要交换的对象，它们都包含一个用于这个List<T>的Exchanger。当你调用Exchanger.exchanger()方法时，它将阻塞直至对方任务调用它自己的exchange()方法，那时，这两个exchange()方法将全部完成，而List<T>则被互换：
 * 在main()中，创建了用于两个任务的单一的Exchanger，以及两个用于互换的CopyOnWriteArrayList。这个特定的List变体允许在列表被遍历时调用remove()方法，而不会抛出ConcurrentModificationException异常。ExchangeProducer将填充这个List，然后将这个满列表交换为ExchangerConsumer传递给它的空列表。因为有了Exchanger，填充一个列表和消费另一个列表便可以同时发生了。
 */
public class ExchangerDemo {
    static int size = 10;
    static int delay = 5; // Seconds

    public static void main(String[] args) throws Exception {
        if (args.length > 0)
            size = new Integer(args[0]);
        if (args.length > 1)
            delay = new Integer(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<List<Fat>>();
        List<Fat>
                producerList = new CopyOnWriteArrayList<Fat>(),
                consumerList = new CopyOnWriteArrayList<Fat>();
        exec.execute(new ExchangerProducer<Fat>(xc, BasicGenerator.create(Fat.class), producerList));
        exec.execute(new ExchangerConsumer<Fat>(xc, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
} /* Output: (Sample)
Final value: Fat id: 29999
*///:~
