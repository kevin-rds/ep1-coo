package game.config;

import java.util.List;

public class Level {
    private final List<SpawnInfo> spawnList;
    private boolean completed = false;
    private final String configFileName;

    public Level(String configFileName, List<SpawnInfo> spawnList) {
        this.configFileName = configFileName;
        this.spawnList = spawnList;
    }

    public List<SpawnInfo> getSpawnList() {
        return spawnList;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getConfigFileName() {
        return configFileName;
    }
}
