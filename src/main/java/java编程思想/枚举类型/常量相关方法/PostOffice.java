package java编程思想.枚举类型.常量相关方法;//: enumerated/PostOffice.java
// Modeling a post office.

import java编程思想.net.mindview.util.Enums;

import java.util.Iterator;

import static java编程思想.net.mindview.util.Print.print;

class Mail {
    // The NO's lower the probability of random selection:
    enum GeneralDelivery {
        YES, NO1, NO2, NO3, NO4, NO5
    }

    enum Scannability {UNSCANNABLE, YES1, YES2, YES3, YES4}

    enum Readability {ILLEGIBLE, YES1, YES2, YES3, YES4}

    enum Address {INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6}

    enum ReturnAddress {MISSING, OK1, OK2, OK3, OK4, OK5}

    GeneralDelivery generalDelivery;
    Scannability scannability;
    Readability readability;
    Address address;
    ReturnAddress returnAddress;
    static long counter = 0;
    long id = counter++;

    public String toString() {
        return "Mail " + id;
    }

    public String details() {
        return toString() +
                ", General Delivery: " + generalDelivery +
                ", Address Scanability: " + scannability +
                ", Address Readability: " + readability +
                ", Address Address: " + address +
                ", Return address: " + returnAddress;
    }

    // Generate test Mail:
    public static Mail randomMail() {
        Mail m = new Mail();
        m.generalDelivery = Enums.random(GeneralDelivery.class);
        m.scannability = Enums.random(Scannability.class);
        m.readability = Enums.random(Readability.class);
        m.address = Enums.random(Address.class);
        m.returnAddress = Enums.random(ReturnAddress.class);
        return m;
    }

    public static Iterable<Mail> generator(final int count) {
        return new Iterable<Mail>() {
            int n = count;

            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    public boolean hasNext() {
                        return n-- > 0;
                    }

                    public Mail next() {
                        return randomMail();
                    }

                    public void remove() { // Not implemented
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

/**
 * 在 职责 链（ Chain of Responsibility） 设计 模式 中， 程序员 以多 种 不同 的 方式 来 解决 一个 问题， 然后 将它 们 链接 在一起。 当 一个 请求 到来 时， 它 遍历 这个 链， 直到 链 中的 某个 解决 方案 能够 处理 该 请求。 通过 常量 相关 的 方法， 我们 可以 很容易 地 实现 一个 简单 的 职责 链。 我们 以 一个 邮局 的 模型 为例。 邮局 需要 以 尽可能 通用 的 方式 来处 理 每一 封 邮件， 并且 要不 断 尝试 处理 邮件， 直到 该 邮件 最终 被 确定 为 一封 死信。 其中 的 每一 次 尝试 可以 看作 为 一个 策略（ 也是 一个 设计 模式）， 而 完整 的 处理 方式 列表 就是 一个 职责 链。 我们 先来 描述 一下 邮件。 邮件 的 每个 关键 特征 都可 以用 enum 来 表示。 程序 将 随机 地 生成 Mail 对象， 如果 要 减小 一封 邮件 的 GeneralDelivery 为 YES 的 概率， 那 最简单 的 方法 就是 多 创建 几个 不是 YES 的 enum 实例， 所以 enum 的 定义 看起来 有点 古怪。
 * 我们 看到 Mail 中有 一个 randomMail() 方法， 它 负责 随机 地 创建 用于 测试 的 邮件。 而 generator() 方法 生成 一个 Iterable 对象， 该 对 象在 你 调用 next() 方法 时， 在 其内 部 使用 randomMail() 来 创建 Mail 对象。 这样 的 结构 使 程序员 可以 通过 调用 Mail. generator() 方法， 很容易 地 构造 出 一个 foreach 循环：
 */
public class PostOffice {
    enum MailHandler {
        GENERAL_DELIVERY {
            boolean handle(Mail m) {
                switch (m.generalDelivery) {
                    case YES:
                        print("Using general delivery for " + m);
                        return true;
                    default:
                        return false;
                }
            }
        },
        MACHINE_SCAN {
            boolean handle(Mail m) {
                switch (m.scannability) {
                    case UNSCANNABLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                print("Delivering " + m + " automatically");
                                return true;
                        }
                }
            }
        },
        VISUAL_INSPECTION {
            boolean handle(Mail m) {
                switch (m.readability) {
                    case ILLEGIBLE:
                        return false;
                    default:
                        switch (m.address) {
                            case INCORRECT:
                                return false;
                            default:
                                print("Delivering " + m + " normally");
                                return true;
                        }
                }
            }
        },
        RETURN_TO_SENDER {
            boolean handle(Mail m) {
                switch (m.returnAddress) {
                    case MISSING:
                        return false;
                    default:
                        print("Returning " + m + " to sender");
                        return true;
                }
            }
        };

        abstract boolean handle(Mail m);
    }

    static void handle(Mail m) {
        for (MailHandler handler : MailHandler.values())
            if (handler.handle(m))
                return;
        print(m + " is a dead letter");
    }

    public static void main(String[] args) {
        for (Mail mail : Mail.generator(10)) {
            print(mail.details());
            handle(mail);
            print("*****");
        }
    }
} /* Output:
Mail 0, General Delivery: NO2, Address Scanability: UNSCANNABLE, Address Readability: YES3, Address Address: OK1, Return address: OK1
Delivering Mail 0 normally
*****
Mail 1, General Delivery: NO5, Address Scanability: YES3, Address Readability: ILLEGIBLE, Address Address: OK5, Return address: OK1
Delivering Mail 1 automatically
*****
Mail 2, General Delivery: YES, Address Scanability: YES3, Address Readability: YES1, Address Address: OK1, Return address: OK5
Using general delivery for Mail 2
*****
Mail 3, General Delivery: NO4, Address Scanability: YES3, Address Readability: YES1, Address Address: INCORRECT, Return address: OK4
Returning Mail 3 to sender
*****
Mail 4, General Delivery: NO4, Address Scanability: UNSCANNABLE, Address Readability: YES1, Address Address: INCORRECT, Return address: OK2
Returning Mail 4 to sender
*****
Mail 5, General Delivery: NO3, Address Scanability: YES1, Address Readability: ILLEGIBLE, Address Address: OK4, Return address: OK2
Delivering Mail 5 automatically
*****
Mail 6, General Delivery: YES, Address Scanability: YES4, Address Readability: ILLEGIBLE, Address Address: OK4, Return address: OK4
Using general delivery for Mail 6
*****
Mail 7, General Delivery: YES, Address Scanability: YES3, Address Readability: YES4, Address Address: OK2, Return address: MISSING
Using general delivery for Mail 7
*****
Mail 8, General Delivery: NO3, Address Scanability: YES1, Address Readability: YES3, Address Address: INCORRECT, Return address: MISSING
Mail 8 is a dead letter
*****
Mail 9, General Delivery: NO1, Address Scanability: UNSCANNABLE, Address Readability: YES2, Address Address: OK1, Return address: OK4
Delivering Mail 9 normally
*****
*///:~
