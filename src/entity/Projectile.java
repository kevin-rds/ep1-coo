package entity;

import lib.GameLib;
import util.State;

import java.awt.Color;

public class Projectile extends Entity {
    private double vx, vy; // velocidades no eixo x e y
    private Color color;

    public Projectile(double x, double y, double vx, double vy, Color color) {
        super(x, y, color == Color.RED ? 2.0 : 0.0);
        this.vx = vx;
        this.vy = vy;
        this.color = color;
    }

    public void update(long delta) {
        if (state == State.ACTIVE) {
//            if (color == Color.RED && y > GameLib.HEIGHT) setInactive();
//            else if (color != Color.RED && y < 0) setInactive();

            /* verificando se projétil saiu da tela */
            if (y < 0 || y > GameLib.HEIGHT) setInactive();
            else {
                x += vx * delta;
                y += vy * delta;
            }
        }
    }

    public void render() {
        if (state == State.ACTIVE) {
            GameLib.setColor(color);

            // projetil inimigo vs projetil player...
            // sera se eh bom fazer assim ou criar classes herdadas
            if (color == Color.RED)
                GameLib.drawCircle(x, y, radius);
            else {
                GameLib.drawLine(x, y - 5, x, y + 5);
                GameLib.drawLine(x - 1, y - 3, x - 1, y + 3);
                GameLib.drawLine(x + 1, y - 3, x + 1, y + 3);
            }
        }
    }
}
