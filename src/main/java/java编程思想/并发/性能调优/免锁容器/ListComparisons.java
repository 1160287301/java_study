package java编程思想.并发.性能调优.免锁容器;//: concurrency/ListComparisons.java
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe List performance.


import java编程思想.net.mindview.util.CountingIntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class ListTest extends Tester<List<Integer>> {
    ListTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    class Reader extends TestTask {
        long result = 0;

        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    result += testContainer.get(index);
        }

        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    testContainer.set(index, writeData[index]);
        }

        void putResults() {
            writeTime += duration;
        }
    }

    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++)
            exec.execute(new Reader());
        for (int i = 0; i < nWriters; i++)
            exec.execute(new Writer());
    }
}

class SynchronizedArrayListTest extends ListTest {
    List<Integer> containerInitializer() {
        return Collections.synchronizedList(
                new ArrayList<Integer>(
                        new CountingIntegerList(containerSize)));
    }

    SynchronizedArrayListTest(int nReaders, int nWriters) {
        super("Synched ArrayList", nReaders, nWriters);
    }
}

class CopyOnWriteArrayListTest extends ListTest {
    List<Integer> containerInitializer() {
        return new CopyOnWriteArrayList<Integer>(
                new CountingIntegerList(containerSize));
    }

    CopyOnWriteArrayListTest(int nReaders, int nWriters) {
        super("CopyOnWriteArrayList", nReaders, nWriters);
    }
}

/**
 * 在ListTest中，Reader和Writer类执行针对List<Integer>的具体动作。在Reader.putResults()中，duration被存储来起来，result也是一样，这样可以防止这些计算被优化掉。startReadersAndWriters()被定义为创建和执行具体的Readers和Writers。一旦创建了ListTest，它就必须被进一步继承，以覆盖containerInitializer()，从而可以创建和初始化具体的测试容器。在main()中，你可以看到各种测试变体，它们具有不同数量的读取者和写入者。由于存在对Tester.initMain（args）的调用，所以你可以使用命令行参数来改变测试变量。默认行是为每个测试运行10次，这有助于稳定输出，而输出是可以变化的，因为存在着诸如hotspot优化和垃圾回收这样的JVM活动[25]。你看到的样本输出已经被编辑为只显示每个测试的最后一个迭代。从输出中可以看到，synchronizedArrayList无论读取者和写入者的数量是多少，都具有大致相同的性能——读取者与其他读取者竞争锁的方式与写入者相同。但是，CopyOnWriteArrayList在没有写入者时，速度会快许多，并且在有5个写入者时，速度仍旧明显地快。看起来你应该尽量使用CopyOnWriteArrayList，对列表写入的影响并没有超过短期同步整个列表的影响。当然，你必须在你的具体应用中尝试这两种不同的方式，以了解到底哪个更好一些。再次注意，这还不是测试结果绝对不变的良好的基准测试，你的结果几乎肯定是不同的。这里的目标只是让你对两种不同类型的容器的相对行为有个概念上的认识。因为CopyOnWriteArraySet使用了CopyOnWriteArrayList，所以它的行为与此类似，在这里就不需要另外设计一个单独的测试了。
 */
public class ListComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);
        new SynchronizedArrayListTest(10, 0);
        new SynchronizedArrayListTest(9, 1);
        new SynchronizedArrayListTest(5, 5);
        new CopyOnWriteArrayListTest(10, 0);
        new CopyOnWriteArrayListTest(9, 1);
        new CopyOnWriteArrayListTest(5, 5);
        Tester.exec.shutdown();
    }
} /* Output: (Sample)
Type                             Read time     Write time
Synched ArrayList 10r 0w      232158294700              0
Synched ArrayList 9r 1w       198947618203    24918613399
readTime + writeTime =        223866231602
Synched ArrayList 5r 5w       117367305062   132176613508
readTime + writeTime =        249543918570
CopyOnWriteArrayList 10r 0w      758386889              0
CopyOnWriteArrayList 9r 1w       741305671      136145237
readTime + writeTime =           877450908
CopyOnWriteArrayList 5r 5w       212763075    67967464300
readTime + writeTime =         68180227375
*///:~
