package java编程思想.并发.活动对象;//: concurrency/ActiveObjectDemo.java
// Can only pass constants, immutables, "disconnected
// objects," or other active objects as arguments
// to asynch methods.

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java编程思想.net.mindview.util.Print.print;


/**
 * 当你通读本章之后，可能会发现，Java中的线程机制看起来非常复杂并难以正确使用。另外，它好像还有点达不到预期效果的味道——尽管多个任务可以并行工作，但是你必须花很大的气力去实现防止这些任务彼此互相干涉的技术。如果你曾经编写过汇编语言，那么编写多线程程序就似曾相识：每个细节都很重要，你有责任处理所有事物，并且没有任何编译器检查形式的安全防护措施。是多线程模型自身有问题吗？毕竟，它来自于过程型编程世界，并且几乎没做什么改变。可能还存在着另一种不同的并发模型，它更加适合面向对象编程。有一种可替换的方式被称为活动对象或行动者[26]。之所以称这些对象是“活动的”，是因为每个对象都维护着它自己的工作器线程和消息队列，并且所有对这种对象的请求都将进入队列排队，任何时刻都只能运行其中的一个。因此，有了活动对象，我们就可以串行化消息而不是方法，这意味着不再需要防备一个任务在其循环的中间被中断这种问题了。当你向一个活动对象发送消息时，这条消息会转变为一个任务，该任务会被插入到这个对象的队列中，等待在以后的某个时刻运行。JavaSE5的Future在实现这种模式时将派上用场。下面是一个简单的示例，它有两个方法，可以将方法调用排进队列：
 * <p>
 * 由对Executors.newSingleThreadExecutor()的调用产生的单线程执行器维护着它自己的无界阻塞队列，并且只有一个线程从该队列中取走任务并执行它们直至完成。我们需要在calculateInt()和calculateFloat()中做的就是用submit()提交一个新的Callable对象，以响应对这些方法的调用，这样就可以把方法调用转变为消息，而submit()的方法体包含在匿名内部类的call()方法中。注意，每个活动对象方法的返回值都是一个具有泛型参数的Future，而这个泛型参数就是该方法中实际的返回类型。通过这种方式，方法调用几乎可以立即返回，调用者可以使用Future来发现何时任务完成，并收集实际的返回值。这样可以处理最复杂的情况，但是如果调用没有任何返回值，那么这个过程将被简化。在main()中，创建了一个List<Future<？>>来捕获由发送给活动对象的calculateFloat()和calculateInt()消息返回的Future对象。对于每个Future，都是使用isDone()来从这个列表中抽取的，这种方式使得当Future完成并且其结果被处理过之后，就会从List中移除。注意，使用CopyOnWriteArrayList可以移除为了防止ConcurrentModificationException而复制List的这种需求。为了能够在不经意间就可以防止线程之间的耦合，任何传递给活动对象方法调用的参数都必须是只读的其他活动对象，或者是不连接对象（我的术语），即没有连接任何其他任务的对象（这一点很难强制保障，因为没有任何语言支持它）。有了活动对象：1.每个对象都可以拥有自己的工作器线程。2.每个对象都将维护对它自己的域的全部控制权（这比普通的类要更严苛一些，普通的类只是拥有防护它们的域的选择权）。3.所有在活动对象之间的通信都将以在这些对象之间的消息形式发生。4.活动对象之间的所有消息都要排队。这些结果很吸引人。由于从一个活动对象到另一个活动对象的消息只能被排队时的延迟所阻塞，并且因为这个延迟总是非常短且独立于任何其他对象的，所以发送消息实际上是不可阻塞的（最坏情况也只是很短的延迟）。由于一个活动对象系统只是经由消息来通信，所以两个对象在竞争调用另一个对象上的方法时，是不会被阻塞的，而这意味着不会发生死锁。这是一种巨大的进步。因为在活动对象中的工作器线程在任何时刻只执行一个消息，所以不存在任何资源竞争，而你也不必操心应该如何同步方法。同步仍旧会发生，但是它通过将方法调用排队，使得任何时刻都只能发生一个调用，从而将同步控制在消息级别上发生。遗憾的是，如果没有直接的编译器支持，上面这种编码方式实在是太过于麻烦了。但是，这在活动对象和行动者领域，或者更有趣的被称为基于代理的编程领域，确实产生了进步。代理实际上就是活动对象，但是代理系统还支持跨网络和机器的透明性。如果代理编程最终成为面向对象编程的继任者，我一点也不会觉得惊讶，因为它把对象和相对容易的并发解决方案结合了起来。
 */
public class ActiveObjectDemo {
    private ExecutorService ex = Executors.newSingleThreadExecutor();
    private Random rand = new Random(47);

    // Insert a random delay to produce the effect
    // of a calculation time:
    private void pause(int factor) {
        try {
            TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
        } catch (InterruptedException e) {
            print("sleep() interrupted");
        }
    }

    public Future<Integer> calculateInt(final int x, final int y) {
        return ex.submit(() -> {
            print("starting " + x + " + " + y);
            pause(500);
            return x + y;
        });
    }

    public Future<Float> calculateFloat(final float x, final float y) {
        return ex.submit(new Callable<Float>() {
            public Float call() {
                print("starting " + x + " + " + y);
                pause(2000);
                return x + y;
            }
        });
    }

    public void shutdown() {
        ex.shutdown();
    }

    public static void main(String[] args) {
        ActiveObjectDemo d1 = new ActiveObjectDemo();
        // Prevents ConcurrentModificationException:
        List<Future<?>> results = new CopyOnWriteArrayList<Future<?>>();
        for (float f = 0.0f; f < 1.0f; f += 0.2f)
            results.add(d1.calculateFloat(f, f));
        for (int i = 0; i < 5; i++)
            results.add(d1.calculateInt(i, i));
        print("All asynch calls made");
        while (results.size() > 0) {
            for (Future<?> f : results)
                if (f.isDone()) {
                    try {
                        print(f.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    results.remove(f);
                }
        }
        d1.shutdown();
    }
} /* Output: (85% match)
All asynch calls made
starting 0.0 + 0.0
starting 0.2 + 0.2
0.0
starting 0.4 + 0.4
0.4
starting 0.6 + 0.6
0.8
starting 0.8 + 0.8
1.2
starting 0 + 0
1.6
starting 1 + 1
0
starting 2 + 2
2
starting 3 + 3
4
starting 4 + 4
6
8
*///:~
