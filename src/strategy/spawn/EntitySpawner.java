package strategy.spawn;

import entity.Entity;
import strategy.spawn.rules.SpawnRule;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawner<T extends Entity> {
    private final List<SpawnRule<T>> spawnRules = new ArrayList<>();

    public void addRule(SpawnRule<T> rule) {
        spawnRules.add(rule);
    }

    public List<T> spawn(long currentTime) {
        List<T> newEntities = new ArrayList<>();

        for (SpawnRule<T> rule : spawnRules) {
            if (rule.shouldSpawn(currentTime)) {
                newEntities.add(rule.spawn(currentTime));
            }
        }

        return newEntities;
    }
}
