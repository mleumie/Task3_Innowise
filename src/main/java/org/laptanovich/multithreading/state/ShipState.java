package org.laptanovich.multithreading.state;

import org.laptanovich.multithreading.entity.Ship;

public interface ShipState {
    void next(Ship ship);
}
