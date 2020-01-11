package juc.lock.other;

import java.util.concurrent.TimeUnit;

class Phone{
    public synchronized void sendEmail() throws Exception{
        System.out.println("*****sendEmail");
    }

    public synchronized void sendEmailSleep() throws Exception{
        TimeUnit.SECONDS.sleep(4);
        System.out.println("*****sendEmailSleep");
    }

    public static synchronized void sendEmailSleepStatic() throws Exception{
        TimeUnit.SECONDS.sleep(4);
        System.out.println("*****sendEmailSleepStatic");
    }

    public static synchronized void sendSMSStatic() throws Exception{
        System.out.println("*****sendSMSStatic");
    }

    public synchronized void sendSMS() throws Exception{
        System.out.println("*****sendSMS");
    }

    public void sayHello() throws Exception {
        System.out.println("*****sayHello");
    }
}


/**
 * 8 种锁的情况
 * 1 标准访问 sendEmail -> sendSMS,先打印邮件还是短信
 *  先邮件,后短信; 因为一个对象里面如果有多个 synchronized 方法,某一个时刻内, 只要有一个县城去调用其中的以一个synchronized方法了,其他线程都只能等待
 *  换句话说,某一个时刻内,只能有唯一一个线程去访问这些synchronized方法,锁的是当前对象 this,被锁定后,其他的线程都不能进入到当前对象的其他synchronized方法
 *
 * 2 邮件方法暂停 4 秒 sendEmailSleep -> sendSMS, 先打印邮件还是短信
 *  先短信,后邮件; 因为一个对象里面如果有多个 synchronized 方法,某一个时刻内, 只要有一个县城去调用其中的以一个synchronized方法了,其他线程都只能等待
 *  换句话说,某一个时刻内,只能有唯一一个线程去访问这些synchronized方法,锁的是当前对象 this,被锁定后,其他的线程都不能进入到当前对象的其他synchronized方法
 *
 * 3 新增普通的 sayHello 方法 sendEmailSleep -> sayHello, 先打印邮件还是hello
 *  先 hello,后邮件; 因为加个普通方法和同步锁无关,不用竞争同一个资源类
 *
 * 4 两部手机, sendEmailSleep -> sendSMS,先打印邮件还是短信
 *  先短信,后邮件; 因为换成两个对象后,不是同一把锁了,不用竞争
 *
 * 5 两个静态同步方法,同一部手机, sendEmailSleepStatic -> sendSMSStatic, 先打印邮件还是短信
 *  先邮件,后短信; 因为一个对象里面如果有多个 synchronized 方法,某一个时刻内, 只要有一个县城去调用其中的以一个synchronized方法了,其他线程都只能等待
 *  换句话说,某一个时刻内,只能有唯一一个线程去访问这些synchronized方法,锁的是当前对象 this,被锁定后,其他的线程都不能进入到当前对象的其他synchronized方法
 *
 * 6 两个静态同步方法,两部手机, sendEmailSleepStatic -> sendSMSStatic, 先打印邮件还是短信
 *  先邮件,后短信; 因为锁是当前类的 Class 对象
 *  synchronized实现同步的基础: java 中的每一个对象都可以作为锁,具体表现为以下 3 种形式
 *      1.对于普通同步方法,锁是当前实例对象this
 *      2.对于同步方法块,锁是synchronized括号里配置的对象
 *      3.对于静态同步方法,锁是当前类的 Class 对象
 *
 * 7 一个静态同步方法,一个普通同步方法,同一部手机, sendEmailSleepStatic -> sendSMS, 先打印邮件还是短信
 *  先短信,后邮件; 锁的是不同对象,一个 class 对象,一个实例对象
 *
 * 8 一个静态同步方法,一个普通同步方法,两部部手机, sendEmailSleepStatic -> sendSMS, 先打印邮件还是短信
 *  先短信,后邮件;锁的是两个不同的实例对象
 *
 * 总结
 *  当一个线程试图访问同步代码块时,它首相必须得到锁,退出或抛出异常时释放锁.也就是说如果一个实例对象的非静态同步方法获取锁喉,
 *  该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁,可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁,
 *  所以不用等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁
 *      所有的静态同步方法用的也是同一把锁 - 类对象本身
 *  这两把锁是连个不同的对象,所以静态同步方法与非静态同步方法之间是不会有竞争关系的
 *  但是一旦一个静态同步方法获取锁后,其他的静态同步方法都必须等待该方法释放锁后才能获取锁,而不管是同一个实例对象的静态同步方法之间,还是不同的实例对象的静态同步方法之间
 *  只要他们是同一个类的实例对象!
 */
public class eightLock {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            try {
//                phone.sendEmail();
//                phone.sendEmailSleep();
                phone.sendEmailSleepStatic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();
        new Thread(() -> {
            try {
//                phone.sendSMS();
//                phone.sayHello();
//                phone2.sendSMS();
//                phone.sendSMSStatic();
                phone2.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
