package org.laptanovich.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.exception.CustomException;
import org.laptanovich.multithreading.state.ShipState;
import org.laptanovich.multithreading.state.impl.CompletedState;
import org.laptanovich.multithreading.state.impl.CreatedState;
import java.util.concurrent.Callable;

public class Ship implements Callable<Integer> {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final int capacity;
    private int containers;
    private final int unloadContainers;
    private final int loadContainers;
    private Pier pier;
    private ShipState state;

    public Ship(int id, int capacity, int containers, int unloadContainers, int loadContainers) {
        this.id = id;
        this.capacity = capacity;
        this.containers = containers;
        this.unloadContainers = unloadContainers;
        this.loadContainers = loadContainers;
        this.state = new CreatedState();
    }

    @Override
    public Integer call() throws CustomException {
        logger.info("Ship {} started work", id);
        while (!(state instanceof CompletedState)) {
            state.next(this);
        }
        logger.info("Ship {} finished with {} containers", id, containers);
        return containers;
    }

    public int getId() {
        return id;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public int getUnloadContainers() {
        return unloadContainers;
    }

    public int getLoadContainers() {
        return loadContainers;
    }

    public void setPier(Pier pier) {
        this.pier = pier;
    }

    public Pier getPier() {
        return pier;
    }

    public void unload(int count) {
        containers -= count;
    }

    public void load(int count) {
        containers += count;
    }


}
