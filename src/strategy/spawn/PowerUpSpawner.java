package strategy.spawn;

import entity.Entity;
import entity.PowerUpEntity;
import factory.EntityFactory;
import powerup.TripleShotPowerUp;
import strategy.spawn.rules.SpawnRule;
import strategy.spawn.rules.TimedSpawnRule;

import java.util.ArrayList;
import java.util.List;

public class PowerUpSpawner extends EntitySpawner<PowerUpEntity> {

    public PowerUpSpawner(long currentTime) {
        super();

        // TODO remover esse power up hardcoded - precisa ser gerado por configuracao de fase
        EntityFactory<PowerUpEntity> factory = (time) -> new PowerUpEntity(time, new TripleShotPowerUp(time, 7000));
        addRule(new TimedSpawnRule<>(factory, currentTime + 1000, 2 * 1000));
    }
}
