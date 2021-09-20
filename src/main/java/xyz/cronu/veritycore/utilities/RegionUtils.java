package xyz.cronu.veritycore.utilities;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.autosell.AutosellManager;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegionUtils {

	public static ArrayList<Block> sphere(final Location center, final int radius) {
		ArrayList<Block> sphere = new ArrayList<Block>();
		for (int Y = -radius; Y < radius; Y++)
			for (int X = -radius; X < radius; X++)
				for (int Z = -radius; Z < radius; Z++)
					if (java.lang.Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
						if (center.getWorld() != null) {
							final Block block = center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
							sphere.add(block);
						}
					}
		return sphere;
	}

	public static boolean isBlockInMine(Block block, String mine) {
		for (ProtectedRegion region : getRegions(block.getLocation())) {
			if (region.getId().toLowerCase().contains(mine))
				return true;
		}
		return false;
	}

	public static boolean isPlayerInMine(Player player, String mine) {
		for (ProtectedRegion region : getRegions(player.getLocation())) {
			if (region.getId().toLowerCase().contains(mine))
				return true;
		}
		return false;
	}

	public static ApplicableRegionSet getRegions(Location loc) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regionManager = container.get(BukkitAdapter.adapt(Objects.requireNonNull(loc.getWorld())));
		BlockVector3 blockLoc = BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
		assert regionManager != null;
		ApplicableRegionSet regionSet = regionManager.getApplicableRegions(blockLoc);
		return regionSet;
	}

	public static void breakBlocks(Player player, World world, List<Block> blocks) throws IOException, DataException {
		EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
		Mine mine = new MineManager().getMine(player);
		int amount = 0;
		for (Block block : blocks) {
			if (!mine.getRegion().contains(block.getLocation()) || block.getType() == Material.AIR) continue;
			editSession.setBlock(BlockVector3.at(block.getX(), block.getY(), block.getZ()), (Pattern) BlockTypes.AIR);
			amount++;
		}
		PickaxeManager.addPickaxeBlocksBroken(PickaxeManager.getPickaxe(player), amount);
		AutosellManager.addMoneyEarned(player, mine.getBlocksWorth() * amount);
		AutosellManager.addItems(player, amount);
		editSession.flushQueue();
	}

	public static void addBlocks(World world, ArrayList<Block> blocks, String blockType) throws MaxChangedBlocksException {
		EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
		for (Block block : blocks) {
			editSession.setBlock(BlockVector3.at(block.getX(), block.getY(), block.getZ()), (Pattern) BlockType.REGISTRY.get(blockType.toLowerCase()));
		}
		editSession.flushQueue();
	}

	public static List<Block> blackHole(Location location, int radius) {
		List<Block> blocks = new ArrayList<>();
		for (int x = -radius; x < radius; x++) {
			for (int y = -radius; y < radius; y++) {
				for (int z = -radius; z < radius; z++) {
					if (location.getWorld() != null) {
						final Block block = location.getWorld().getBlockAt(x + location.getBlockX(), y + location.getBlockY(), z + location.getBlockZ());
						if (block.getType() == Material.AIR) continue;
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

		public static List<Block> jackhammer (Location location,int radius){
			List<Block> blocks = new ArrayList<>();
			for (int x = -radius; x < radius; x++) {
				for (int z = -radius; z < radius; z++) {
					if (location.getWorld() != null) {
						final Block block = location.getWorld().getBlockAt(x + location.getBlockX(), location.getBlockY(), z + location.getBlockZ());
						if (block.getType() == Material.AIR) continue;
						blocks.add(block);
					}
				}
			}
			return blocks;
		}
		public static List<Block> explosive (Location location,int radius){
			return sphere(location, radius);
		}

	}
