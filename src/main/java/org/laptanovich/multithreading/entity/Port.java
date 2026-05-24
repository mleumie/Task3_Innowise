package org.laptanovich.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static final private Logger logger = LogManager.getLogger();

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pierCondition = lock.newCondition();
    private final Condition warehouseCondition = lock.newCondition();
    private final Deque<Pier> availablePiers;
    private final AtomicInteger warehouse;
    private final int capacity;

    private Port(int pierCount, int warehouseCapacity, int cargoCount) {
        this.availablePiers = new ArrayDeque<>();
        this.warehouse = new AtomicInteger(cargoCount);
        this.capacity = warehouseCapacity;
        for (int i = 1; i <= pierCount; i++) {
            availablePiers.addLast(new Pier(i));
        }
    }

    private static class Holder {
        private static final Port INSTANCE = new Port(2, 100, 50);
    }

    public static Port getInstance() {
        return Holder.INSTANCE;
    }

    public Pier dock(Ship ship) throws InterruptedException {
        lock.lock();
        try {
            while (availablePiers.isEmpty()) {
                logger.info("Ship {} is waiting", ship.getId());
                pierCondition.await();
            }
            Pier pier = availablePiers.pollFirst();
            logger.info("Ship docked");
            return pier;
        }
        finally {
            lock.unlock();
        }
    }

    public void release(Ship ship) {
        lock.lock();
        try {
            Pier pier = ship.getPier();
            availablePiers.addLast(pier);
            logger.info("Ship {} released {}", ship.getId(), pier);
            pierCondition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void unload(Ship ship) throws InterruptedException{
        lock.lock();
        try {
            int count = ship.getUnloadContainers();
            while (warehouse.get() + count > capacity) {
                warehouseCondition.await();
            }
            warehouse.addAndGet(count);
            ship.unload(count);
            warehouseCondition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    public void load(Ship ship) throws InterruptedException {
        lock.lock();
        try {
            int count = ship.getLoadContainers();
            while (warehouse.get() < count) {
                warehouseCondition.await();
            }
            warehouse.addAndGet(-count);
            ship.load(count);
            warehouseCondition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
}
