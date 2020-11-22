package java编程思想.注解.基本语法.编写注解处理器;//: annotations/UseCaseTracker.java

import java编程思想.注解.基本语法.定义注解.PasswordUtils;
import java编程思想.注解.基本语法.定义注解.UseCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这个程序用到了两个反射的方法：getDeclaredMethods()和getAnnotation()，它们都属于AnnotatedElement接口（Class、Method与Field等类都实现了该接口）。getAnnoation()方法返回指定类型的注解对象，在这里就是UseCase。如果被注解的方法上没有该类型的注解，则返回null值。然后我们通过调用id()和description()方法从返回的UseCase对象中提取元素的值。其中，encriptPassword()方法在注解的时候没有指定description的值，因此处理器在处理它对应的注解时，通过description()方法取得的是默认值nodescription。
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case:" + uc.id() +
                        " " + uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }
        for (int i : useCases) {
            System.out.println("Warning: Missing use case-" + i);
        }
    }

    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 47, 48, 49, 50);
        trackUseCases(useCases, PasswordUtils.class);
    }
} /* Output:
Found Use Case:47 Passwords must contain at least one numeric
Found Use Case:48 no description
Found Use Case:49 New passwords can't equal previously used ones
Warning: Missing use case-50
*///:~
