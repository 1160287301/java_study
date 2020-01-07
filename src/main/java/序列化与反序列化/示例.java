package 序列化与反序列化;

import java.io.*;

public class 示例 {
    static String dir = "C:/myApp/PycharmProjects/java_study/src/序列化与反序列化/test";
    static File file = new File(dir);

    static class Account implements Serializable {
        //        private static final long serialVersionUID = 1L;
        Account(int age) {
            this.age = age;
        }

        Account(String name) {
            this.name = name;
        }

        void setUserName(String name) {
            this.name = name;
        }

        String getUserName() {
            return this.name;
        }

        private int age;
        private long birthday;
        private String name;
        private String name_;
    }

    static void write() throws Exception {
        FileOutputStream fos = new FileOutputStream(dir);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Account account = new Account(5);
        oos.writeObject(account);
        oos.flush();

    }

    static void read() throws Exception {
        // 读取Account的内容
        FileInputStream fis = new FileInputStream(dir);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Account account2 = (Account) ois.readObject();
        System.out.println(account2.age);
    }

    static void rule() throws Exception {
        // 将account对象保存两次，第二次保存时修改其用户名
        Account account = new Account("Freeman");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(account);
        System.out.println("fileSize=" + file.length());
        account.setUserName("Tom");
        oos.writeObject(account);
        System.out.println("fileSize=" + file.length());

        // 读取两次保存的account对象
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Account account2 = (Account) ois.readObject();
        Account account3 = (Account) ois.readObject();
        System.out.println("account2.name=" + account2.getUserName() + "\n  account3.name=" + account3.getUserName() + "\naccount2==account3 -> " + account2.equals(account3));
        //所以在对同一个对象进行多次序列化的时候，最好通过clone一个新的对象再进行序列化

    }

    public static void main(String[] args) {
        try {
//            write();
//            read();
            rule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
