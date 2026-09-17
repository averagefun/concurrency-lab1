package org.labs;

import java.util.List;

public record Result(List<Integer> portionsByProgrammer, int remainingPortions) {
    public Result {
        portionsByProgrammer = List.copyOf(portionsByProgrammer);
    }

    public int totalEaten() {
        return portionsByProgrammer.stream().mapToInt(Integer::intValue).sum();
    }

    public int minimumEaten() {
        return portionsByProgrammer.stream().mapToInt(Integer::intValue).min().orElse(0);
    }

    public int maximumEaten() {
        return portionsByProgrammer.stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    public boolean isBalanced() {
        return maximumEaten() - minimumEaten() <= 1;
    }
}
