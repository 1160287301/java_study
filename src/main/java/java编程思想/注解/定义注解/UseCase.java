package java编程思想.注解.定义注解;//: annotations/UseCase.java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注意，id和description类似方法定义。由于编译器会对id进行类型检查，因此将用例文档的追踪数据库与源代码相关联是可靠的。description元素有一个default值，如果在注解某个方法时没有给出description的值，则该注解的处理器就会使用此元素的默认值。
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    public int id();

    public String description() default "no description";
} ///:~
