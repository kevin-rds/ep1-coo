package entity;

import lib.GameLib;
import powerup.PowerUp;
import util.State;

import java.awt.*;

public class PowerUpEntity extends Entity {

    private final PowerUp powerUp;
    private double dx, dy;
    private final double baseDx = 0.15, baseDy = 0.15;
    private long nextDirectionChange;

    public PowerUpEntity(long currentTime, PowerUp powerUp) {
        super(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, Math.random() * (GameLib.HEIGHT / 2) + 10, 9.0);
        this.powerUp = powerUp;

        double angle = Math.random() * 2 * Math.PI;
        dx = Math.cos(angle) * baseDx;
        dy = Math.sin(angle) * baseDy;

        nextDirectionChange = currentTime + 1000 + (long)(Math.random() * 2000);
    }

    public void update(long delta, long currentTime) {
        if (state != State.ACTIVE) return;

        x += delta * dx;
        y += delta * dy;

        if (x < 10.0 || x > GameLib.WIDTH - 10.0) setInactive();
        if (y < 10.0 || y > GameLib.HEIGHT - 10.0) setInactive();

        if (currentTime >= nextDirectionChange) {
            double angle = Math.random() * 2 * Math.PI;
            dx = Math.cos(angle) * baseDx;
            dy = Math.sin(angle) * baseDy;
            nextDirectionChange = currentTime + 1000 + (long)(Math.random() * 2000);
        }
    }

    public void render(long currentTime) {
        if (state == State.ACTIVE) {
            GameLib.setColor(Color.YELLOW);
            GameLib.drawDiamond(x, y, radius);
        }
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
