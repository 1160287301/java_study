package java编程思想.枚举类型;//: enumerated/RandomTest.java

import java编程思想.net.mindview.util.Enums;

enum Activity {
    SITTING, LYING, STANDING, HOPPING,
    RUNNING, DODGING, JUMPING, FALLING, FLYING
}

/**
 * 古怪的语法<TextendsEnum<T>>表示T是一个enum实例。而将Class<T>作为参数的话，我们就可以利用Class对象得到enum实例的数组了。
 * 重载后的random()方法只需使用T[]作为参数，因为它并不会调用Enum上的任何操作，它只需从数组中随机选择一个元素即可。这样，最终的返回类型正是enum的类型
 */
public class RandomTest {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++)
            System.out.print(Enums.random(Activity.class) + " ");
    }
} /* Output:
STANDING FLYING RUNNING STANDING RUNNING STANDING LYING DODGING SITTING RUNNING HOPPING HOPPING HOPPING RUNNING STANDING LYING FALLING RUNNING FLYING LYING
*///:~
