package entity.enemy;

import entity.Entity;
import entity.projectiles.Projectile;
import game.context.GameContext;
import util.State;

import java.util.List;

public abstract class Enemy extends Entity {
    protected double angle; // ângulos (indicam direção do movimento)
    protected double rotationVelocity; // velocidades de rotação
    protected double velocity; // velocidades

    public Enemy(double x, double y, double radius) {
        super(x, y, radius);
    }

    public void explode(long currentTime) {
        super.explode(currentTime, 500);
    }

    @Override
    public void update(GameContext context) {
        if (state == State.EXPLODING) {
            explosion.update(context);
            if(!explosion.isActive()){
                setInactive();
            }
        }

        if (state == State.ACTIVE) {
            if (isOffScreen()) {
                setInactive();
            } else {
                move(context.getDelta());
                tryShoot(context.getCurrentTime(), context.getEnemyProjectiles(), context.getPlayer());
            }
        }
    }

    public abstract boolean isOffScreen();
    public abstract void move(long delta);
    public abstract void tryShoot(long currentTime, List<Projectile> projectiles, Entity targetEntity);
}
