//: annotations/database/Member.java
package java编程思想.注解.定义注解.生成外部文件;

/**
 * 类的注解@DBTable给定了值MEMBER，它将会用来作为表的名字。Bean的属性firstName和lastName，都被注解为@SQLString类型，并且其元素值分别为30和50。这些注解有两个有趣的地方：第一，他们都使用了嵌入的@Constraints注解的默认值；第二，它们都使用了快捷方式。何谓快捷方式呢，如果程序员的注解中定义了名为value的元素，并且在应用该注解的时候，如果该元素是唯一需要赋值的一个元素，那么此时无需使用名值对的这种语法，而只需在括号内给出value元素所需的值即可。这可以应用于任何合法类型的元素。当然了，这也限制了程序员必须将此元素命名为value，不过在上面的例子中，这不但使语义更清晰，而且这样的注解语句也更易于理解
 * 默认值的语法虽然很灵巧，但它很快就变得复杂起来。以handle域的注解为例，这是一个@SQLString注解，同时该域将成为表的主键，因此在嵌入的@Constraints注解中，必须对primaryKey元素进行设定。这时事情就变得麻烦了。现在，你不得不使用很长的名值对形式，重新写出元素名和@interface的名字。与此同时，由于有特殊命名的value元素已经不再是唯一需要赋值的元素了，所以你也不能再使用快捷方式为其赋值了。如你所见，最终的结果算不上清晰易懂。
 * <p>
 * 变通之道
 * <p>
 * 可以使用多种不同的方式来定义自己的注解，以实现上例中的功能。例如，你可以使用一个单一的注解类@TableColumn，它带有一个enum元素，该枚举类定义了STRING、INTEGER以及FLOAT等枚举实例。这就消除了每个SQL类型都需要一个@interface定义的负担，不过也使得以额外的信息修饰SQL类型的需求变得不可能，而这些额外的信息，例如长度或精度等，可能是非常有必要的需求。我们也可以使用String元素来描述实际的SQL类型，比如VARCHAR（30）或INTEGER。这使得程序员可以修饰SQL类型。但是，它同时也将Java类型到SQL类型的映射绑在了一起，这可不是一个好的设计。我们可不希望更换数据库导致代码必须修改并重新编译。如果我们只需告诉注解处理器，我们正在使用的是什么“口味”的SQL，然后由处理器为我们处理SQL类型的细节，那将是一个优雅的设计。第三种可行的方案是同时使用两个注解类型来注解一个域，@Constraints和相应的SQL类型（例如@SQLIntege）。这种方式可能会使代码有点乱，不过编译器允许程序员对一个目标同时使用多个注解。注意，使用多个注解的时候，同一个注解不能重复使用。
 */
@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30)
    String firstName;
    @SQLString(50)
    String lastName;
    @SQLInteger
    Integer age;
    @SQLString(value = 30,
            constraints = @Constraints(primaryKey = true))
    String handle;
    static int memberCount;

    public String getHandle() {
        return handle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toString() {
        return handle;
    }

    public Integer getAge() {
        return age;
    }
} ///:~
