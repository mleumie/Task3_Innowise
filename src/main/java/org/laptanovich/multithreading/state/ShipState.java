package org.laptanovich.multithreading.state;

import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.exception.CustomException;

public interface ShipState {
    void next(Ship ship) throws CustomException;
}
