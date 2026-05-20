package org.laptanovich.multithreading.state.impl;

import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatedState implements ShipState {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void next(Ship ship) {
        logger.info("Ship {} is created", ship.getId());
        ship.setState(new WaitingState());
    }
}
