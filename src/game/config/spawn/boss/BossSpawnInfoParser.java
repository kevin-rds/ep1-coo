package game.config.spawn.boss;

import entity.EntityType;
import game.config.spawn.SpawnInfo;
import game.config.spawn.SpawnInfoParser;

public class BossSpawnInfoParser extends SpawnInfoParser {
    @Override
    public boolean canParse(EntityType entityType) {
        return entityType == EntityType.BOSS;
    }

    @Override
    public SpawnInfo parse(String[] parts) {
        int type = Integer.parseInt(parts[1]);
        int health = Integer.parseInt(parts[2]);
        long when = Long.parseLong(parts[3]);
        double x = Double.parseDouble(parts[4]);
        double y = Double.parseDouble(parts[5]);
        return new BossSpawnInfo(type, health, when, x, y);
    }
}
