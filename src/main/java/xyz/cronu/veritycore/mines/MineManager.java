package xyz.cronu.veritycore.mines;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.utilities.*;

import java.io.IOException;
import java.util.*;

public class MineManager {

	public static List<Mine> mines = new ArrayList<>();

	public Location location = new Location(VerityCore.MINE_WORLD, 0, 55, 0);

	public void createMine(UUID user) throws IOException, DataException, MaxChangedBlocksException {

		Player player = Bukkit.getPlayer(user);

		location.setX(new Mine().getLastLocation() + 500);
		SchematicUtil.paste(VerityCore.MINE_SCHEMATIC, location);

		Bukkit.getScheduler().runTaskLater(VerityCore.getPlugin(), () -> {
			Synchronizer.synchronize(() -> {
				Location next = getNext().clone();
				Location clone = next.clone().add(-19, -5, 0);
				Location right = clone.clone().subtract(0, 0, -80);
				Location left = clone.clone().add(38, -30, 42);

				Cuboid region = new Cuboid(left, right);

				assert player != null;
				Mine mine = new Mine(user, player.getName(), region, next.clone(), new ArrayList<>(), 1, "STONE", 0.1D, 0);
				Mine.setLastLocation((int) next.clone().getX());
				mines.add(mine);
				saveMines();
				try { reset(player); } catch (DataException | IOException e) { e.printStackTrace(); }

			});
		}, 60L);
	}


	public void teleport(Player player) throws DataException, IOException, MaxChangedBlocksException {
		if (player == null) return;
		if (getMine(player) == null) {
			Text.message(player, Prefix.MINE.getPrefix() + "&7You do not have a private mine, do /pm create.");
			return;
		}
		Mine mine = getMine(player);
		player.teleport(mine.getSpawn());
	}

	public Mine getMine(Player player) throws MaxChangedBlocksException {
		if (player == null) return null;
		Mine playerMine = null;
		for (Mine mine : mines) {
			if (mine.getName().equalsIgnoreCase(player.getName())) playerMine = mine;
		}
		return playerMine;
	}

	public boolean hasMine(Player player) {
		return mines.stream().anyMatch(mine -> mine.getOwner().equals(player.getUniqueId()));
	}

	public void reset(Player player) throws DataException, IOException, MaxChangedBlocksException {
		if (mines.isEmpty()) return;
		if (!hasMine(player)) {
			Text.message(player, Prefix.MINE.getPrefix() + "&7Create a Private Mine using &c/pm create&7.");
			return;
		}

		Mine mine = getMine(player);

		String mineBlock = mine.getBlock();

		ArrayList<Block> blocks = new ArrayList<>();
		final Iterator<Block> iterator = mine.getRegion().blockList();
		while (iterator.hasNext()) {
			final Block block = iterator.next();
			blocks.add(block);
		}

		RegionUtils.addBlocks(Objects.requireNonNull(Bukkit.getWorld("mines")), blocks, mineBlock);
		player.teleport(mine.getRegion().getCenter().add(0, 20, 0));
	}

	public void loadMines() {
		Mine.load();
	}

	public static void saveMines() {
		if (!mines.isEmpty()) {
			mines.forEach(Mine::save);
		}
	}

	public Location getNext() {
		final Location nextLocation = location.clone();
		addDistance(500);
		return nextLocation;
	}

	public void addDistance(int distance) {
		this.location = this.location.add(distance, 0, 0);
	}

}
