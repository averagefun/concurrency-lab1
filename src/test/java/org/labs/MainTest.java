package org.labs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void consumesAllPortions() throws InterruptedException {
        Result result = new Restaurant(new Config(7, 73, 2)).run();

        assertEquals(73, result.totalEaten());
        assertEquals(0, result.remainingPortions());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void smallFoodSupply() throws InterruptedException {
        Result result = new Restaurant(new Config(10, 25, 4)).run();

        assertEquals(25, result.totalEaten());
        assertEquals(0, result.remainingPortions());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void differentProgrammersCount() throws InterruptedException {
        for (int programmers = 2; programmers <= 12; programmers++) {
            int portions = programmers * 5 + 1; // чтобы не делилось на число программистов
            Result result = new Restaurant(
                    new Config(programmers, portions, Math.min(programmers, 3))
            ).run();

            assertEquals(portions, result.totalEaten());
            assertEquals(0, result.remainingPortions());
        }
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void emptySklad() throws InterruptedException {
        Result result = new Restaurant(new Config(7, 0, 2)).run();

        assertEquals(0, result.totalEaten());
        assertEquals(0, result.remainingPortions());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void singleWaiter() throws InterruptedException {
        Result result = new Restaurant(new Config(5, 17, 1)).run();

        assertEquals(17, result.totalEaten());
        assertEquals(0, result.remainingPortions());
    }
}
