package game.manager;

import entity.Player;
import entity.boss.Boss;
import entity.enemy.Enemy;
import entity.projectiles.Projectile;
import entity.PowerUpEntity;
import game.context.GameContext;
import graphics.Background;

import java.util.List;

public class RenderManager {
    public void render(GameContext context, long currentTime, long delta) {
        context.background2.render(delta);
        context.background1.render(delta);
        for (Projectile p : context.projectiles) {
            p.render();
        }
        for (Projectile p : context.enemyProjectiles) {
            p.render();
        }
        for (Enemy e : context.enemies) {
            e.render(currentTime);
        }
        for (Boss b : context.bosses) {
            b.render(currentTime);
        }
        for (PowerUpEntity p : context.powerUps) {
            p.render(currentTime);
        }
        context.player.render(currentTime);
        context.lifeManager.drawPlayerLives();
    }
}
