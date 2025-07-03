package game;

import entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawner {

    private final List<EnemySpawnRule> spawnRules;

    public EnemySpawner(List<EnemySpawnRule> spawnRules) {
        this.spawnRules = spawnRules;
    }

    public List<Enemy> spawn(long currentTime) {
        List<Enemy> newEnemies = new ArrayList<>();
        for (EnemySpawnRule rule : spawnRules) {
            if (rule.shouldSpawn(currentTime)) {
                newEnemies.add(rule.spawn());
            }
        }
        return newEnemies;
    }
}
