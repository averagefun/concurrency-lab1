package org.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

final class Stol {
    private static final long EATING_TIME_MILLIS = 30;

    private final List<Spoon> spoons;
    private final Semaphore seats;

    Stol(int size) {
        spoons = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            spoons.add(new Spoon());
        }
        seats = new Semaphore(size - 1, true);
    }

    void eat(int place) throws InterruptedException {
        Spoon left = spoons.get(place);
        Spoon right = spoons.get((place + 1) % spoons.size());
        boolean leftTaken = false;
        boolean rightTaken = false;

        seats.acquire();
        try {
            left.take();
            leftTaken = true;
            right.take();
            rightTaken = true;
            Thread.sleep(EATING_TIME_MILLIS);
        } finally {
            if (rightTaken) {
                right.putBack();
            }
            if (leftTaken) {
                left.putBack();
            }
            seats.release();
        }
    }
}
