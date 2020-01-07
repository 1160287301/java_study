package 线程与并发;

import java.io.IOException;

public class 多进程 {
    public static void main(String[] args) throws IOException {
        // 在Java中如何开启一个进程:运行笔记本程序
        // 方式1: 使用Runtime类的exec方法
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("notepad");
        // 方式2: ProcessBuilder的start方法
        ProcessBuilder pb = new ProcessBuilder("notepad");
        pb.start();
    }
}
