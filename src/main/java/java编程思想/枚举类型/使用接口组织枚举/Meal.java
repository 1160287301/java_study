//: enumerated/menu/Meal.java
package java编程思想.枚举类型.使用接口组织枚举;

/**
 * 在上面的程序中，每一个Course的实例都将其对应的Class对象作为构造器的参数。通过getEnumConstants()方法，可以从该Class对象中取得某个Food子类的所有enum实例。这些实例在randomSelection()中被用到。因此，通过从每一个Course实例中随机地选择一个Food，我们便能够生成一份菜单：
 * <p>
 * Bruce Eckel. Java编程思想（第4版） (计算机科学丛书，Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉！) (Chinese Edition) (Kindle 位置 10896-10899). Kindle 版本.
 */
public class Meal {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Course course : Course.values()) {
                Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("---");
        }
    }
} /* Output:
SPRING_ROLLS
VINDALOO
FRUIT
DECAF_COFFEE
---
SOUP
VINDALOO
FRUIT
TEA
---
SALAD
BURRITO
FRUIT
TEA
---
SALAD
BURRITO
CREME_CARAMEL
LATTE
---
SOUP
BURRITO
TIRAMISU
ESPRESSO
---
*///:~
