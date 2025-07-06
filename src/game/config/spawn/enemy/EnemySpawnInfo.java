package game.config.spawn.enemy;

import entity.EntityType;
import game.config.spawn.SpawnInfo;

public class EnemySpawnInfo extends SpawnInfo {

    public EnemySpawnInfo(int type, long spawnTime, double posX, double posY) {
        super(EntityType.ENEMY, type, spawnTime, posX, posY);
    }
}