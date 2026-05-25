package org.laptanovich.multithreading.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.entity.Pier;
import org.laptanovich.multithreading.entity.Port;
import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.exception.CustomException;
import org.laptanovich.multithreading.state.ShipState;

public class WaitingState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) throws CustomException {
        Port port = Port.getInstance();
        try {
            logger.info("Ship {} is waiting", ship.getId());
            Pier pier = port.dock(ship);
            ship.setPier(pier);
            ship.setState(new ProcessingState());
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomException("Ship " + ship.getId() + " was interrupted", e);
        }
    }
}
