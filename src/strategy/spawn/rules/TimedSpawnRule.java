package strategy.spawn.rules;

import entity.Entity;
import entity.enemy.Enemy;
import entity.enemy.EnemyFactory;
import factory.EntityFactory;
import game.EnemySpawnRule;

public class TimedSpawnRule<T extends Entity> implements SpawnRule<T> {
    private final EntityFactory<T> factory;
    private final long spawnInterval;
    private long timeToSpawnNext;

    public TimedSpawnRule(EntityFactory<T> factory, long timeToSpawnNext, long intervalMs) {
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
    public T spawn(long currentTime) {
        return factory.create(currentTime);
    }
}
