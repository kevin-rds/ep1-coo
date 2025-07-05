package game.context;

import entity.Player;
import entity.boss.Boss;
import entity.enemy.Enemy;
import entity.projectiles.Projectile;
import entity.PowerUpEntity;
import graphics.Background;
import game.manager.LifeManager;

import java.util.List;

public class GameContext {

    public final Player player;
    public final Background background1;
    public final Background background2;
    public final LifeManager lifeManager;
    public final List<Enemy> enemies;
    public final List<Boss> bosses;
    public final List<Projectile> projectiles;
    public final List<Projectile> enemyProjectiles;
    public final List<PowerUpEntity> powerUps;

    public GameContext(Player player, Background bg1, Background bg2, LifeManager lm, List<Enemy> enemies, List<Boss> bosses, List<Projectile> projectiles, List<Projectile> enemyProjectiles, List<PowerUpEntity> powerUps) {
        this.player = player;
        this.background1 = bg1;
        this.background2 = bg2;
        this.lifeManager = lm;
        this.enemies = enemies;
        this.bosses = bosses;
        this.projectiles = projectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.powerUps = powerUps;
    }
}