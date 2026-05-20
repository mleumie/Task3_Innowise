package org.laptanovich.multithreading.state.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.state.ShipState;

public class CompletedState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) {
        logger.info("Ship {} is completed", ship.getId());
    }
}
