package strategy.spawn;

import entity.boss.Boss;
import entity.boss.Boss1;
import entity.boss.Boss2;
import factory.EntityFactory;
import game.config.spawn.boss.BossSpawnInfo;
import strategy.spawn.rules.SingleSpawnRule;

import java.util.List;

public class BossSpawner extends EntitySpawner<Boss> {
    public BossSpawner(List<BossSpawnInfo> spawnList, long levelStartTime) {
        super();
        for (BossSpawnInfo info : spawnList) {
            EntityFactory<Boss> factory = () -> {
                Boss newBoss = null;
                if (info.getType() == 1) {
                    newBoss = new Boss1(info.getHealth(), info.getPosX(), info.getPosY());
                } else if (info.getType() == 2) {
                    newBoss = new Boss2(info.getHealth(), info.getPosX(), info.getPosY());
                }

                return newBoss;
            };
            addRule(new SingleSpawnRule<>(factory, levelStartTime + info.getSpawnTime()));
        }
    }
}
