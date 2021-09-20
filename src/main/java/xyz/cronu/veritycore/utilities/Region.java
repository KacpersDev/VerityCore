package xyz.cronu.veritycore.utilities;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Region {

	public static boolean isBlockInMine(Block block) {
		for (ProtectedRegion region : getRegions(block.getLocation())) {
			if (region.getId().toLowerCase().contains("mine"))
				return true;
		}
		return false;
	}


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

	public static void setBlocks(Location pos1, Location pos2, World world, HashMap<String, Float> blocks){
		CuboidRegion region = new CuboidRegion(BlockVector3.at(pos1.getX(), pos1.getY(), pos1.getZ()), BlockVector3.at(pos2.getX(), pos2.getY(), pos2.getZ()));
		EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
		System.out.println("test");
		float rnd;
		float chance;
		for (BlockVector3 blockVector3 : region) {
			rnd = (float) java.lang.Math.random();
			chance = 0;
			for (String block : blocks.keySet()) {
				if (rnd > chance && rnd <= chance + blocks.get(block)) {
					editSession.setBlock(blockVector3.getX(), blockVector3.getY(), blockVector3.getZ(), BlockType.REGISTRY.get(block.toLowerCase()));
					break;
				}
				chance += blocks.get(block);
			}
		}
		editSession.flushQueue();
	}

	public static ApplicableRegionSet getRegions(Location loc) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regionManager = container.get(BukkitAdapter.adapt(Objects.requireNonNull(loc.getWorld())));
		BlockVector3 blockLoc = BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
		assert regionManager != null;
		ApplicableRegionSet regionSet = regionManager.getApplicableRegions(blockLoc);
		return regionSet;
	}

	public static void breakBlocks(Player player, World world, ArrayList<Block> blocks) throws MaxChangedBlocksException {
		EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
		for (Block block : blocks) {
			editSession.setBlock(BlockVector3.at(block.getX(), block.getY(), block.getZ()), (Pattern) BlockTypes.AIR);
		}
		editSession.flushQueue();
	}

	public void addBlocks(World world, ArrayList<Block> blocks) throws MaxChangedBlocksException {
		EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world.getName())).fastmode(true).build();
		for (Block block : blocks) {
			editSession.setBlock(BlockVector3.at(block.getX(), block.getY(), block.getZ()), (Pattern) BlockTypes.COAL_ORE);
		}
		editSession.flushSession();
	}

	public static List<Block> getBlocksInRadius(Location location, int radius) {
		List<Block> blocks = new ArrayList<>();
		for (int x = -radius; x < radius; x++) {
			for (int y = -radius; y < radius; y++) {
				for (int z = -radius; z < radius; z++) {
					if (location.getWorld() != null) {
						final Block block = location.getWorld().getBlockAt(x + location.getBlockX(), location.getBlockY() + 1, z + location.getBlockZ());
						if (block.getType() == Material.AIR) continue;
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

}
