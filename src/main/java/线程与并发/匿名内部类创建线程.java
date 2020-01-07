package 线程与并发;

public class 匿名内部类创建线程 {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println("玩游戏" + i);
            if (i == 0) {
                // 方式一: 实现Runnable接口
//                new Thread(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                for (int j = 0; j < 50; j++) {
//                                    System.out.println("听英语" + j);
//                                }
//                            }
//                        }
//                ).start();
                //方式二: 继承Thread类
                new Thread(){
                    @Override
                    public void run() {
                        for (int j = 0; j < 50; j++) {
                            System.out.println("听英语" + j);
                        }
                    }
                }.start();
            }
        }
    }
}
