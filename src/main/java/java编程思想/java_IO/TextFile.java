package java编程思想.java_IO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * TextFile类在本书前面的示例中就已经被用来简化对文件的读写操作了。它包含的static方法可以像简单字符串那样读写文本文件，并且我们可以创建一个TextFile对象，
 * 它用一个ArrayList来保存文件的若干行（如此，当我们操纵文件内容时，就可以使用ArrayList的所有功能）
 */
public class TextFile extends ArrayList<String> {
    public static String read(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static void write(String fileName, String text) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TextFile(String fileName, String splitter) throws Exception {
        super(Arrays.asList(read(fileName).split(splitter)));
        if (get(0).equals("")) remove(0);
    }

    public TextFile(String fileName) throws Exception {
        this(fileName, "\n");
    }

    public void write(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for (String item : this)
                    out.println(item);
            } finally {
                out.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        String file = read("/Users/h/PycharmProjects/java_study/src/main/java/java编程思想/java_IO/TextFile.java");
        write("test.txt", file);
        TextFile text = new TextFile("test.txt");
        text.write("test2.txt");
        TreeSet<String> words = new TreeSet<>(new TextFile("/Users/h/PycharmProjects/java_study/src/main/java/java编程思想/java_IO/TextFile.java", "\\W+"));
        System.out.println(words.headSet("a"));
    }

}
