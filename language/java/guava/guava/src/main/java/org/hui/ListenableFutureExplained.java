package org.hui;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ListenableFutureExplained {
    public static void main(String[] args) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
//        service.submit(new Callable<Expl>() {
//        })
    }
}
