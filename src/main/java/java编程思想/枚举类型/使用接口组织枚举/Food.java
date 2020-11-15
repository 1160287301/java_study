package java编程思想.枚举类型.使用接口组织枚举;

//: enumerated/menu/Food.java
// Subcategorization of enums within interfaces.

/**
 * 无法从enum继承子类有时很令人沮丧。这种需求有时源自我们希望扩展原enum中的元素，有时是因为我们希望使用子类将一个enum中的元素进行分组。
 * 在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组，可以达到将枚举元素分类组织的目的。举例来说，假设你想用enum来表示不同类别的食物，
 * 同时还希望每个enum元素仍然保持Food类型。那可以这样实现
 * <p>
 * 在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组，可以达到将枚举元素分类组织的目的。
 * 举例来说，假设你想用enum来表示不同类别的食物，同时还希望每个enum元素仍然保持Food类型。那可以这样实现：
 */
public interface Food {
    enum Appetizer implements Food {
        SALAD, SOUP, SPRING_ROLLS;
    }

    enum MainCourse implements Food {
        LASAGNE, BURRITO, PAD_THAI,
        LENTILS, HUMMOUS, VINDALOO;
    }

    enum Dessert implements Food {
        TIRAMISU, GELATO, BLACK_FOREST_CAKE,
        FRUIT, CREME_CARAMEL;
    }

    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
        LATTE, CAPPUCCINO, TEA, HERB_TEA;
    }
} ///:~

