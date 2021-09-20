package xyz.cronu.veritycore.pickaxe.enchants;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.crates.CrateManager;
import xyz.cronu.veritycore.crates.CrateType;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.utilities.Math;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class KeyFinder extends Enchant {

	@Override
	public String getEnchantName() {
		return "&eKey Finder";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(Text.color("&7Increase the speed that")
				, Text.color("&7blocks break while mining.")));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "KEY_FINDER";
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
		return 234D;
	}

	@Override
	public void blockBreakEvent(BlockBreakEvent e) throws IOException, DataException {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		if (itemInHand.getType() != Material.DIAMOND_PICKAXE) return;

		int level = PickaxeManager.getEnchantLevel(itemInHand, this);
		int keySmithLevel = PickaxeManager.getEnchantLevel(itemInHand, new KeySmith());
		if(keySmithLevel == 0) keySmithLevel = 1;
		int keysGiven = ThreadLocalRandom.current().nextInt(keySmithLevel);
		if(keysGiven == 0) keysGiven = 1;

		if (!Math.isRandom(0.05D * level, getMaxLevel())) return; // Checking if the chance happens.
		int random = ThreadLocalRandom.current().nextInt(100);

		if(level <= 1000){
			new CrateManager().giveCrateKey(player, CrateType.SHADOW, keysGiven);
		} else if(level > 1001 && level <= 2000){
			if(random <= 50) new CrateManager().giveCrateKey(player, CrateType.SHADOW, keysGiven);
			if(random <= 25) new CrateManager().giveCrateKey(player, CrateType.RADIANT, keysGiven);
		} else if(level > 2001 && level <= 3000){
			if(random <= 50) new CrateManager().giveCrateKey(player, CrateType.SHADOW, keysGiven);
			if(random <= 30) new CrateManager().giveCrateKey(player, CrateType.RADIANT, keysGiven);
		} else if(level > 3001 && level <= 4000){
			if(random <= 50) new CrateManager().giveCrateKey(player, CrateType.SHADOW, keysGiven);
			if(random <= 30) new CrateManager().giveCrateKey(player, CrateType.RADIANT, keysGiven);
			if(random <= 20) new CrateManager().giveCrateKey(player, CrateType.STELLAR, keysGiven);
		} else if(level > 4001 && level <= 5000){
			if(random <= 50) new CrateManager().giveCrateKey(player, CrateType.SHADOW, keysGiven);
			if(random <= 30) new CrateManager().giveCrateKey(player, CrateType.RADIANT, keysGiven);
			if(random <= 20) new CrateManager().giveCrateKey(player, CrateType.STELLAR, keysGiven);
			if(random <= 10) new CrateManager().giveCrateKey(player, CrateType.CRYSTAL, keysGiven);
		}

	}

}
