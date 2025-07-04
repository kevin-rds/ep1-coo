package strategy.shooting;

import entity.Player;
import entity.projectiles.Projectile;

import java.util.List;

public interface ShootingStrategy {
    void shoot(Player player, List<Projectile> projectiles);
}
