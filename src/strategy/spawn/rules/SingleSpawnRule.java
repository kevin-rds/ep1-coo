package strategy.spawn.rules;

import entity.Entity;
import factory.EntityFactory;

public class SingleSpawnRule<T extends Entity> implements SpawnRule<T> {

    private final EntityFactory<T> factory;
    private long timeToSpawnNext;
    private boolean hasSpawned = false;

    public SingleSpawnRule(EntityFactory<T> factory, long timeToSpawnNext) {
        this.factory = factory;
        this.timeToSpawnNext = timeToSpawnNext;
    }

    @Override
    public boolean shouldSpawn(long currentTime) {
        if (currentTime > timeToSpawnNext && !hasSpawned) {
            timeToSpawnNext = -1;
            return true;
        }
        return false;
    }

    @Override
    public T spawn(long currentTime) {
        hasSpawned = true;
        return factory.create();
    }
}