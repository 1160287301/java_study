package 线程与并发;

public class 多线程 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println("玩游戏" + i);
            if (i == 0) {
                //方式1: 继承Thread类
//                MusicThread mt = new MusicThread();
//                mt.start();
                //方式2: 实现Runnable接口
                MusicThread mt1 = new MusicThread();
                Thread t = new Thread(mt1);
                t.start();
            }
        }
    }

}

//播放音乐的类
class MusicThread extends Thread {
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("播放音乐" + i);
        }
    }
}
//播放音乐的类
class MusicRunnableImpl implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("播放音乐" + i);
        }
    }
}