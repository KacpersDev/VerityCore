package xyz.cronu.veritycore.pickaxe.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.utilities.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fortune extends Enchant {
	@Override
	public String getEnchantName() {
		return "&7Fortune";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(
				Text.color("&7Chance to earn extra blocks"),
				Text.color("&7for each block that you mine.")
		));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "FORTUNE";
	}

	@Override
	public int getStartLevel() {
		return 10;
	}

	@Override
	public int getMaxLevel() {
		return 1000;
	}

	@Override
	public int getAdminMaxLevel() {
		return Integer.MAX_VALUE;
	}

	@Override
	public double getBaseCost() {
		return 100;
	}


	@Override
	public void blockBreakEvent(BlockBreakEvent e) {

	}
}
