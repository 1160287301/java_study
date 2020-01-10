package jvm.jvmError;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : TODO
 * @Author :    yangguang
 * @Date :      2019/11/27
 */
public class OutOfMemoryErrorDemo {

    /**
     *
     * OutOfMemoryError:
     1: Java堆存放对象实例,当需要为对象分配内存时,而堆空间大小已经达到最大值,
     无法为对象实例继续分配空间时,抛出 OutOfMemoryError错误

     2: GC时间过长可能会抛出OutOfMemoryError.也大部分的时间都用在GC上了,并
     且每次回收都只回收一点内存,而清理的一点内存很快又被消耗殆尽,这样就恶性循环,
     不断长时间的GC,就可能抛出GC Overhead limit,但是这点在我的机器上测试不出来,
     可能与jdk版本或gc收集器或Xmx分配内存的大小有关,一直抛出的是java heap sapce

     3: 因为jvm内存依赖于本地物理内存,那么给程序分配超额的物理内存,而堆内存充足,
     那么GC就不会执行回收,DirectByteBuffer对象就不会被回收,如果继续分配
     直接物理内存,那么可能会出现DirectBufferMemoryError

     4: 一个应用程序可以创建的线程数有限,如果创建的线程的数量达到相应平台的上限,
     那么可能会出现 unable to create new native thread 错误

     5:jdk8之后的Metaspace元空间也有可能抛出OOM,Metasapce受限于物理内存,它存储
     着类的元信息,当Metaspace里的类的信息过多时,Metaspace可能会发生OOM.
     这里是可以使用cglb的字节码生成类的技术测试的.
     */

    //测试java heap space错误的时候,把堆的max size 设置的小如:-Xmx30m, 会快一点,不然电脑的内存足够大,没有限制,很难出现OutOfMemory
    public static void heapSpace()
    {
        byte[] bytes = new byte[1024 * 1024 * 2000];
    }

    //测试第2中情况这里可能与jdk版本或垃圾收集器或Xmx分配的内存有关,我的机器始终没有出现GC overhead limit exceeded
    public static void overHeadLimitExceeded()
    {
        int i = 1;
        List<String> list = new ArrayList<>();
        try
        {
            while (true)
            {
                list.add(String.valueOf(i++).intern());
            }
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    //测试第3种情况
    public static void directBufferMemoryError()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 2000);
    }

    //测试第4种情况
    public static void unableCreateNewNativeThread()
    {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        while (true)
        {
            System.out.println("当前线程 : " + atomicInteger.getAndIncrement());
            new Thread(()->{
                try
                {
                    //使当前线程停止,让GC慢点回收此线程,这样使线程停留快点
                    TimeUnit.SECONDS.sleep(5);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }).start();
        }

    }



    public static void main(String[] args) {

//        heapSpace();
//        overHeadLimitExceeded();
//        directBufferMemoryError();
//        unableCreateNewNativeThread();
    }
}
