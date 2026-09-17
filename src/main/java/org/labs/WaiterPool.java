package org.labs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicIntegerArray;

final class WaiterPool implements AutoCloseable {
    private static final long DELIVERY_TIME_MILLIS = 10;

    private final Sklad sklad;
    private final ExecutorService waiters;
    private final PriorityBlockingQueue<PortionRequest> requests = new PriorityBlockingQueue<>();
    private final AtomicIntegerArray servedPortions;

    WaiterPool(int waiterCount, int programmerCount, Sklad sklad) {
        this.sklad = sklad;
        servedPortions = new AtomicIntegerArray(programmerCount);

        waiters = Executors.newFixedThreadPool(waiterCount);
        for (int i = 0; i < waiterCount; i++) {
            waiters.submit(this::handleRequests);
        }
    }

    Future<Boolean> bringPortion(int programmer) {
        FutureTask<Boolean> delivery = new FutureTask<>(() -> {
            if (!sklad.takePortion()) {
                return false;
            }

            servedPortions.incrementAndGet(programmer);
            Thread.sleep(DELIVERY_TIME_MILLIS);
            return true;
        });

        requests.add(new PortionRequest(servedPortions.get(programmer), delivery));
        return delivery;
    }

    private void handleRequests() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                requests.take().delivery().run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        waiters.shutdownNow();
    }

    private record PortionRequest(int servedPortions, FutureTask<Boolean> delivery)
            implements Comparable<PortionRequest> {

        @Override
        public int compareTo(PortionRequest other) {
            return Integer.compare(servedPortions, other.servedPortions);
        }
    }
}
