package org.laptanovich.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static final private Logger logger = LogManager.getLogger();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pierCondition = lock.newCondition();
    private int availablePiers;
    private final AtomicInteger warehouse;
    private final int capacity;

    public Port(int pierCount, int warehouseCapacity, int cargoCount) {
        this.availablePiers = pierCount;
        this.warehouse = new AtomicInteger(cargoCount);
        this.capacity = warehouseCapacity;
    }

    private static class Holder {
        private static final Port INSTANCE = new Port(2, 100, 50);
    }

    private static Port getInstance() {
        return Holder.INSTANCE;
    }

    public void dock() throws InterruptedException {
        lock.lock();
        try {
            while (availablePiers <=0 ) {
                logger.info("Ship is waiting");
                pierCondition.await();
            }
            availablePiers--;
            logger.info("Ship docked");
        }
        finally {
            lock.unlock();
        }
    }

    private void release() {
        lock.lock();
        try {
            availablePiers++;
            logger.info("Ship undocked");
            pierCondition.signal();
        }
        finally {
            lock.unlock();
        }
    }
}
