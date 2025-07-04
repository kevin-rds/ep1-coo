package strategy.spawn.rules;

import entity.Entity;

public interface SpawnRule<T extends Entity> {
    boolean shouldSpawn(long currentTime);
    T spawn(long currentTime);
}
