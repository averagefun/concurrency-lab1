package org.labs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class Programmer implements Callable<Integer> {
    private final int place;
    private final Stol stol;
    private final WaiterPool waiters;

    Programmer(int place, Stol stol, WaiterPool waiters) {
        this.place = place;
        this.stol = stol;
        this.waiters = waiters;
    }

    @Override
    public Integer call() throws InterruptedException {
        int portions = 0;
        while (getDelivery(waiters.bringPortion(place))) {
            stol.eat(place);
            portions++;
        }
        return portions;
    }

    private boolean getDelivery(Future<Boolean> delivery) throws InterruptedException {
        try {
            return delivery.get();
        } catch (ExecutionException e) {
            throw new IllegalStateException("deliver error", e.getCause());
        }
    }
}
