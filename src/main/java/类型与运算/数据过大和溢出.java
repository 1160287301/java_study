package 类型与运算;

public class 数据过大和溢出 {
    public static void main(String[] args) {
        //int类型最大的值
        int intMax = 2147483647;
        intMax = intMax + 1;
        System.out.println(intMax);  //变成最小值 -2147483648
        /*
        * 原因
        * 2147483647的二进制吗
        * 01111111 11111111 11111111 11111111
        *+00000000 00000000 00000000 00000001
        * 10000000 00000000 00000000 00000000
        *
        * */
        int intMin = -2147483648;
        intMin = intMin - 1;
        System.out.println(intMin);  //变成最大值 2147483647

    }
}
