package entity.enemy;

import entity.Entity;
import entity.projectiles.Projectile;
import util.State;

import java.util.List;

public abstract class Enemy extends Entity {
    protected double angle; // ângulos (indicam direção do movimento)
    protected double rotationVelocity; // velocidades de rotação
    protected double velocity; // velocidades
    protected long explosionStart; // instantes dos inícios das explosões
    protected long explosionEnd; // instantes dos finais da explosões

    public Enemy(double x, double y, double radius) {
        super(x, y, radius);
    }

    public void explode(long currentTime) {
        state = State.EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + 500;
    }

    public void update(long delta, long currentTime, List<Projectile> enemyProjectiles, Entity refEntity) {
        if (state == State.EXPLODING && currentTime > explosionEnd) {
            setInactive();
        }

        if (state == State.ACTIVE) {
            if (isOffScreen()) {
                setInactive();
            } else {
                move(delta);
                tryShoot(currentTime, enemyProjectiles, refEntity);
            }
        }
    }

    public abstract boolean isOffScreen();
    public abstract void move(long delta);
    public abstract void tryShoot(long currentTime, List<Projectile> projectiles, Entity targetEntity);
    public abstract void render(long currentTime);
}
