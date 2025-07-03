package entity;

import lib.GameLib;
import util.State;

import java.awt.Color;
import java.util.List;

public class Player extends Entity {
    private double vx = 0.25;
    private double vy = 0.25;
    private long nextShotTime = 0;
    private long explosionStart;
    private long explosionEnd;

    public Player(double x, double y) {
        super(x, y, 12.0, State.ACTIVE);
    }

    public void update(long delta, long currentTime, List<Projectile> projectiles) {
        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if (state == State.EXPLODING && currentTime > explosionEnd) {
            state = State.ACTIVE;
        }

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/
        if (state == State.ACTIVE) {
            if (GameLib.iskeyPressed(GameLib.KEY_UP)) y -= delta * vy;
            if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) y += delta * vy;
            if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) x -= delta * vx;
            if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) x += delta * vx;

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                if (currentTime > nextShotTime) {
                    projectiles.add(new Projectile(x, y - 2 * radius, 0.0, -1.0, Color.GREEN, State.ACTIVE));
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
}
