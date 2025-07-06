package entity;

import game.context.GameContext;
import lib.GameLib;
import powerup.PowerUp;
import util.State;

public class PowerUpEntity extends Entity {

    private final PowerUp powerUp;
    private double dx, dy;
    private final double baseDx = 0.15, baseDy = 0.15;
    private long nextDirectionChange;

    public PowerUpEntity(long currentTime, PowerUp powerUp) {
        super(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, Math.random() * ((float)GameLib.HEIGHT / 2) + 10, 9.0);
        this.powerUp = powerUp;

        double angle = Math.random() * 2 * Math.PI;
        dx = Math.cos(angle) * baseDx;
        dy = Math.sin(angle) * baseDy;

        nextDirectionChange = currentTime + 1000 + (long)(Math.random() * 2000);
    }

    public PowerUpEntity(long currentTime, PowerUp powerUp, double x, double y) {
        super(x, y, 9.0);
        this.powerUp = powerUp;

        double angle = Math.random() * 2 * Math.PI;
        dx = Math.cos(angle) * baseDx;
        dy = Math.sin(angle) * baseDy;

        nextDirectionChange = currentTime + 1000 + (long)(Math.random() * 2000);
    }

    @Override
    public void update(GameContext context) {
        long delta = context.getDelta();
        long currentTime = context.getCurrentTime();

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

    @Override
    public void render(GameContext context) {
        if (state == State.ACTIVE) {
            GameLib.setColor(powerUp.getColor());
            GameLib.drawDiamond(x, y, radius);
        }
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
