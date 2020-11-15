//: enumerated/menu/Course.java
package java编程思想.枚举类型.使用接口组织枚举;


import java编程思想.net.mindview.util.Enums;

/**
 * 然而，当你需要与一大堆类型打交道时，接口就不如enum好用了。例如，如果你想创建一个“枚举的枚举”，那么可以创建一个新的enum，然后用其实例包装Food中的每一个enum类：
 * <p>
 * Bruce Eckel. Java编程思想（第4版） (计算机科学丛书，Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉！) (Chinese Edition) (Kindle 位置 10892-10894). Kindle 版本.
 */
public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);
    private Food[] values;

    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public Food randomSelection() {
        return Enums.random(values);
    }
} ///:~
