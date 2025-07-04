package entity.projectiles;

import entity.Entity;
import lib.GameLib;
import util.State;

import java.awt.Color;

public abstract class Projectile extends Entity {
    private double vx, vy; // velocidades no eixo x e y
    protected Color color;

    public Projectile(double x, double y, double vx, double vy, Color color) {
        super(x, y, color == Color.RED ? 2.0 : 0.0);
        this.vx = vx;
        this.vy = vy;
        this.color = color;
    }

    public void update(long delta) {
        if (state == State.ACTIVE) {
            /* verificando se projétil saiu da tela */
            if (isOffScreen()) setInactive();
            else {
                x += vx * delta;
                y += vy * delta;
            }
        }
    }

    public void render() {
        if (state == State.ACTIVE) {
            GameLib.setColor(color);
            doRender();
        }
    }

    protected abstract void doRender();
    protected abstract boolean isOffScreen();
}
