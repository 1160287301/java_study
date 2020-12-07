package java编程思想.并发.共享受限资源;//: concurrency/IntGenerator.java

public abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int next();

    // Allow this to be canceled:
    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
} ///:~
