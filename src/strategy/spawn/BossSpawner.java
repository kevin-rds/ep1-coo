package strategy.spawn;

import entity.boss.Boss;
import entity.boss.Boss1;
import entity.boss.Boss2;
import factory.EntityFactory;
import game.config.SpawnInfo;
import strategy.spawn.rules.SingleSpawnRule;

import java.util.List;

public class BossSpawner extends EntitySpawner<Boss> {

    public BossSpawner(long currentTime) {
        super();

        // TODO remover esse power up hardcoded - precisa ser gerado por configuracao de fase
        EntityFactory<Boss> boss1Factory = Boss1::new;
        EntityFactory<Boss> boss2Factory = Boss2::new;

//        addRule(new SingleSpawnRule<>(boss1Factory, currentTime + 2000));
        addRule(new SingleSpawnRule<>(boss2Factory, currentTime + 2000));
    }
    public BossSpawner(List<SpawnInfo> spawnList, long levelStartTime) {
        super();
        for (SpawnInfo info : spawnList) {
            if ("CHEFE".equalsIgnoreCase(info.getEntityType())) {
                EntityFactory<Boss> factory = () -> {
                    Boss newBoss = null;
                    if (info.getType() == 1) {
                        newBoss = new Boss1();
                    } else if (info.getType() == 2) {
                        newBoss = new Boss2();
                    }

                    return newBoss;
                };
                addRule(new SingleSpawnRule<>(factory, levelStartTime + info.getSpawnTime()));
            }
        }
    }
}
