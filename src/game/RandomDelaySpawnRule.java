package game;

import entity.enemy.Enemy;

import java.util.function.Supplier;

public class RandomDelaySpawnRule implements EnemySpawnRule {
    private final Supplier<Enemy> factory;
    private final long delayMs;
    private long timeToSpawnNext;

    public RandomDelaySpawnRule(Supplier<Enemy> factory, long timeToSpawnNext, long delayMs) {
        this.factory = factory;
        this.timeToSpawnNext = timeToSpawnNext;
        this.delayMs = delayMs;
    }

    @Override
    public boolean shouldSpawn(long currentTime) {
        if (currentTime > timeToSpawnNext) {
            timeToSpawnNext = currentTime + getRandomDelay();
            return true;
        }
        return false;
    }

    @Override
    public Enemy spawn() {
        return factory.get();
    }

    private long getRandomDelay() {
        return delayMs + (long) (Math.random() * delayMs);
    }
}
