package xyz.cronu.veritycore.pickaxe;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.autosell.AutosellManager;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PickaxeManager {

	public static void simulateBlockBreak(ItemStack pickaxe, Player player, Mine mine, PrisonPlayer prisonPlayer, BlockBreakEvent e) throws IOException, DataException {
		AutosellManager.addMoneyEarned(player, mine.getBlocksWorth());
		AutosellManager.addItems(player, 1);

		PickaxeManager.addPickaxeExperience(player, pickaxe, 500);

		mine.addBlocksMined(1);
		if (mine.emptyBlocks() <= mine.fullBlocks() / 2) new MineManager().reset(player);

		for (Enchant enchant : VerityCore.getEnchantments()) enchant.blockBreakEvent(e);

		if (PetManager.getBlockPet(player) != null && PetManager.getPetLevel(PetManager.getBlockPet(player)) > 1) {
			prisonPlayer.addBlocksMined(PetManager.getPetLevel(PetManager.getBlockPet(player)));
			PickaxeManager.addPickaxeBlocksBroken(pickaxe, PetManager.getPetLevel(PetManager.getBlockPet(player)));
		} else {
			prisonPlayer.addBlocksMined(1);
			PickaxeManager.addPickaxeBlocksBroken(pickaxe, 1);
		}

	}

	public static double upgradeEnchantment(Player player, Enchant enchant, int amount) {
		int maxLevel = enchant.getMaxLevel();
		int currentLevel = getEnchantLevel(player.getInventory().getItem(0), enchant);
		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());

		if (currentLevel == maxLevel) return -1;
		if (currentLevel + amount > maxLevel) amount = maxLevel - currentLevel;
		double cost = getEnchantmentCost(enchant, currentLevel + amount) * amount;
		if (user.getTokens() < cost) return -1;

		user.takeTokens(cost);
		setEnchantLevel(player.getInventory().getItem(0), enchant, currentLevel + amount);
		return cost;
	}

	public static void updatePickaxe(Player player) {
		ItemStack pickaxeItem = getPickaxe(player);
		ItemMeta meta = pickaxeItem.getItemMeta();
		assert meta != null;
		List<String> lore = getPickaxeLore(pickaxeItem);
		meta.setLore(lore);
		pickaxeItem.setItemMeta(meta);
		player.getInventory().setItem(0, pickaxeItem);
	}

	// ------------------ TAKERS ---------------------------------------------

	public static void takeEnchantLevel(ItemStack pickaxeItem, Enchant enchant, int level) {
		if (getEnchantLevel(pickaxeItem, enchant) - level < 0) level = getEnchantLevel(pickaxeItem, enchant);
		setEnchantLevel(pickaxeItem, enchant, getEnchantLevel(pickaxeItem, enchant) - level);
	}

	public static void takePickaxeLevel(ItemStack pickaxeItem, int level) {
		if (getPickaxeLevel(pickaxeItem) - level < 0) level = getPickaxeLevel(pickaxeItem);
		setPickaxeLevel(pickaxeItem, getPickaxeLevel(pickaxeItem) - level);
	}

	public static void takePickaxeBlocksBroken(ItemStack pickaxeItem, long blocksBroken) {
		if (getPickaxeBlocksBroken(pickaxeItem) - blocksBroken < 0) blocksBroken = getPickaxeBlocksBroken(pickaxeItem);
		setPickaxeBlocksBroken(pickaxeItem, getPickaxeBlocksBroken(pickaxeItem) - blocksBroken);
	}

	public static void takePickaxeExperience(ItemStack pickaxeItem, double experience) {
		if (getPickaxeExperience(pickaxeItem) - experience <= 0) experience = getPickaxeExperience(pickaxeItem);
		setPickaxeExperience(pickaxeItem, getPickaxeExperience(pickaxeItem) - experience);
	}

	// ------------------ ADDERS ---------------------------------------------

	public static void addEnchantLevel(ItemStack pickaxeItem, Enchant enchant, int level) {
		setEnchantLevel(pickaxeItem, enchant, getEnchantLevel(pickaxeItem, enchant) + level);
	}

	public static void addPickaxeLevel(ItemStack pickaxeItem, int level) {
		setPickaxeLevel(pickaxeItem, getPickaxeLevel(pickaxeItem) + level);
	}

	public static void addPickaxeBlocksBroken(ItemStack pickaxeItem, long blocksBroken) {
		setPickaxeBlocksBroken(pickaxeItem, getPickaxeBlocksBroken(pickaxeItem) + blocksBroken);
	}

	public static void addPickaxeExperience(Player player, ItemStack pickaxeItem, double experience) {
		if(getPickaxeExperience(pickaxeItem) >= getLevelUpCost(pickaxeItem)){
			addPickaxeLevel(pickaxeItem, 1);
			setPickaxeExperience(pickaxeItem, 0);
			Text.message(player, Prefix.PICKAXE.getPrefix() + "&7Your Pickaxe Has Leveled Up To Level &a" + getPickaxeLevel(pickaxeItem) + "&7!");
		} else {
			setPickaxeExperience(pickaxeItem, getPickaxeExperience(pickaxeItem) + experience);
		}
	}

	// ------------------ SETTERS ---------------------------------------------

	public static void setPickaxeExperience(ItemStack pickaxeItem, double experience) {
		PD.setItemDataDouble(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_EXPERIENCE"), experience);
	}

	public static void setPickaxeBlocksBroken(ItemStack pickaxeItem, long blocksBroken) {
		PD.setItemDataLong(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_BLOCKS_BROKEN"), blocksBroken);
	}

	public static void setPickaxeLevel(ItemStack pickaxeItem, int level) {
		PD.setItemDataInteger(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_LEVEL"), level);
	}

	public static void setEnchantLevel(ItemStack pickaxeItem, Enchant enchant, int level) {
		PD.setItemDataInteger(pickaxeItem, enchant.getKey(), level);
	}

	// ------------------ GETTERS ---------------------------------------------

	public static double getLevelUpCost(ItemStack item){
		return Math.pow(getPickaxeLevel(item), 3);
	}

	public static Enchant getEnchant(String input) {
		Enchant ench = null;
		for (Enchant enchant : VerityCore.getEnchantments()) {
			if (enchant.getRawName().equalsIgnoreCase(input)) {
				ench = enchant;
				break;
			}
		}
		return ench;
	}

	public static ItemStack getPickaxe(Player player) {
		return player.getInventory().getItem(0);
	}

	public static List<String> getPickaxeLore(ItemStack pickaxeItem) {
		List<String> lore = new ArrayList<>();

		lore.add("");
		lore.add(Text.color("&d&lPROGRESSION"));
		lore.add("");
		lore.add(Text.color("&7Blocks Mined: &b" + Number.formatted(getPickaxeBlocksBroken(pickaxeItem))));
		lore.add(Text.color("&7Level: &b" + Number.formatted(getPickaxeLevel(pickaxeItem))));
		lore.add(Text.color("&7Progress:"));
		lore.add(Text.color("&7[" + Text.getProgressBar(getPickaxeExperience(pickaxeItem), getLevelUpCost(pickaxeItem), 35, ':', ChatColor.GREEN, ChatColor.GRAY) + "&7]"));
		lore.add("");
		lore.add(Text.color("&d&lENCHANTMENTS"));
		lore.add("");
		for (Enchant enchant : VerityCore.getEnchantments()) {
			if (getEnchantLevel(pickaxeItem, enchant) > 0)
				lore.add(Text.color(enchant.getEnchantName() + " &7" + getEnchantLevel(pickaxeItem, enchant)));
		}
		lore.add("");
		return lore;
	}

	public static double getEnchantmentCost(Enchant enchant, int level) {
		return (enchant.getBaseCost() * level) * 3.5D;
	}

	public static double getPickaxeExperience(ItemStack pickaxeItem) {
		return  PD.getItemDataDouble(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_EXPERIENCE"));
	}

	public static long getPickaxeBlocksBroken(ItemStack pickaxeItem) {
		return PD.getItemDataLong(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_BLOCKS_BROKEN"));
	}

	public static int getPickaxeLevel(ItemStack pickaxeItem) {
		return PD.getItemDataInteger(pickaxeItem, new NamespacedKey(VerityCore.getPlugin(), "PICKAXE_LEVEL"));
	}

	public static int getEnchantLevel(ItemStack pickaxeItem, Enchant enchant) {
		return PD.getItemDataInteger(pickaxeItem, enchant.getKey());
	}

	// NEW PICKAXE GENERATION FUNCTIONALITY

	public static void generateNewPickaxe(Player player) {

		ItemStack pickaxeItem = new ItemBuilder()
				.setMaterial(Material.DIAMOND_PICKAXE)
				.setName("&a&lOld Reliable")
				.setAmount(1)
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.create();

		for (Enchant enchant : VerityCore.getEnchantments()) setEnchantLevel(pickaxeItem, enchant, enchant.getStartLevel());
		ItemMeta meta = pickaxeItem.getItemMeta();
		assert meta != null;
		meta.setLore(generateNewPickaxeLore(pickaxeItem));
		pickaxeItem.setItemMeta(meta);

		setPickaxeExperience(pickaxeItem, 0);
		setPickaxeBlocksBroken(pickaxeItem, 0);
		setPickaxeLevel(pickaxeItem, 1);

		player.getInventory().setItem(0, pickaxeItem);

	}

	public static List<String> generateNewPickaxeLore(ItemStack item) {
		List<String> lore = new ArrayList<>();

		lore.add("");
		lore.add(Text.color("&d&lPROGRESSION"));
		lore.add("");
		lore.add(Text.color("&7Blocks Mined: &b0"));
		lore.add(Text.color("&7Level: &b1"));
		lore.add(Text.color("&7Progress:"));
		lore.add(Text.color("&7[" + Text.getProgressBar(0, 100, 35, ':', ChatColor.GREEN, ChatColor.GRAY) + "&7]"));
		lore.add("");
		lore.add(Text.color("&d&lENCHANTMENTS"));
		lore.add("");

		for (Enchant enchant : VerityCore.getEnchantments()) {
			if (enchant.getStartLevel() > 0)
				lore.add(Text.color(enchant.getEnchantName() + " &7" + enchant.getStartLevel()));
		}

		lore.add("");
		return lore;
	}

}
