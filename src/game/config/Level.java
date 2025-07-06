package game.config;

import entity.EntityType;
import game.config.spawn.SpawnInfo;

import java.util.List;
import java.util.stream.Collectors;

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

    public <T extends SpawnInfo> List<T> getSpawnList(EntityType entityType, Class<T> clazz) {
        return spawnList.stream()
                .filter(spawnInfo -> spawnInfo.getEntityType() == entityType)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
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
