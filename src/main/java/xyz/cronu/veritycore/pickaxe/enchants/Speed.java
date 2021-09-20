package xyz.cronu.veritycore.pickaxe.enchants;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Speed extends Enchant {

	@Override
	public String getEnchantName() {
		return "&fSpeed";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(
				Text.color("&7When your pickaxe is equipped"),
				Text.color("&7you'll receive an increased"),
				Text.color("&7movement speed boost.")
		));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "SPEED";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public int getMaxLevel() {
		return 5;
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

	}

}
