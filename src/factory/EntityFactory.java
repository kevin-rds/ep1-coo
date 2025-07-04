package factory;

import entity.Entity;

@FunctionalInterface
public interface EntityFactory<T extends Entity> {
    T create();
}
