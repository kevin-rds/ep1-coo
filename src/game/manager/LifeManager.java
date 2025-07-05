package game.manager;

import lib.GameLib;
import java.awt.Color;

/**
 * Gerencia o sistema de vidas do jogador.
 * Controla a contagem de vidas e a condição de "Game Over"
 */
public class LifeManager {
    private final int initialLives;
    private int currentLives;

    public LifeManager(int initialLives) {
        this.initialLives = initialLives;
        this.currentLives = initialLives;
    }

    public void loselife(){
        if (currentLives > 0){
            currentLives--;
        }
    }

    public int getLives(){
        return currentLives;
    }

    public boolean isGameOver(){
        return currentLives == 0;
    }

    public void reset(){
        this.currentLives = this.initialLives;
    }

    public void drawPlayerLives() {
        GameLib.setColor(Color.RED);
        double iconSize = 8.0;
        double padding = 10.0;

        for (int i = 0; i < this.currentLives; i++) {
            double x = GameLib.WIDTH - (padding + iconSize * 2) * (i + 1);
            double y = GameLib.HEIGHT - (padding + iconSize * 2);
            GameLib.drawPlayer(x, y, iconSize);
        }
    }

}
