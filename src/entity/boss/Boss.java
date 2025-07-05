package entity.boss;

import entity.Entity;
import lib.GameLib;
import util.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Boss extends Entity {
    protected double rotationVelocity; // velocidades de rotação
    protected double vx;
    protected double vy;
    protected long explosionStart; // instantes dos inícios das explosões
    protected long explosionEnd; // instantes dos finais da explosões
    protected List<ShieldSegment> shields = new ArrayList<>();
    protected int maxHealth;
    protected int currentHealth;
    protected double angle = 0;
    protected long nextShoot;
    protected long shootDelay = -1;
    private long delayedExplosionStart = -1;

    public Boss(double x, double y, double radius) {
        super(x, y, radius);
    }

    public void delayedExplode(long currentTime) {
        for (int i = 0; i < shields.size(); i++) {
            shields.get(i).delayedExplode(currentTime + i * 200L);
        }
        delayedExplosionStart = currentTime + shields.size() * 200L;
    }

    public void explode(long currentTime) {
        state = State.EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + 2500;
        delayedExplosionStart = -1;
    }

    public void update(long delta, long currentTime, List<Projectile> projectiles) {
        if (state == State.EXPLODING && currentTime > explosionEnd) {
            setInactive();
        }

        if (delayedExplosionStart != -1) {
            if (currentTime > delayedExplosionStart) explode(currentTime);
            else {
                x += vx * Math.cos(angle * 200) * 5 * delta;
                angle += rotationVelocity * delta;
            }
        }

        if (!isActive()) return;

        for (ShieldSegment s : shields) {
            s.update(currentTime);
        }

        move(delta, currentTime);
        tryShoot(currentTime, projectiles);
        keepOnScreen();
    }

    public abstract void move(long delta, long currentTime);
    public abstract void render(long currentTime);
    private void keepOnScreen() {
        double margin = radius;
        if (x < margin) x = margin;
        if (x > GameLib.WIDTH - margin) x = GameLib.WIDTH - margin;
        if (y < margin) y = margin;
        if (y > GameLib.HEIGHT - margin) y = GameLib.HEIGHT - margin;
    }

    public void takeDamage(int amount, long currentTime) {
        if (state != State.ACTIVE) return;

        currentHealth -= amount;
        if (currentHealth <= 0) {
            currentHealth = 0;
            explode(currentTime);
        }
    }

    public List<ShieldSegment> getShields() {
        return shields;
    }

    protected void renderHealthBar(double x, double y, double radius) {
        int barWidth = 200;
        int barHeight = 10;
        double barX = x;
        double barY = y - radius - 30;
        double healthPercent = (double) currentHealth / maxHealth;
        double healthBarWidth = barWidth * healthPercent;

        GameLib.setColor(Color.GRAY);
        GameLib.fillRect(barX, barY, barWidth, barHeight);

        GameLib.setColor(Color.RED);
        GameLib.fillRect(barX - (double)barWidth/2 + healthBarWidth/2, barY, (int) healthBarWidth, barHeight);
    }

    public void tryShoot(long currentTime, List<Projectile> projectiles) {}
}
