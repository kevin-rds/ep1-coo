package entity.enemy;

import entity.Entity;
import entity.projectiles.EnemyProjectile;
import entity.projectiles.Projectile;
import game.context.GameContext;
import lib.GameLib;
import util.State;

import java.awt.Color;
import java.util.List;

public class Enemy1 extends Enemy {

    private long nextShoot; // instantes do próximo tiro

    public Enemy1(long currentTime) {
        super(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0, 9.0);
        this.velocity = 0.20 + Math.random() * 0.15;
        this.angle = (3 * Math.PI) / 2;
        this.rotationVelocity = 0.0;
        this.nextShoot = currentTime + 500;
    }

    @Override
    public boolean isOffScreen() {
        /* verificando se inimigo saiu da tela */
        return y > GameLib.HEIGHT + 10;
    }

    @Override
    public void move(long delta) {
        x += velocity * Math.cos(angle) * delta;
        y += velocity * Math.sin(angle) * delta * (-1.0);
        angle += rotationVelocity * delta;
    }

    @Override
    public void tryShoot(long currentTime, List<Projectile> projectiles, Entity targetEntity) {
        if (currentTime > nextShoot && y < targetEntity.getY()) {
            double vx = Math.cos(angle) * 0.45;
            double vy = Math.sin(angle) * 0.45 * (-1.0);

            projectiles.add(new EnemyProjectile(x, y, vx, vy, Color.RED));
            nextShoot = currentTime + 200 + (long) (Math.random() * 500);
        }
    }

    @Override
    public void render(GameContext context) {
        if (state == State.EXPLODING) {
            explosion.render(context);
        }

        if (state == State.ACTIVE) {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(x, y, radius);
        }
    }
}
