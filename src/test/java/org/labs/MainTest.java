package org.labs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void checkForBalance() throws InterruptedException {
        Result result = new Restaurant(new Config(7, 73, 2)).run();

        assertEquals(73, result.totalEaten());
        assertEquals(0, result.remainingPortions());
        assertTrue(result.isBalanced());
        assertEquals(10, result.minimumEaten());
        assertEquals(11, result.maximumEaten());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void smallFoodSupply() throws InterruptedException {
        Result result = new Restaurant(new Config(10, 25, 4)).run();

        assertEquals(25, result.totalEaten());
        assertEquals(2, result.minimumEaten());
        assertEquals(3, result.maximumEaten());
        assertTrue(result.isBalanced());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void diffProgrammersCount() throws InterruptedException {
        for (int programmers = 2; programmers <= 12; programmers++) {
            int portions = programmers * 5 + 1; // чтобы не делилось на число программистов
            Result result = new Restaurant(
                    new Config(programmers, portions, Math.min(programmers, 3))
            ).run();

            assertEquals(portions, result.totalEaten());
            assertEquals(0, result.remainingPortions());
            assertTrue(result.isBalanced());
        }
    }
}
