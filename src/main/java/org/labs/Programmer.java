package org.labs;

import java.util.concurrent.Callable;

final class Programmer implements Callable<Integer> {
    private final int place;
    private final int target;
    private final Stol stol;
    private final WaiterPool waiters;

    Programmer(int place, int target, Stol stol, WaiterPool waiters) {
        this.place = place;
        this.target = target;
        this.stol = stol;
        this.waiters = waiters;
    }

    @Override
    public Integer call() throws InterruptedException {
        int eaten = 0;
        while (eaten < target) {
            if (!waiters.bringPortion()) {
                throw new IllegalStateException("the sklad is empty:(");
            }
            stol.eat(place);
            eaten++;
        }
        return eaten;
    }
}
