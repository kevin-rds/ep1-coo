package game.config.spawn.enemy;

import entity.EntityType;
import game.config.spawn.SpawnInfo;
import game.config.spawn.SpawnInfoParser;

public class EnemySpawnInfoParser extends SpawnInfoParser {
    @Override
    public boolean canParse(EntityType entityType) {
        return entityType == EntityType.ENEMY;
    }

    @Override
    public SpawnInfo parse(String[] parts) {
        int type = Integer.parseInt(parts[1]);
        long when = Long.parseLong(parts[2]);
        double x = Double.parseDouble(parts[3]);
        double y = Double.parseDouble(parts[4]);
        return new EnemySpawnInfo(type, when, x, y);
    }
}
