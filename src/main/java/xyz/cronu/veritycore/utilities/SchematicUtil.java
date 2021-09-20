package xyz.cronu.veritycore.utilities;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class SchematicUtil {

	private SchematicUtil() {
		throw new AssertionError();
	}

	/**
	 * Paste Schematic
	 *
	 * @param file
	 * @param location
	 * @throws IOException
	 */
	public static void paste(File file, Location location) throws IOException, MaxChangedBlocksException {
		final BlockVector3 vector = BlockVector3.at(location.toVector().getX(), location.toVector().getY(), location.toVector().getZ());
		//final EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), Integer.MAX_VALUE);


		if(location.getWorld() != null) {
			final BukkitWorld bukkitWorld = new BukkitWorld(location.getWorld());
			Clipboard schematic = FaweAPI.load(file);
			schematic.paste(bukkitWorld, vector);
		}
	}

}
