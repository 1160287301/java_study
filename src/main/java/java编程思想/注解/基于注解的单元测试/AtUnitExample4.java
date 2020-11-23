//: annotations/AtUnitExample4.java
package java编程思想.注解.基于注解的单元测试;

import java编程思想.java_IO.进程控制.OSExecute;
import java编程思想.net.mindview.atunit.Test;
import java编程思想.net.mindview.atunit.TestObjectCreate;
import java编程思想.net.mindview.atunit.TestProperty;

import java.util.*;

import static java编程思想.net.mindview.util.Print.print;

/**
 * 有的时候，我们需要向单元测试中添加一些额外的域。这时可以使用@TestProperty注解，由它注解的域表示只在单元测试中使用（因此，在我们将产品发布给客户之前，他们应该被删除掉）。在下面的例子中，一个String通过String.split()方法被拆散了，从其中读取一个值，这个值将被用来生成测试对象：
 *
 * @TestProperty也可以用来标记那些只在测试中使用的方法，而他们本身又不是测试方法。注意，这个程序依赖于测试执行的顺序，这可不是一个好的实践。
 */
public class AtUnitExample4 {
    static String theory = "All brontosauruses " +
            "are thin at one end, much MUCH thicker in the " +
            "middle, and then thin again at the far end.";
    private String word;
    private Random rand = new Random(); // Time-based seed

    public AtUnitExample4(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String scrambleWord() {
        List<Character> chars = new ArrayList<Character>();
        for (Character c : word.toCharArray())
            chars.add(c);
        Collections.shuffle(chars, rand);
        StringBuilder result = new StringBuilder();
        for (char ch : chars)
            result.append(ch);
        return result.toString();
    }

    @TestProperty
    static List<String> input =
            Arrays.asList(theory.split(" "));
    @TestProperty
    static Iterator<String> words = input.iterator();

    @TestObjectCreate
    static AtUnitExample4 create() {
        if (words.hasNext())
            return new AtUnitExample4(words.next());
        else
            return null;
    }

    @Test
    boolean words() {
        print("'" + getWord() + "'");
        return getWord().equals("are");
    }

    @Test
    boolean scramble1() {
        // Change to a specific seed to get verifiable results:
        rand = new Random(47);
        print("'" + getWord() + "'");
        String scrambled = scrambleWord();
        print(scrambled);
        return scrambled.equals("lAl");
    }

    @Test
    boolean scramble2() {
        rand = new Random(74);
        print("'" + getWord() + "'");
        String scrambled = scrambleWord();
        print(scrambled);
        return scrambled.equals("tsaeborornussu");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("starting");
        OSExecute.command(
                "java net.mindview.atunit.AtUnit AtUnitExample4");
    }
} /* Output:
starting
annotations.AtUnitExample4
  . scramble1 'All'
lAl

  . scramble2 'brontosauruses'
tsaeborornussu

  . words 'are'

OK (3 tests)
*///:~
