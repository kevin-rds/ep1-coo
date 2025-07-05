package game.config;

public class SpawnInfo {
    private final String entityType;
    private final int type;
    private final long spawnTime;
    private final double posX;
    private final double posY;
    private final int health;


    public SpawnInfo(String entityType, int type, long spawnTime, double posX, double posY) {
        if (!"INIMIGO".equalsIgnoreCase(entityType)) {
            throw new IllegalArgumentException("EntityType must be INIMIGO for this constructor.");
        }
        this.entityType = entityType;
        this.type = type;
        this.spawnTime = spawnTime;
        this.posX = posX;
        this.posY = posY;
        this.health = -1;
    }


    public SpawnInfo(String entityType, int type, int health, long spawnTime, double posX, double posY) {
        if (!"CHEFE".equalsIgnoreCase(entityType)) {
            throw new IllegalArgumentException("EntityType must be CHEFE for this constructor.");
        }
        this.entityType = entityType;
        this.type = type;
        this.health = health;
        this.spawnTime = spawnTime;
        this.posX = posX;
        this.posY = posY;
    }

    public String getEntityType() {
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
    public int getHealth() {
        return health;
    }
}