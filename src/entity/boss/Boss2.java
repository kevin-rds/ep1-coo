package entity.boss;

import entity.Entity;
import entity.projectiles.EnemyProjectile;
import entity.projectiles.Projectile;
import lib.GameLib;
import util.State;

import java.awt.*;
import java.util.List;

public class Boss2 extends Boss {

    public Boss2() {
        super(Math.random() * 20.0 + (float)GameLib.WIDTH / 2, (float)GameLib.HEIGHT / 2, 15);
        this.rotationVelocity = 0.0015;
        this.vx = 0.15;
        this.vy = 0.00;
        this.shootDelay = 33; // ms

        this.maxHealth = 100;
        this.currentHealth = 100;
    }

    protected void draw(double x, double y, double outerRadius, double innerRadius, int points) {
        double angleStep = Math.PI / points;
        double[] xPoints = new double[points * 2];
        double[] yPoints = new double[points * 2];

        for (int i = 0; i < points * 2; i++) {
            double angle = i * angleStep - Math.PI / 2;
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;

            xPoints[i] = x + Math.cos(angle) * radius;
            yPoints[i] = y + Math.sin(angle) * radius;
        }

        for (int i = 0; i < xPoints.length; i++) {
            int j = (i + 1) % xPoints.length;
            GameLib.drawLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]); // usa o próprio método
        }
    }

    @Override
    public void move(long delta, long currentTime) {
        x += vx * delta;
        double verticalMovement = Math.sin(currentTime / 500.0) * 0.1;
        y += verticalMovement;

        if (x <= radius + 100 || x >= GameLib.WIDTH - radius - 100) {
            vx *= -1;
            x = Math.max(radius + 100, Math.min(x, GameLib.WIDTH - radius - 100));
        }

        // Rotação
        angle += rotationVelocity * delta;
    }

    @Override
    public void render(long currentTime) {
        if (state == State.EXPLODING) {
            explosion.render(currentTime);
            return;
        }

        if (state != State.ACTIVE) return;

        GameLib.setColor(Color.RED);
        draw(x, y, radius * 1.5, radius, 15);

        renderHealthBar(x, y, radius);
    }

    public void tryShoot(long currentTime, List<Projectile> projectiles) {
        if (shootDelay != -1 && nextShoot == 0) nextShoot = currentTime + shootDelay;

        if (currentTime > nextShoot) {
            double vx = Math.cos(angle) * 0.60;
            double vy = Math.sin(angle) * 0.60;
            projectiles.add(new EnemyProjectile(x, y, vx, vy, Color.RED));
            nextShoot = currentTime + shootDelay;
        }
    }
}
