package java编程思想.java_IO.进程控制;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 为了捕获程序执行时产生的标准输出流，你需要调用getInputStream()，这是因为InputStream是我们可以从中读取信息的流。
 * 从程序中产生的结果每次输出一行，因此要使用readLine()来读取。这里这些行只是直接被打印了出来，但是你还可能希望从command()中捕获和返回它们。
 * 该程序的错误被发送到了标准错误流，并且通过调用getErrotStream()得以捕获。如果存在任何错误，它们都会被打印并且会抛出OSExecuteException，
 * 因此调用程序需要处理这个问题。
 */
public class OSExecute {
    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null)
                System.out.println(s);
            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = errors.readLine()) != null) {
                System.out.println(s);
                err = true;
            }
        } catch (IOException e) {
            if (command.startsWith("CMD /C")) {
                command(command + "CMD /C");
            } else {
                throw new RuntimeException(e);
            }
        }
        if (err) {
            throw new OSExecuteException("Errors Executing" + command);
        }
    }
}
