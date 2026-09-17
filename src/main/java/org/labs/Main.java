package org.labs;

public class Main {
    // ./gradlew run --args="<programmers> <portions> <waiters>", ex 7 1000000 3
    public static void main(String[] args) throws InterruptedException {
        Config config = Config.fromArgs(args);
        long startedAt = System.nanoTime();
        Result result = new Restaurant(config).run();
        long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000;

        for (int i = 0; i < result.portionsByProgrammer().size(); i++) {
            System.out.printf("Programmer %d ate %d portions%n", i + 1, result.portionsByProgrammer().get(i));
        }
        System.out.printf("Total: %d, left: %d, time: %d ms%n",
                result.totalEaten(), result.remainingPortions(), elapsedMillis);
    }
}
