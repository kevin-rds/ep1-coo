package game;

import entity.enemy.Enemy;
import entity.enemy.Enemy2;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class Enemy2SpawnRule implements EnemySpawnRule {
    private final EnemySpawnRule fastSpawnRule;
    private final EnemySpawnRule slowSpawnRule;
    private final IntSupplier getEnemyCount;

    public Enemy2SpawnRule(long timeToSpawnNext, long linearIntervalMs, long randomBaseIntervalMs) {
        Supplier<Enemy> enemy2Supplier = Enemy2::new;
        this.fastSpawnRule = new TimedSpawnRule(enemy2Supplier, timeToSpawnNext, linearIntervalMs);
        this.slowSpawnRule = new RandomDelaySpawnRule(enemy2Supplier, timeToSpawnNext, randomBaseIntervalMs);
        this.getEnemyCount = Enemy2::getCount;
    }

    private EnemySpawnRule selectRule() {
        return getEnemyCount.getAsInt() < 10 ? fastSpawnRule : slowSpawnRule;
    }

    @Override
    public boolean shouldSpawn(long currentTime) {
        return selectRule().shouldSpawn(currentTime);
    }

    @Override
    public Enemy spawn() {
        return selectRule().spawn();
    }
}
