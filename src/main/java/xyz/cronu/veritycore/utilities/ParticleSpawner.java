package xyz.cronu.veritycore.utilities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleSpawner {

	public static void spawn(Player player, Particle.DustOptions dustOptions) {
		float radius = 1.5f;
		float angle = 0f;
			while (angle < 10f) {
				Location location = player.getLocation().add(0, 1, 0);
				double x = (radius * java.lang.Math.sin(angle));
				double z = (radius * java.lang.Math.cos(angle));
				player.spawnParticle(Particle.REDSTONE, location.getX() + x, location.getY(), location.getZ() + z, 0, 0, 1, 0, dustOptions);
				angle += 0.1;
			}
	}

}
