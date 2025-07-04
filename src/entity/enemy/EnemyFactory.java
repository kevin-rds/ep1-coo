package entity.enemy;

@FunctionalInterface
public interface EnemyFactory {
    Enemy create(long currentTime);
}
