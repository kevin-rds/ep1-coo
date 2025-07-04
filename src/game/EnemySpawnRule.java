package game;

import entity.enemy.Enemy;

public interface EnemySpawnRule {
    boolean shouldSpawn(long currentTime);
    Enemy spawn(long currentTime);
}
