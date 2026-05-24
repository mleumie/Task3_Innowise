import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.laptanovich.multithreading.entity.Port;
import org.laptanovich.multithreading.entity.Ship;
import org.laptanovich.multithreading.reader.ShipReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {
    private static final Logger logger = LogManager.getLogger();

    private static void main(String[] args) {
        ShipReader shipReader = new ShipReader();
        ExecutorService executor;

        try {
            Port.getInstance();
            List<Ship> ships = shipReader.readShips("src/main/resources/ships.txt");
            executor = Executors.newFixedThreadPool(ships.size());
            List<Future<Integer>> futures = new ArrayList<>();

        }

    }
}