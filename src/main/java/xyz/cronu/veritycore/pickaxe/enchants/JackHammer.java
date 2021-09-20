package xyz.cronu.veritycore.pickaxe.enchants;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.utilities.Math;
import xyz.cronu.veritycore.utilities.RegionUtils;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JackHammer extends Enchant {

	@Override
	public String getEnchantName() {
		return "&4JackHammer";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(
				Text.color("&7Chance to remove a layer"),
				Text.color("&7of the mine while mining.")
		));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "JACKHAMMER";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public int getMaxLevel() {
		return 5000;
	}

	@Override
	public int getAdminMaxLevel() {
		return Integer.MAX_VALUE;
	}

	@Override
	public double getBaseCost() {
		return 2500D;
	}


	@Override
	public void blockBreakEvent(BlockBreakEvent e) throws IOException, DataException {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		if (itemInHand.getType() != Material.DIAMOND_PICKAXE) return;

		int level = PickaxeManager.getEnchantLevel(itemInHand, this);

		if (!Math.isRandom(0.003D * level, getMaxLevel())) return; // Checking if the chance happens.
		List<Block> blocks = RegionUtils.jackhammer(block.getLocation(), 39); // Gets all blocks in the radius
		RegionUtils.breakBlocks(player, block.getWorld(), blocks); // Simulates the JackHammer breaking
	}

}
