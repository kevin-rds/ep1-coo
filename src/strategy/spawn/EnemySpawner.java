package strategy.spawn;

import entity.enemy.Enemy;
import entity.enemy.Enemy1;
import entity.enemy.Enemy2;
import factory.EntityFactory;
import factory.TimedEntityFactory;
import game.config.spawn.enemy.EnemySpawnInfo;
import strategy.spawn.rules.Enemy2SpawnRule;
import strategy.spawn.rules.SingleSpawnRule;
import strategy.spawn.rules.TimedSpawnRule;

import java.util.List;

public class EnemySpawner extends EntitySpawner<Enemy> {

    public EnemySpawner(long currentTime) {
        super();

        TimedEntityFactory<Enemy> enemy1Factory = Enemy1::new;
        TimedEntityFactory<Enemy> enemy2Factory = (time) -> new Enemy2();

        addRule(new TimedSpawnRule<>(enemy1Factory, currentTime + 2000, 500));
        addRule(new Enemy2SpawnRule(enemy2Factory, currentTime + 7000, 120, 3000));
    }
    public EnemySpawner(List<EnemySpawnInfo> spawnList, long levelStartTime) {
        super();
        for (EnemySpawnInfo info : spawnList) {
            EntityFactory<Enemy> factory = () -> {
                Enemy newEnemy = null;
                if (info.getType() == 1) {
                    newEnemy = new Enemy1(levelStartTime + info.getSpawnTime());
                } else if (info.getType() == 2) {
                    newEnemy = new Enemy2();
                }

                return newEnemy;
            };

            addRule(new SingleSpawnRule<>(factory, levelStartTime + info.getSpawnTime()));
        }
    }
}
