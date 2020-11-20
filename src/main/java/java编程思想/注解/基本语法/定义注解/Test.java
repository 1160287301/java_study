package java编程思想.注解.基本语法.定义注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 除了@符号以外，@Test的定义很像一个空的接口。定义注解时，会需要一些元注解（metaannotation），如@Target和@Retention。@Target用来定义你的注解将应用于什么地方（例如是一个方法或者一个域）。@Rectetion用来定义该注解在哪一个级别可用，在源代码中（SOURCE）、类文件中（CLASS）或者运行时（RUNTIME）。
 * 在注解中，一般都会包含一些元素以表示某些值。当分析处理注解时，程序或工具可以利用这些值。注解的元素看起来就像接口的方法，唯一的区别是你可以为其指定默认值。没有元素的注解称为标记注解（markerannotation），例如上例中的@Test。
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
}
