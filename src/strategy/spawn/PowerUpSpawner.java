package strategy.spawn;

import entity.PowerUpEntity;
import factory.EntityFactory;
import factory.TimedEntityFactory;
import game.config.spawn.powerup.PowerUpSpawnInfo;
import powerup.InvincibilityPowerUp;
import powerup.TripleShotPowerUp;
import strategy.spawn.rules.SingleSpawnRule;
import strategy.spawn.rules.TimedSpawnRule;

import java.util.List;

public class PowerUpSpawner extends EntitySpawner<PowerUpEntity> {

    public PowerUpSpawner(long currentTime) {
        super();

        TimedEntityFactory<PowerUpEntity> tripleShotPowerUpFactory = (time) -> new PowerUpEntity(time, new TripleShotPowerUp(time, 7000));
        TimedEntityFactory<PowerUpEntity> invicibilityPowerUpFactory = (time) -> new PowerUpEntity(time, new InvincibilityPowerUp(time, 5000));
        addRule(new TimedSpawnRule<>(tripleShotPowerUpFactory, currentTime + 1000, 2 * 1000));
        addRule(new TimedSpawnRule<>(invicibilityPowerUpFactory, currentTime + 1000, 2 * 3000));
    }

    public PowerUpSpawner(List<PowerUpSpawnInfo> spawnList, long levelStartTime) {
        super();
        for (PowerUpSpawnInfo info : spawnList) {
            EntityFactory<PowerUpEntity> factory = () -> {
                PowerUpEntity newPowerUp = null;
                long spawnTime = levelStartTime + info.getSpawnTime();

                if (info.getType() == 1) {
                    newPowerUp = new PowerUpEntity(spawnTime, new TripleShotPowerUp(spawnTime, 7000), info.getPosX(), info.getPosY());
                } else if (info.getType() == 2) {
                    newPowerUp =  new PowerUpEntity(spawnTime, new InvincibilityPowerUp(spawnTime, 5000), info.getPosX(), info.getPosY());
                }

                return newPowerUp;
            };

            addRule(new SingleSpawnRule<>(factory, levelStartTime + info.getSpawnTime()));
        }
    }
}
