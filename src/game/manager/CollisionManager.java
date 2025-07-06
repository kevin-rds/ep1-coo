package game.manager;

import entity.boss.Boss;
import entity.boss.ShieldSegment;
import entity.enemy.Enemy;
import entity.projectiles.EnemyProjectile;
import entity.projectiles.Projectile;
import entity.PowerUpEntity;
import game.context.GameContext;
import util.State;

import java.awt.Color;

public class CollisionManager {
    private final LifeManager lifeManager;
    public CollisionManager(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
    }
    public void checkCollisions(GameContext context) {
        long currentTime = context.getCurrentTime();

        if (context.getPlayer().getState() == State.ACTIVE) {
            if (!context.getPlayer().isInvincible()) {
                /* colisões player - projeteis (inimigo) */
                for (Projectile p : context.getEnemyProjectiles()) {
                    if (p.isActive() && context.getPlayer().collidesWith(p)) {
                        lifeManager.loselife();
                        context.getPlayer().explode(currentTime);
                        break;
                    }
                }

                /* colisões player - inimigos */
                for (Enemy e : context.getEnemies()) {
                    if (e.isActive() && context.getPlayer().collidesWith(e)) {
                        lifeManager.loselife();
                        context.getPlayer().explode(currentTime);
                        break;
                    }
                }

                /* colisões player - bosses */
                for (Boss b : context.getBosses()) {
                    if (b.isActive() && (context.getPlayer().collidesWith(b) || b.getShields().stream().anyMatch(context.getPlayer()::collidesWith))) {
                        lifeManager.loselife();
                        context.getPlayer().explode(currentTime);
                        break;
                    }
                }
            }

            /* colisões player - power ups coletaveis */
            for (PowerUpEntity pw : context.getPowerUps()) {
                if (pw.isActive() && context.getPlayer().collidesWith(pw)) {
                    context.getPlayer().addPowerUp(pw.getPowerUp());
                    pw.setInactive();
                    break;
                }
            }
        }

        /* colisões projeteis (player) - inimigos */
        for (Projectile p : context.getProjectiles()) {
            for (Enemy e : context.getEnemies()) {
                if (p.isActive() && e.isActive() && p.collidesWith(e)) {
                    e.explode(currentTime);
                    p.setInactive();
                }
            }
        }

        /* colisões projeteis (player) - bosses */
        for (Projectile p : context.getProjectiles()) {
            for (Boss b : context.getBosses()) {
                if (p.isActive() && b.isActive() && p.collidesWith(b)) {
                    b.takeDamage(5, currentTime);
                    p.setInactive();
                }

                for (ShieldSegment s : b.getShields()) {
                    if (p.isActive() && b.isActive() && p.collidesWith(s)) {
                        p.setInactive();

                        Projectile deflected = new EnemyProjectile(p.getX(), p.getY(), p.getVX(), p.getVY(), Color.RED);
                        deflected.deflect();
                        context.getEnemyProjectiles().add(deflected);
                    }
                }
            }
        }

        /* colisões projeteis (player) - power ups */
        for (Projectile p : context.getProjectiles()) {
            for (PowerUpEntity pw : context.getPowerUps()) {
                if (p.isActive() && pw.isActive() && p.collidesWith(pw)) {
                    pw.setInactive();
                    context.getPlayer().addPowerUp(pw.getPowerUp());
                }
            }
        }
    }
}
