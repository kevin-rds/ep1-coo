package game.config.spawn;

import entity.EntityType;

public abstract class SpawnInfoParser {
    public abstract boolean canParse(EntityType entityType);
    public boolean canParse(String entityType) {
        return canParse(EntityType.fromLabel(entityType));
    }
    public abstract SpawnInfo parse(String[] parts);
}
