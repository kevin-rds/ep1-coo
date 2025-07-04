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

    public void deflect() {
        vx *= -0.7;
        vy *= -0.7;

        double angle = Math.atan2(vy, vx);
        double deviation = Math.toRadians(Math.random() * 30 - 15); // entre -15 e 15 graus
        double speed = Math.sqrt(vx * vx + vy * vy);

        angle += deviation;

        vx = Math.cos(angle) * speed;
        vy = Math.sin(angle) * speed;
    }

    public double getVX() {
        return vx;
    }

    public double getVY() {
        return vy;
    }

    protected abstract void doRender();
    protected abstract boolean isOffScreen();
}
