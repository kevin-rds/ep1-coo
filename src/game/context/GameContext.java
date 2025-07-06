package game.context;

import entity.Entity;
import entity.Player;
import entity.PowerUpEntity;
import entity.boss.Boss;
import entity.enemy.Enemy;
import entity.projectiles.Projectile;
import game.manager.LifeManager;
import graphics.Background;

import java.util.List;

public class GameContext {

    private final Player player;
    private final Background background1;
    private final Background background2;
    private final LifeManager lifeManager;
    private final List<Enemy> enemies;
    private final List<Boss> bosses;
    private final List<Projectile> projectiles;
    private final List<Projectile> enemyProjectiles;
    private final List<PowerUpEntity> powerUps;
    private final List<Entity> allEntities;
    private final long delta;
    private final long currentTime;

    public GameContext(
            Player player,
            Background bg1,
            Background bg2,
            LifeManager lm,
            List<Enemy> enemies,
            List<Boss> bosses,
            List<Projectile> projectiles,
            List<Projectile> enemyProjectiles,
            List<PowerUpEntity> powerUps,
            List<Entity> allEntities,
            long currentTime,
            long delta
    ) {
        this.player = player;
        this.background1 = bg1;
        this.background2 = bg2;
        this.lifeManager = lm;
        this.enemies = enemies;
        this.bosses = bosses;
        this.projectiles = projectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.powerUps = powerUps;
        this.allEntities = allEntities;
        this.currentTime = currentTime;
        this.delta = delta;
    }

    public Player getPlayer() {
        return player;
    }

    public Background getBackground1() {
        return background1;
    }

    public Background getBackground2() {
        return background2;
    }

    public LifeManager getLifeManager() {
        return lifeManager;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Projectile> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public List<PowerUpEntity> getPowerUps() {
        return powerUps;
    }
    public List<Entity> getAllEntities() {
        return allEntities;
    }

    public long getDelta() {
        return delta;
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
