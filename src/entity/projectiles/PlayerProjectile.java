package entity.projectiles;

import lib.GameLib;

import java.awt.Color;

public class PlayerProjectile extends Projectile {

    public PlayerProjectile(double x, double y, double vx, double vy, Color color) {
        super(x, y, vx, vy, color);
    }

    @Override
    public void doRender() {
        GameLib.drawLine(x, y - 5, x, y + 5);
        GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
        GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
    }

    @Override
    protected boolean isOffScreen() {
        return y < 0;
    }
}
