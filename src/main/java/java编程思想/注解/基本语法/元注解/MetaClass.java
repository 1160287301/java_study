package java编程思想.注解.基本语法.元注解;

public class MetaClass {
    /*
     *1、元注解
      元注解的作用就是负责注解其他注解。Java5.0定义了4个标准的meta-annotation类型，它们被用来提供对其它 annotation类型作说明。Java5.0定义的元注解有以下几种：
        @Target
        @Retention
        @Documented
        @Inherited

    1.1 @Target
　　用于描述注解的范围，即注解在哪用。它说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）等。取值类型（ElementType）有以下几种：
　　CONSTRUCTOR:用于描述构造器
　　FIELD:用于描述域即类成员变量
　　LOCAL_VARIABLE:用于描述局部变量
　　METHOD:用于描述方法
　　PACKAGE:用于描述包
　　PARAMETER:用于描述参数
　　TYPE:用于描述类、接口(包括注解类型) 或enum声明
　　TYPE_PARAMETER:1.8版本开始，描述类、接口或enum参数的声明
　　TYPE_USE:1.8版本开始，描述一种类、接口或enum的使用声明

   1.2 @Retention

　　用于描述注解的生命周期，表示需要在什么级别保存该注解，即保留的时间长短。取值类型（RetentionPolicy）有以下几种：

　　SOURCE:在源文件中有效（即源文件保留）
　　CLASS:在class文件中有效（即class保留）
　　RUNTIME:在运行时有效（即运行时保留）
*
   1.3 @Documented
　　用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。它是一个标记注解，没有成员。
*
   1.4 @Inherited
　　用于表示某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。
     *
     *
     *
     * */
}
