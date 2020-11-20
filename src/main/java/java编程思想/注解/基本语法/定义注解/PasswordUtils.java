package java编程思想.注解.基本语法.定义注解;//: annotations/PasswordUtils.java

import java.util.List;

/**
 * 注解的元素在使用时表现为名值对的形式，并需要置于@UseCase声明之后的括号内。在encryptPassword()方法的注解中，并没有给出description元素的值，因此，在UseCase的注解处理器分析处理这个类时会使用该元素的默认值。
 */
public class PasswordUtils {
    @UseCase(id = 47, description =
            "Passwords must contain at least one numeric")
    public boolean validatePassword(String password) {
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49, description =
            "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(
            List<String> prevPasswords, String password) {
        return !prevPasswords.contains(password);
    }
} ///:~
