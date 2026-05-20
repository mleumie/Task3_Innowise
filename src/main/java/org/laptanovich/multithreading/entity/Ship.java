package org.laptanovich.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.state.ShipState;
import org.laptanovich.multithreading.state.impl.CompletedState;
import org.laptanovich.multithreading.state.impl.CreatedState;
import org.laptanovich.multithreading.state.impl.WaitingState;

import java.util.concurrent.Callable;

public class Ship implements Callable<Integer> {
    private static final Logger logger = LogManager.getLogger();

    private final int id;
    private final int capacity;
    private int containers;
    private ShipState state;

    public Ship(int id, int capacity, int containers) {
        this.id = id;
        this.capacity = capacity;
        this.containers = containers;
        this.state = new CreatedState();
    }

    @Override
    public Integer call() {
        while (!(state instanceof CompletedState)) {
            state.next(this);
        }


        return containers;
    }

    public int getId() {
        return id;
    }

    public void setState(ShipState state) {
        this.state = state;
    }
}
