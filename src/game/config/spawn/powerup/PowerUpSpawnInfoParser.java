package game.config.spawn.powerup;

import entity.EntityType;
import game.config.spawn.SpawnInfo;
import game.config.spawn.SpawnInfoParser;

public class PowerUpSpawnInfoParser extends SpawnInfoParser {
    @Override
    public boolean canParse(EntityType entityType) {
        return entityType == EntityType.POWERUP;
    }

    @Override
    public SpawnInfo parse(String[] parts) {
        int type = Integer.parseInt(parts[1]);
        long when = Long.parseLong(parts[2]);
        double x = Double.parseDouble(parts[3]);
        double y = Double.parseDouble(parts[4]);
        return new PowerUpSpawnInfo(type, when, x, y);
    }
}
