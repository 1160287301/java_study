package juc.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步回调方法
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        // 没有返回值的异步回调
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread().getName() + "没有返回!"));
//        voidCompletableFuture.get();

        // 有返回值的异步回调
        // ****t: ForkJoinPool.commonPool-worker-1有返回值!!****u: null
        // ****t: null****u: java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            int a = 1/0;
            return Thread.currentThread().getName() + "有返回值!!";
        });
        stringCompletableFuture.whenComplete((t, u) -> System.out.println("****t: " + t + "****u: " + u)).exceptionally(f -> ("出错了: exception是: " + f.getMessage()));

    }
}
