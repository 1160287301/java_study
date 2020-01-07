package 深入理解java虚拟机;

import java.util.ArrayList;
import java.util.List;

public class 内存溢出 {
    public static void main(String[] args) {
        List<Demo> demoList = new ArrayList<>();
        while (true){
            demoList.add(new Demo());
        }
        /*怎么定位内存溢出
        1. vm argument 添加
        -XX:+HeapDumpOnOutOfMemoryError   表示当JVM发生OOM时，自动生成DUMP文件。
        dump出来的文件可以用MemoryAnalyzer 查看

        */
    }
}
