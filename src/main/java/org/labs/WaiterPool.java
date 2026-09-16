package org.labs;

import java.util.concurrent.Semaphore;

final class WaiterPool {
    private final Semaphore availableWaiters;
    private final Sklad sklad;

    WaiterPool(int waiterCount, Sklad sklad) {
        availableWaiters = new Semaphore(waiterCount, true);
        this.sklad = sklad;
    }

    boolean bringPortion() throws InterruptedException {
        availableWaiters.acquire();
        try {
            return sklad.takePortion();
        } finally {
            availableWaiters.release();
        }
    }
}
