package game.config.spawn.powerup;

import entity.EntityType;
import game.config.spawn.SpawnInfo;

public class PowerUpSpawnInfo extends SpawnInfo {

    public PowerUpSpawnInfo(int type, long spawnTime, double posX, double posY) {
        super(EntityType.POWERUP, type, spawnTime, posX, posY);
    }
}