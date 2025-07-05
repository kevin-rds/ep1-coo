package game.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    public static int playerLives;
    public static List<Level> levels = new ArrayList<>();

    public static void loadGameConfig(String mainConfigFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(mainConfigFilePath))) {
            playerLives = Integer.parseInt(reader.readLine().trim());
            int numLevels = Integer.parseInt(reader.readLine().trim());
            levels.clear();

            for (int i = 0; i < numLevels; i++) {
                String levelFileName = reader.readLine().trim();
                String fullPath = "configfiles/" + levelFileName;
                levels.add(loadLevelConfig(fullPath));
            }
        }
    }

    private static Level loadLevelConfig(String levelConfigFilePath) throws IOException {
        List<SpawnInfo> spawnList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(levelConfigFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" ");
                String entityType = parts[0];
                int type = Integer.parseInt(parts[1]);

                if ("INIMIGO".equalsIgnoreCase(entityType)) {
                    long when = Long.parseLong(parts[2]);
                    double x = Double.parseDouble(parts[3]);
                    double y = Double.parseDouble(parts[4]);
                    spawnList.add(new SpawnInfo(entityType, type, when, x, y));
                } else if ("CHEFE".equalsIgnoreCase(entityType)) {
                    int health = Integer.parseInt(parts[2]);
                    long when = Long.parseLong(parts[3]);
                    double x = Double.parseDouble(parts[4]);
                    double y = Double.parseDouble(parts[5]);
                    spawnList.add(new SpawnInfo(entityType, type, health, when, x, y));
                }
            }
        }

        return new Level(levelConfigFilePath, spawnList);
    }
}