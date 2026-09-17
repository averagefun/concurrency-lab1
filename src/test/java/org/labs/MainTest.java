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
        Result result = new Restaurant(new Config(7, 10_005, 2)).run();

        assertEquals(10_005, result.totalEaten());
        assertEquals(0, result.remainingPortions());
        assertTrue(result.isBalanced());
        assertEquals(1_429, result.minimumEaten());
        assertEquals(1_430, result.maximumEaten());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void notEnoughFood() throws InterruptedException {
        Result result = new Restaurant(new Config(10, 3, 4)).run();

        assertEquals(3, result.totalEaten());
        assertEquals(0, result.minimumEaten());
        assertEquals(1, result.maximumEaten());
        assertTrue(result.isBalanced());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void diffProgrammersCount() throws InterruptedException {
        for (int programmers = 2; programmers <= 12; programmers++) {
            int portions = programmers * 200 + 17; // чтобы не делилось на число программистов
            Result result = new Restaurant(
                    new Config(programmers, portions, Math.min(programmers, 3))
            ).run();

            assertEquals(portions, result.totalEaten());
            assertEquals(0, result.remainingPortions());
            assertTrue(result.isBalanced());
        }
    }
}
