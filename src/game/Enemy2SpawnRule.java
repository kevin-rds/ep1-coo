package game;

import entity.enemy.Enemy;
import entity.enemy.Enemy2;
import entity.enemy.EnemyFactory;

public class Enemy2SpawnRule implements EnemySpawnRule {

    private final EnemyFactory factory;
    private final DelayStrategy fastStrategy;
    private final DelayStrategy slowStrategy;

    private long timeToSpawnNext;

    public Enemy2SpawnRule(EnemyFactory factory, long timeToSpawnNext, long linearIntervalMs, long randomBaseIntervalMs) {
        this.factory = factory;
        this.timeToSpawnNext = timeToSpawnNext;
        this.fastStrategy = new FixedDelayStrategy(linearIntervalMs);
        this.slowStrategy = new RandomDelayStrategy(randomBaseIntervalMs);
    }

    private DelayStrategy getCurrentStrategy() {
        return Enemy2.getCount() < 10 ? fastStrategy : slowStrategy;
    }

    @Override
    public boolean shouldSpawn(long currentTime) {
        if (currentTime > timeToSpawnNext) {
            timeToSpawnNext = currentTime + getCurrentStrategy().getNextDelay();
            return true;
        }

        return false;
    }

    @Override
    public Enemy spawn(long currentTime) {
        return factory.create(currentTime);
    }
}
