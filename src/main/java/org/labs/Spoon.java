package org.labs;

import java.util.concurrent.locks.ReentrantLock;

final class Spoon {
    private final ReentrantLock lock = new ReentrantLock(true);

    void take() throws InterruptedException {
        lock.lockInterruptibly();
    }

    void putBack() {
        lock.unlock();
    }
}
