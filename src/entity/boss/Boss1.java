package entity.boss;

import lib.GameLib;
import util.State;

import java.awt.*;


public class Boss1 extends Boss {

    private final int numShields = 15;
    private final double shieldDistance = 150;
    private final double shieldRadius = 15;

    public Boss1(int health, double x, double y) {
        super(x, y, 15);
        this.rotationVelocity = 0.0015;
        this.vx = 0.05;
        this.vy = 0.05;

        this.maxHealth = health;
        this.currentHealth = health;

        createShieldRing();
    }

    protected void createShieldRing() {
        double angleStep = 2 * Math.PI / numShields;
        for (int i = 0; i < numShields; i++) {
            double shieldAngle = angle + i * angleStep;
            double sx = x + Math.cos(shieldAngle) * shieldDistance;
            double sy = y + Math.sin(shieldAngle) * shieldDistance;
            shields.add(new ShieldSegment(sx, sy, shieldRadius, shieldAngle));
        }
    }

    @Override
    public void move(long delta, long currentTime) {
        double verticalMovement = Math.sin(currentTime / 500.0) * 0.1;
        y += verticalMovement;
        angle += rotationVelocity * delta;

        for (ShieldSegment s : shields) {
            double shieldAngle = angle + s.getBaseAngle();
            double sx = getX() + Math.cos(shieldAngle) * shieldDistance;
            double sy = getY() + Math.sin(shieldAngle) * shieldDistance;
            s.setPosition(sx, sy);
        }
    }

    @Override
    public void render(long currentTime) {
        if (state == State.EXPLODING) {
            explosion.render(currentTime);
            return;
        }

        if (state != State.ACTIVE) return;

        GameLib.setColor(Color.RED);
        GameLib.drawDiamond(x, y, radius);

        for (ShieldSegment s : shields) {
            s.render(currentTime);
        }

        renderHealthBar(x, y, radius);
    }

    public void takeDamage(int amount, long currentTime) {
        if (state != State.ACTIVE) return;

        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
            delayedExplode(currentTime);
        }
    }
}
