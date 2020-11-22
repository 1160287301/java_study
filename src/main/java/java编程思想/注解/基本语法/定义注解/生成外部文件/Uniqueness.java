//: annotations/database/Uniqueness.java
// Sample of nested annotations
package java编程思想.注解.基本语法.定义注解.生成外部文件;

/**
 * 。如果要令嵌入的@Constraints注解中的unique()元素为true，并以此作为constraints()元素的默认值，则需要如下定义该元素：
 */
public @interface Uniqueness {
    Constraints constraints()
            default @Constraints(unique = true);
} ///:~
