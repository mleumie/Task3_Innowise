package org.laptanovich.multithreading.reader;

import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.exception.CustomException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShipReader {
    public List<Ship> readShips(String file) throws CustomException{
        List<Ship> ships = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))){
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                int capacity = scanner.nextInt();
                int containers = scanner.nextInt();
                int unloadContainers = scanner.nextInt();
                int loadContainers = scanner.nextInt();
                Ship ship = new Ship(id, capacity, containers, unloadContainers, loadContainers);
                ships.add(ship);
            }
        }
        catch (FileNotFoundException e) {
            throw new CustomException("File not found " + file);
        }
        return ships;
    }
}
