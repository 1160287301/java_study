//: net/mindview/atunit/TestProperty.java
// The @Unit @TestProperty tag.
package java编程思想.注解.基于注解的单元测试.实现unit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Both fields and methods may be tagged as properties:
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestProperty {
} ///:~
