package game.manager;

import entity.Entity;
import game.context.GameContext;

public class RenderManager {
    public void render(GameContext context) {
        for (Entity e : context.getAllEntities()) {
            e.render(context);
        }
        context.getLifeManager().drawPlayerLives();
        context.getBackground1().render(context);
        context.getBackground2().render(context);
    }
}
