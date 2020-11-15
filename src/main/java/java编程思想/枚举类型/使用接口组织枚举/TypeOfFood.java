//: enumerated/menu/TypeOfFood.java
package java编程思想.枚举类型.使用接口组织枚举;
//import static .*;

/**
 * 对于enum而言，实现接口是使其子类化的唯一办法，所以嵌入在Food中的每个enum都实现了Food接口。现在，在下面的程序中，我们可以说“所有东西都是某种类型的Food”：
 * 如果enum类型实现了Food接口，那么我们就可以将其实例向上转型为Food，所以上例中的所有东西都是Food。
 */
public class TypeOfFood {
    public static void main(String[] args) {
        Food food = Food.Appetizer.SALAD;
        food = Food.MainCourse.LASAGNE;
        food = Food.Dessert.GELATO;
        food = Food.Coffee.CAPPUCCINO;
    }
} ///:~
