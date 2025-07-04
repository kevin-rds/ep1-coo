package strategy.spawn.rules;

import entity.enemy.Enemy;
import entity.enemy.Enemy2;
import factory.TimedEntityFactory;
import strategy.delay.DelayStrategy;
import strategy.delay.FixedDelayStrategy;
import strategy.delay.RandomDelayStrategy;

public class Enemy2SpawnRule implements SpawnRule<Enemy> {

    private final TimedEntityFactory<Enemy> factory;
    private final DelayStrategy fastStrategy;
    private final DelayStrategy slowStrategy;

    private long timeToSpawnNext;

    public Enemy2SpawnRule(TimedEntityFactory<Enemy> factory, long timeToSpawnNext, long linearIntervalMs, long randomBaseIntervalMs) {
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
