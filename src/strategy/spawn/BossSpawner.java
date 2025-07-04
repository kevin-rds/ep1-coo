package strategy.spawn;

import entity.boss.Boss;
import entity.boss.Boss1;
import entity.boss.Boss2;
import factory.EntityFactory;
import strategy.spawn.rules.SingleSpawnRule;

public class BossSpawner extends EntitySpawner<Boss> {

    public BossSpawner(long currentTime) {
        super();

        // TODO remover esse power up hardcoded - precisa ser gerado por configuracao de fase
        EntityFactory<Boss> boss1Factory = Boss1::new;
        EntityFactory<Boss> boss2Factory = Boss2::new;

//        addRule(new SingleSpawnRule<>(boss1Factory, currentTime + 2000));
        addRule(new SingleSpawnRule<>(boss2Factory, currentTime + 2000));
    }
}
