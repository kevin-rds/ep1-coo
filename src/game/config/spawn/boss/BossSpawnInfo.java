package game.config.spawn.boss;

import entity.EntityType;
import game.config.spawn.SpawnInfo;

public class BossSpawnInfo extends SpawnInfo {

    private final int health;

    public BossSpawnInfo(int type, int health, long spawnTime, double posX, double posY) {
        super(EntityType.BOSS, type, spawnTime, posX, posY);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}