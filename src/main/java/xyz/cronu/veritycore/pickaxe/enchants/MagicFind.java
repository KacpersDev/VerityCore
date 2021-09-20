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

public class MagicFind extends Enchant {


	@Override
	public String getEnchantName() {
		return "&bMagic Find";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(Text.color("&7Increase the amount of")
				, Text.color("&7tokens you receive from")
				, Text.color("&7Tokenator while mining.")));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "MAGICFIND";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public int getMaxLevel() {
		return 1000000;
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
