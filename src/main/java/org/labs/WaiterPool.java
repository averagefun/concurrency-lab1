package org.labs;

import java.util.concurrent.Semaphore;

final class WaiterPool {
    private static final long DELIVERY_TIME_MILLIS = 10;

    private final Semaphore availableWaiters;
    private final Sklad sklad;

    WaiterPool(int waiterCount, Sklad sklad) {
        availableWaiters = new Semaphore(waiterCount, true);
        this.sklad = sklad;
    }

    boolean bringPortion() throws InterruptedException {
        availableWaiters.acquire();
        try {
            Thread.sleep(DELIVERY_TIME_MILLIS);
            return sklad.takePortion();
        } finally {
            availableWaiters.release();
        }
    }
}
