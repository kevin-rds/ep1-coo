package entity.projectiles;

import lib.GameLib;

import java.awt.*;

public class EnemyProjectile extends Projectile {

    public EnemyProjectile(double x, double y, double vx, double vy, Color color) {
        super(x, y, vx, vy, color);
    }

    @Override
    public void doRender() {
        GameLib.drawCircle(x, y, radius);
    }

    @Override
    protected boolean isOffScreen() {
        return y > GameLib.HEIGHT;
    }
}
