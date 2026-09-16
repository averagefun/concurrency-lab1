package org.labs;

public class Main {
    public static void main(String[] args) {
        Config config = Config.fromArgs(args);
        System.out.printf("Programmers: %d, portions: %d, waiters: %d%n",
                config.programmers(), config.portions(), config.waiters());
    }
}
