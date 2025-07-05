package game.manager;

import entity.boss.Boss;
import entity.boss.ShieldSegment;
import entity.enemy.Enemy;
import entity.projectiles.EnemyProjectile;
import entity.projectiles.Projectile;
import entity.PowerUpEntity;
import game.context.GameContext;
import util.State;

import java.awt.*;

public class CollisionManager {
    private final LifeManager lifeManager;
    public CollisionManager(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
    }
    public void checkCollisions(GameContext context, long currentTime) {
        if (context.player.getState() == State.ACTIVE) {
            if (!context.player.isInvincible()) {
                /* colisões player - projeteis (inimigo) */
                for (Projectile p : context.enemyProjectiles) {
                    if (p.isActive() && context.player.collidesWith(p)) {
                        lifeManager.loselife();
                        context.player.explode(currentTime);
                        break;
                    }
                }

                /* colisões player - inimigos */
                for (Enemy e : context.enemies) {
                    if (e.isActive() && context.player.collidesWith(e)) {
                        lifeManager.loselife();
                        context.player.explode(currentTime);
                        break;
                    }
                }

                /* colisões player - bosses */
                for (Boss b : context.bosses) {
                    if (b.isActive() && (context.player.collidesWith(b) || b.getShields().stream().anyMatch(context.player::collidesWith))) {
                        lifeManager.loselife();
                        context.player.explode(currentTime);
                        break;
                    }
                }
            }

            /* colisões player - power ups coletaveis */
            for (PowerUpEntity pw : context.powerUps) {
                if (pw.isActive() && context.player.collidesWith(pw)) {
                    context.player.addPowerUp(pw.getPowerUp());
                    pw.setInactive();
                    break;
                }
            }
        }

        /* colisões projeteis (player) - inimigos */
        for (Projectile p : context.projectiles) {
            for (Enemy e : context.enemies) {
                if (p.isActive() && e.isActive() && p.collidesWith(e)) {
                    e.explode(currentTime);
                    p.setInactive();
                }
            }
        }

        /* colisões projeteis (player) - bosses */
        for (Projectile p : context.projectiles) {
            for (Boss b : context.bosses) {
                if (p.isActive() && b.isActive() && p.collidesWith(b)) {
                    b.takeDamage(5, currentTime);
                    p.setInactive();
                }

                for (ShieldSegment s : b.getShields()) {
                    if (p.isActive() && b.isActive() && p.collidesWith(s)) {
                        p.setInactive();

                        Projectile deflected = new EnemyProjectile(p.getX(), p.getY(), p.getVX(), p.getVY(), Color.RED);
                        deflected.deflect();
                        context.enemyProjectiles.add(deflected);
                    }
                }
            }
        }

        /* colisões projeteis (player) - power ups */
        for (Projectile p : context.projectiles) {
            for (PowerUpEntity pw : context.powerUps) {
                if (p.isActive() && pw.isActive() && p.collidesWith(pw)) {
                    pw.setInactive();
                    context.player.addPowerUp(pw.getPowerUp());
                }
            }
        }
    }
}
