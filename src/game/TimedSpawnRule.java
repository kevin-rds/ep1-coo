package game;

import entity.enemy.Enemy;
import entity.enemy.EnemyFactory;

import java.sql.Time;
import java.util.function.Supplier;

public class TimedSpawnRule implements EnemySpawnRule {
    private final EnemyFactory factory;
    private final long spawnInterval;
    private long timeToSpawnNext;

    public TimedSpawnRule(EnemyFactory factory, long timeToSpawnNext, long intervalMs) {
        this.factory = factory;
        this.timeToSpawnNext = timeToSpawnNext;
        this.spawnInterval = intervalMs;
    }

    @Override
    public boolean shouldSpawn(long currentTime) {
        if (currentTime > timeToSpawnNext) {
            timeToSpawnNext = currentTime + spawnInterval;
            return true;
        }
        return false;
    }

    @Override
    public Enemy spawn(long currentTime) {
        return factory.create(currentTime);
    }
}
