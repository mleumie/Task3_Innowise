package org.laptanovich.multithreading.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.entity.Port;
import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.exception.CustomException;
import org.laptanovich.multithreading.state.ShipState;

public class ProcessingState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) throws CustomException {
        logger.info("Ship {} is processing", ship.getId());
        Port port = Port.getInstance();
        try {
            port.unload(ship);
            port.load(ship);
            port.release(ship);
            ship.setState(new CompletedState());
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomException("Ship " + ship.getId() + " was interrupted", e);
        }
    }
}
