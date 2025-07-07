package entity;

import game.context.GameContext;
import lib.GameLib;
import powerup.InvincibilityPowerUp;
import powerup.PowerUp;
import powerup.PowerUpManager;
import strategy.shooting.ShootingStrategy;
import strategy.shooting.SingleShotStrategy;
import util.State;

import java.awt.Color;

public class Player extends Entity {
    private double vx = 0.25;
    private double vy = 0.25;
    private long nextShotTime = 0;
    private boolean invincible = false;
    private ShootingStrategy shootingStrategy = new SingleShotStrategy();
    private final PowerUpManager powerUpManager = new PowerUpManager();

    public Player(double x, double y) {
        super(x, y, 12.0);
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    @Override
    public void update(GameContext context) {
        long delta = context.getDelta();
        long currentTime = context.getCurrentTime();

        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if (state == State.EXPLODING) {
            explosion.update(context);
            if(!explosion.isActive()){
                this.state = State.INACTIVE;
            }
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
                    shootingStrategy.shoot(this, context.getProjectiles());
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
        super.explode(currentTime, 2000);
    }

    @Override
    public void render(GameContext context) {
        long currentTime = context.getCurrentTime();

        if (state == State.EXPLODING) { //
            explosion.render(context);
        } else if (state == State.ACTIVE) {
            // Lógica de piscar quando invencível
            if (invincible) {
                // A cada 150ms, o jogador pisca (não é desenhado)
                if ((currentTime / 150) % 2 == 0) {
                    GameLib.setColor(Color.BLUE); //
                    GameLib.drawPlayer(x, y, radius); //
                }
            } else {
                // Desenho normal quando não está invencível
                GameLib.setColor(Color.BLUE); //
                GameLib.drawPlayer(x, y, radius); //
            }
        }
    }

    public void respawn(long currentTime) {
        this.setX(GameLib.WIDTH / 2.0);
        this.setY(GameLib.HEIGHT * 0.90);
        this.setActive();

        this.addPowerUp(new InvincibilityPowerUp(currentTime, 2000));
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

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean value) {
        this.invincible = value;
    }
}