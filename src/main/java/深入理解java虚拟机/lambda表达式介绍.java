package 深入理解java虚拟机;


import javax.swing.*;

public class lambda表达式介绍 extends JFrame {
    private JButton jb;

    public lambda表达式介绍() {
        this.setBounds(200, 200, 400, 200);
        this.setTitle("lambda测试");

        jb = new JButton("click");
        this.add(jb);
        this.setVisible(true);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new lambda表达式介绍();
    }



}
