package entity;

import entity.projectiles.Projectile;
import lib.GameLib;
import powerup.PowerUp;
import powerup.PowerUpManager;
import strategy.shooting.ShootingStrategy;
import strategy.shooting.SingleShotStrategy;
import util.State;

import java.awt.*;
import java.util.List;

public class Player extends Entity {
    private double vx = 0.25;
    private double vy = 0.25;
    private long nextShotTime = 0;
    private long explosionStart;
    private long explosionEnd;
    private ShootingStrategy shootingStrategy = new SingleShotStrategy();
    private final PowerUpManager powerUpManager = new PowerUpManager();

    public Player(double x, double y) {
        super(x, y, 12.0);
    }

    public void update(long delta, long currentTime, List<Projectile> projectiles) {
        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if (state == State.EXPLODING && currentTime > explosionEnd) {
            setActive();
        }

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/
        if (state == State.ACTIVE) {
            powerUpManager.update(this, currentTime);

            if (GameLib.iskeyPressed(GameLib.KEY_UP)) y -= delta * vy;
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) y += delta * vy;
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) x -= delta * vx;
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) x += delta * vx;

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (currentTime > nextShotTime) {
                    shootingStrategy.shoot(this, projectiles);
                    nextShotTime = currentTime + 100;
                }
            }

            /* Verificando se coordenadas do player ainda estão dentro */
            /* da tela de jogo após processar entrada do usuário.      */
            if(x < 0.0) x = 0.0;
            if(x >= GameLib.WIDTH) x = GameLib.WIDTH - 1;
            if(y < 25.0) y = 25.0;
            if(y >= GameLib.HEIGHT) y = GameLib.HEIGHT - 1;
        }
    }

    public void explode(long currentTime) {
        state = State.EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + 2000;
    }

    public void render(long currentTime) {
        if (state == State.EXPLODING) {
            double alpha = (double) (currentTime - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(x, y, alpha);
        } else {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(x, y, radius);
        }
    }

    public void setShootingStrategy(ShootingStrategy strategy) {
        this.shootingStrategy = strategy;
    }

    public ShootingStrategy getShootingStrategy() {
        return shootingStrategy;
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUpManager.addPowerUp(powerUp, this);
    }
}
