package xyz.cronu.veritycore.pickaxe.enchants;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.Math;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenator extends Enchant {

	@Override
	public String getEnchantName() {
		return "&6Tokenator";
	}

	@Override
	public List<String> getDescription() {
		return new ArrayList<>(Arrays.asList(Text.color("&7Increase the chance of")
				, Text.color("&7finding tokens while mining.")));
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(VerityCore.getPlugin(), getRawName());
	}

	@Override
	public String getRawName() {
		return "TOKENATOR";
	}

	@Override
	public int getStartLevel() {
		return 0;
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
		return 234D;
	}

	@Override
	public void blockBreakEvent(BlockBreakEvent e) throws IOException, DataException {
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		if (itemInHand.getType() != Material.DIAMOND_PICKAXE) return;

		int tokenatorLevel = PickaxeManager.getEnchantLevel(itemInHand, this);
		int magicFindLevel = PickaxeManager.getEnchantLevel(itemInHand, new MagicFind());

		if (!Math.isRandom(0.2D * tokenatorLevel, getMaxLevel())) return; // Checking if the chance happens.
		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());
		user.addTokens(100 * magicFindLevel);
		Text.actionBar(player, "&7You've received &e" + (100 * magicFindLevel) + "&7 Tokens!");

	}

}
