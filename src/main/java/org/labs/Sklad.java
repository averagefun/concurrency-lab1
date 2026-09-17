package org.labs;

import java.util.concurrent.atomic.AtomicInteger;

final class Sklad {
    private final AtomicInteger portions;

    Sklad(int portions) {
        this.portions = new AtomicInteger(portions);
    }

    boolean takePortion() {
        int current;
        do {
            current = portions.get();
            if (current == 0) {
                return false;
            }
        } while (!portions.compareAndSet(current, current - 1));
        return true;
    }

    int remaining() {
        return portions.get();
    }
}
