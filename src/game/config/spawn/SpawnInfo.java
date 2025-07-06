package game.config.spawn;

import entity.EntityType;

public class SpawnInfo {
    private final EntityType entityType;
    private final int type;
    private final long spawnTime;
    private final double posX;
    private final double posY;

    public SpawnInfo(EntityType entityType, int type, long spawnTime, double posX, double posY) {
        this.entityType = entityType;
        this.type = type;
        this.spawnTime = spawnTime;
        this.posX = posX;
        this.posY = posY;
    }

    public EntityType getEntityType() {
        return entityType;
    }
    public int getType() {
        return type;
    }
    public long getSpawnTime() {
        return spawnTime;
    }
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
}