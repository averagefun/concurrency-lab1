package org.labs;

import java.util.ArrayList;
import java.util.List;

final class Stol {
    private static final long EATING_TIME_MILLIS = 30;

    private final List<Spoon> spoons;

    Stol(int size) {
        spoons = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            spoons.add(new Spoon());
        }
    }

    void eat(int place) throws InterruptedException {
        int neighbour = (place + 1) % spoons.size();
        Spoon first = spoons.get(Math.min(place, neighbour));
        Spoon second = spoons.get(Math.max(place, neighbour));
        boolean firstTaken = false;
        boolean secondTaken = false;

        try {
            first.take();
            firstTaken = true;
            second.take();
            secondTaken = true;
            Thread.sleep(EATING_TIME_MILLIS);
        } finally {
            if (secondTaken) {
                second.putBack();
            }
            if (firstTaken) {
                first.putBack();
            }
        }
    }
}
