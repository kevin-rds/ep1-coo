package entity.enemy;

import entity.Entity;
import entity.Projectile;
import lib.GameLib;
import util.State;

import java.awt.Color;
import java.util.List;

public class Enemy2 extends Enemy {
    private static int count = 0; // contagem de inimigos tipo 2 (usada na "formação de voo")
    private static double spawnX = GameLib.WIDTH * 0.20; // coordenada x do próximo inimigo tipo 2 a aparecer

    public Enemy2() {
        super(spawnX, -10.0, 12.0);
        this.velocity = 0.42;
        this.angle = (3 * Math.PI) / 2;
        this.rotationVelocity = 0.0;
        count++;

        if(count > 10){
            count = 0;
            spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
        }
    }

    public static int getCount() {
        return count;
    }

    @Override
    public boolean isOffScreen() {
        /* verificando se inimigo saiu da tela */
        return x < -10 || x > GameLib.WIDTH + 10;
    }

    @Override
    public void move(long delta) {
        double previousY = y;

        x += velocity * Math.cos(angle) * delta;
        y += velocity * Math.sin(angle) * delta * (-1.0);
        angle += rotationVelocity * delta;

        double threshold = GameLib.HEIGHT * 0.30;

        if (previousY < threshold && y >= threshold) {
            if(x < GameLib.WIDTH / 2) rotationVelocity = 0.003;
            else rotationVelocity = -0.003;
        }

        if (rotationVelocity > 0 && Math.abs(angle - 3 * Math.PI) < 0.05) {
            rotationVelocity = 0.0;
            angle = 3 * Math.PI;
        }

        if (rotationVelocity < 0 && Math.abs(angle) < 0.05) {
            rotationVelocity = 0.0;
            angle = 0.0;
        }
    }

    @Override
    public void tryShoot(long currentTime, List<Projectile> projectiles, Entity targetEntity) {
        // TODO ver se precisa do shootNow = true ou rotationVelocity = 0 ja basta, ja que os dois eram setados nas mesmas condicoes
        // Unica possivel diferenca, rotationVelocity inicializa como 0.0 (true), enquanto o outro inicializava como false (false)
        if (rotationVelocity == 0.0) {
            double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
            for (double a : angles) {
                a += Math.random() * Math.PI/6 - Math.PI/12;
                double vx = Math.cos(a) * 0.30;
                double vy = Math.sin(a) * 0.30;
                projectiles.add(new Projectile(x, y, vx, vy, Color.RED));
            }
        }
    }

    @Override
    public void render(long currentTime) {
        if (state == State.EXPLODING) {
            double alpha = (double) (currentTime - explosionStart) /  (explosionEnd - explosionStart);
            GameLib.drawExplosion(x, y, alpha);
        }

        if (state == State.ACTIVE) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(x, y, radius);
        }
    }
}
