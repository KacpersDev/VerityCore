package xyz.cronu.veritycore.autosell;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.pickaxe.enchants.Fortune;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.util.HashMap;
import java.util.UUID;

public class AutosellManager {

	private static HashMap<UUID, Double> moneyAmount = new HashMap<>();
	private static HashMap<UUID, Double> blocksMined = new HashMap<>();

	public static void broadCast(Player p) {

		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(p.getUniqueId());
		if (user == null) return;
		double moneyEarned = moneyAmount.getOrDefault(p.getUniqueId(), 0D);
		double blocksEarned = blocksMined.getOrDefault(p.getUniqueId(), 0D);

		int fortuneLevel = PickaxeManager.getEnchantLevel(p.getInventory().getItem(0), new Fortune());
		moneyEarned = moneyEarned * fortuneLevel;
		blocksEarned = blocksEarned + fortuneLevel;

		if (!(moneyEarned > 0 && blocksEarned > 0)) return;
		ItemStack moneyPet = PetManager.getMoneyPet(p);
		if (moneyPet != null && PetManager.getPetLevel(moneyPet) > 1)
			moneyEarned = moneyEarned * PetManager.getPetLevel(moneyPet);

		user.addMoney(moneyEarned);
		Text.message(p, "&7You've sold &e" + Number.pretty(blocksEarned) + "&7 blocks for &e$" + Number.pretty(moneyEarned));

		blocksMined.clear();
		moneyAmount.clear();
	}

	public static void addMoneyEarned(Player player, double amount) {
		double moneyEarned = moneyAmount.getOrDefault(player.getUniqueId(), 0D);
		moneyEarned += amount;
		moneyAmount.put(player.getUniqueId(), moneyEarned);
	}

	public static void addItems(Player player, long amount) {
		double itemsMined = blocksMined.getOrDefault(player.getUniqueId(), 0D);
		itemsMined += amount;
		blocksMined.put(player.getUniqueId(), itemsMined);
	}

}
