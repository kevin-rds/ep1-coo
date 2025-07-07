package graphics;

import game.Renderable;
import game.context.GameContext;
import lib.GameLib;

import java.awt.Color;

public class Background implements Renderable {
    private final double[] x, y; // coordenadas x e y
    private final double speed;
    private double count;
    private final Color color;

    private final int size;

    public Background(int numStars, Color color, double speed, int size) {
        this.x = new double[numStars];
        this.y = new double[numStars];
        this.speed = speed;
        this.count = 0.0;
        this.color = color;
        this.size = size;

        for (int i = 0; i < numStars; i++) {
            x[i] = Math.random() * GameLib.WIDTH;
            y[i] = Math.random() * GameLib.HEIGHT;
        }
    }

    @Override
    public void render(GameContext context) {
        GameLib.setColor(color);
        count += speed * context.getDelta();

        for (int i = 0; i < x.length; i++) {
            GameLib.fillRect(x[i], (y[i] + count) % GameLib.HEIGHT, size, size);
        }
    }
}

