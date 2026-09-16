package org.labs;

final class Sklad {
    private int portions;

    Sklad(int portions) {
        this.portions = portions;
    }

    synchronized boolean takePortion() {
        if (portions == 0) {
            return false;
        }
        portions--;
        return true;
    }

    synchronized int remaining() {
        return portions;
    }
}
