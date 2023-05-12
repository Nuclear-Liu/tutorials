package org.hui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoinPoolTest {

    @Test
    void testDefaultPool1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        list.stream().parallel().forEach((i) -> {
            while (true) {
                System.out.println(i);
                try {
                    TimeUnit.MICROSECONDS.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Test
    void testDefaultPool2() {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        for (int i = 0; i < 50; i++) {
            int finalI = i;
            future.whenCompleteAsync((p, throwable) -> {
                while (true) {
                    System.out.println(finalI);
                    try {
                        TimeUnit.MICROSECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        while (true) ;

    }

    @Test
    void testCustomPool2() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(50);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        for (int i = 0; i < 50; i++) {
            int finalI = i;
            future.whenCompleteAsync((p, throwable) -> {
                while (true) {
                    System.out.println(finalI);
                    try {
                        TimeUnit.MICROSECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, forkJoinPool);
        }

        while (true) ;

    }

    @Test
    void testCustomPool3() {
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("custom-fork_join_pool:" + worker.getPoolIndex());
            return worker;
        };

        ForkJoinPool forkJoinPool = new ForkJoinPool(4, factory, null, true);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4,
                16,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                // new ThreadPoolExecutor.AbortPolicy()
                // new ThreadPoolExecutor.CallerRunsPolicy()
                // new ThreadPoolExecutor.DiscardOldestPolicy()
                new ThreadPoolExecutor.DiscardPolicy()
        );

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        for (int i = 0; i < 50; i++) {
            int finalI = i;
            future.whenCompleteAsync((p, throwable) -> {
                while (true) {
                    System.out.println(finalI);
                    try {
                        TimeUnit.MICROSECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, threadPoolExecutor);
        }

        while (true) ;
    }
}
