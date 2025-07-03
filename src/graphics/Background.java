package graphics;

import lib.GameLib;

import java.awt.Color;

public class Background {
    private double[] x, y; // coordenadas x e y
    private double speed;
    private double count;
    private Color color;

    public Background(int numStars, Color color, double speed) {
        this.x = new double[numStars];
        this.y = new double[numStars];
        this.speed = speed;
        this.count = 0.0;
        this.color = color;

        for (int i = 0; i < numStars; i++) {
            x[i] = Math.random() * GameLib.WIDTH;
            y[i] = Math.random() * GameLib.HEIGHT;
        }
    }

    public void render(long delta) {
        GameLib.setColor(color);
        count += speed * delta;

        for (int i = 0; i < x.length; i++) {
            // TODO ao inves de verificar por color (3 ou 2) criar uma propriedade SIZE
            GameLib.fillRect(x[i], (y[i] + count) % GameLib.HEIGHT, color == Color.GRAY ? 3 : 2, color == Color.GRAY ? 3 : 2);
        }
    }
}

