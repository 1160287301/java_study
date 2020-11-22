//: annotations/ExtractInterface.java
// APT-based annotation processing.
package java编程思想.注解.基本语法.使用apt处理注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过使用AnnotationProcessorFactory，apt能够为每一个它发现的注解生成一个正确的注解处理器。当你使用apt的时候，必须指明一个工厂类，或者指明能找到apt所需的工厂类的路径。否则，apt会踏上一个神秘的探索之旅，详细的信息可以在Sun文档的“开发一个注解处理器”一节中找到。使用apt生成注解处理器时，我们无法利用Java的反射机制，因为我们操作的是源代码，而不是编译后的类[4]。使用mirrorAPI[5]能够解决这个问题，它使我们能够在未经编译的源代码中查看方法，域以及类型。下面是一个自定义的注解，使用它可以把一个类中的public方法提取出来，构造成一个新的接口
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    public String value();
} ///:~
