package strategy.spawn;

import entity.enemy.Enemy;
import entity.enemy.Enemy1;
import entity.enemy.Enemy2;
import factory.TimedEntityFactory;
import strategy.spawn.rules.Enemy2SpawnRule;
import strategy.spawn.rules.TimedSpawnRule;

public class EnemySpawner extends EntitySpawner<Enemy> {

    public EnemySpawner(long currentTime) {
        super();

        // TODO remover esse power up hardcoded - precisa ser gerado por configuracao de fase
        TimedEntityFactory<Enemy> enemy1Factory = Enemy1::new;
        TimedEntityFactory<Enemy> enemy2Factory = (time) -> new Enemy2();

        addRule(new TimedSpawnRule<>(enemy1Factory, currentTime + 2000, 500));
        addRule(new Enemy2SpawnRule(enemy2Factory, currentTime + 7000, 120, 3000));
    }
}
