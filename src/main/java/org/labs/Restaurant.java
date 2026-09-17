package org.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public final class Restaurant {
    private final Config config;

    public Restaurant(Config config) {
        this.config = config;
    }

    public Result run() throws InterruptedException {
        Sklad sklad = new Sklad(config.portions());
        WaiterPool waiters = new WaiterPool(config.waiters(), config.programmers(), sklad);
        Stol stol = new Stol(config.programmers());
        AtomicInteger threadNumber = new AtomicInteger(1);
        ExecutorService executor = Executors.newFixedThreadPool(
                config.programmers(),
                task -> new Thread(task, "programmer-" + threadNumber.getAndIncrement())
        );

        try {
            List<Future<Integer>> futures = new ArrayList<>(config.programmers());
            for (int i = 0; i < config.programmers(); i++) {
                futures.add(executor.submit(new Programmer(i, stol, waiters)));
            }

            List<Integer> portions = new ArrayList<>(config.programmers());
            for (Future<Integer> future : futures) {
                portions.add(getResult(future));
            }
            return new Result(portions, sklad.remaining());
        } finally {
            executor.shutdownNow();
            waiters.close();
        }
    }

    private int getResult(Future<Integer> future) throws InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw new IllegalStateException(cause);
        }
    }
}
