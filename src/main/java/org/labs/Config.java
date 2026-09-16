package org.labs;

public record Config(int programmers, int portions, int waiters) {

    public static Config fromArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("expected: <programmers> <portions> <waiters>");
        }

        return new Config(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }
}
