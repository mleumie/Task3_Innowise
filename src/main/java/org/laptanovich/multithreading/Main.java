package org.laptanovich.multithreading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.entity.Port;
import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.exception.CustomException;
import org.laptanovich.multithreading.reader.ShipReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ShipReader shipReader = new ShipReader();
        ExecutorService executor = null;

        try {
            List<Ship> ships = shipReader.readShips("src/main/resources/ships.txt");
            logger.info("Ships loaded: {}", ships.size());
            executor = Executors.newFixedThreadPool(ships.size());
            List<Future<Integer>> futures = new ArrayList<>();
            for (Ship ship : ships) {
                futures.add(executor.submit(ship));
            }
            for (Future<Integer> future : futures) {
                int result = future.get();
                logger.info("Ship finished with {} containers", result);
            }
        } catch (CustomException e) {
            logger.error("Application error: {}", e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Main thread was interrupted", e);
        } catch (ExecutionException e) {
            logger.error("Ship execution error", e);
        }
        finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
    }
}