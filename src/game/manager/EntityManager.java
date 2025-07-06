package game.manager;

import entity.Entity;
import entity.Player;
import entity.PowerUpEntity;
import entity.boss.Boss;
import entity.enemy.Enemy;
import entity.projectiles.Projectile;
import game.GameMode;
import game.context.GameContext;
import strategy.spawn.EntitySpawner;
import util.State;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Boss> bosses = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Projectile> enemyProjectiles = new ArrayList<>();
    private final List<PowerUpEntity> powerUps = new ArrayList<>();

    private final Player player;


    private EntitySpawner<Enemy> enemySpawner;
    private EntitySpawner<Boss> bossSpawner;
    private EntitySpawner<PowerUpEntity> powerUpSpawner;

    public EntityManager(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() { return enemies; }
    public List<Boss> getBosses() { return bosses; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<Projectile> getEnemyProjectiles() { return enemyProjectiles; }
    public List<PowerUpEntity> getPowerUps() { return powerUps; }
    public List<Entity> getAllEntities() {
        List<Entity> allEntities = new ArrayList<>();
        allEntities.add(player);
        allEntities.addAll(enemies);
        allEntities.addAll(bosses);
        allEntities.addAll(projectiles);
        allEntities.addAll(enemyProjectiles);
        allEntities.addAll(powerUps);
        return allEntities;
    }

    public void updateAll(GameContext context) {
        for (Entity e : getAllEntities()){
            e.update(context);
        }
    }

    public boolean spawnAll(long currentTime, boolean isBossActive, GameMode gameMode) {
        boolean bossSpawnedThisFrame = false;
        if(bossSpawner!=null){
            List<Boss> newBosses = bossSpawner.spawn(currentTime);
            if (!newBosses.isEmpty()) {
                bosses.addAll(newBosses);
                bossSpawnedThisFrame = true;
            }
        }
        powerUps.addAll(powerUpSpawner.spawn(currentTime));

        if (bosses.isEmpty() || "INFINITE".equals(gameMode)) {
            enemies.addAll(enemySpawner.spawn(currentTime));
        } else if ("STORY".equals(gameMode) && !bosses.isEmpty()) {
            enemies.forEach(Enemy::setInactive);
        }

        return bossSpawnedThisFrame;
    }

    public void cleanupAll() {
        projectiles.removeIf(p -> !p.isActive());
        enemyProjectiles.removeIf(p -> !p.isActive());
        enemies.removeIf(e -> e.getState() == State.INACTIVE);
        powerUps.removeIf(p -> p.getState() == State.INACTIVE);
        bosses.removeIf(b -> b.getState() == State.INACTIVE);
    }

    public void setInfiniteModeSpawners(EntitySpawner<Enemy> enemySpawner, EntitySpawner<Boss> bossSpawner) {
        this.enemySpawner = enemySpawner;
        this.bossSpawner = bossSpawner;
    }

    public void setStoryModeSpawners(EntitySpawner<Enemy> enemySpawner, EntitySpawner<Boss> bossSpawner, EntitySpawner<PowerUpEntity> powerUpSpawner) {
        this.enemySpawner = enemySpawner;
        this.bossSpawner = bossSpawner;
        this.powerUpSpawner = powerUpSpawner;
        this.enemies.clear();
        this.bosses.clear();
        this.enemyProjectiles.clear();
        this.projectiles.clear();
    }
}
