package factory;

import entity.Entity;

@FunctionalInterface
public interface TimedEntityFactory<T extends Entity> {
    T create(long currentTime);
}
