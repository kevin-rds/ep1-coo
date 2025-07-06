package strategy.spawn;

import entity.PowerUpEntity;
import factory.TimedEntityFactory;
import powerup.InvincibilityPowerUp;
import powerup.TripleShotPowerUp;
import strategy.spawn.rules.TimedSpawnRule;

public class PowerUpSpawner extends EntitySpawner<PowerUpEntity> {

    public PowerUpSpawner(long currentTime) {
        super();

        TimedEntityFactory<PowerUpEntity> tripleShotPowerUpFactory = (time) -> new PowerUpEntity(time, new TripleShotPowerUp(time, 7000));
        TimedEntityFactory<PowerUpEntity> invicibilityPowerUpFactory = (time) -> new PowerUpEntity(time, new InvincibilityPowerUp(time, 5000));
        addRule(new TimedSpawnRule<>(tripleShotPowerUpFactory, currentTime + 1000, 2 * 1000));
        addRule(new TimedSpawnRule<>(invicibilityPowerUpFactory, currentTime + 1000, 2 * 3000));
    }
}
