package java编程思想.java_IO.进程控制;

/**
 * 你经常会需要在Java内部执行其他操作系统的程序，并且要控制这些程序的输入和输出。Java类库提供了执行这些操作的类。
 * 一项常见的任务是运行程序，并将产生的输出发送到控制台。本节包含了一个可以简化这项任务的实用工具。
 * 在使用这个实用工具时，可能会产生两种类型的错误：普通的导致异常的错误——对这些错误我们只需重新抛出一个运行时异常，以及从进程自身的执行过程中产生的错误，我们希望用单独的异常来报告这些错误：
 */
public class OSExecuteException extends RuntimeException {
    public OSExecuteException(String why) {
        super(why);
    }
}
