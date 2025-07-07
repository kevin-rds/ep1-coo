package game.config;

import game.config.spawn.SpawnInfo;
import game.config.spawn.SpawnInfoParser;
import game.config.spawn.boss.BossSpawnInfoParser;
import game.config.spawn.enemy.EnemySpawnInfoParser;
import game.config.spawn.powerup.PowerUpSpawnInfoParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    public static int playerLives;
    public static List<Level> levels = new ArrayList<>();
    private static final List<SpawnInfoParser> spawnParsers = List.of(
            new EnemySpawnInfoParser(),
            new BossSpawnInfoParser(),
            new PowerUpSpawnInfoParser()
    );

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

                boolean parsed = false;
                for (SpawnInfoParser parser : spawnParsers) {
                    if (parser.canParse(entityType)) {
                        spawnList.add(parser.parse(parts));
                        parsed = true;
                        break;
                    }
                }

                if (!parsed) {
                    throw new IllegalArgumentException("Tipo de entidade desconhecida: " + entityType);
                }
            }
        }

        return new Level(levelConfigFilePath, spawnList);
    }
}