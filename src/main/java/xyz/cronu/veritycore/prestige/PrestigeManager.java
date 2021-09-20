package xyz.cronu.veritycore.prestige;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.pets.PetType;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

public class PrestigeManager {

	public static void prestige(Player player) {
		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());
		double moneyBalance = user.getMoney();
		long prestige = user.getPrestige();
		double prestigeCost = prestigeCost(prestige);

		if (moneyBalance < prestigeCost) {
			double moneyNeeded = prestigeCost - moneyBalance;
			Text.message(player, Prefix.PRESTIGE.getPrefix() + "&cYou need $" + Number.pretty(moneyNeeded) + " in order to prestige!");
			return;
		}

		long prestigeAmount = 1;
		ItemStack prestigePet = PetManager.getPrestigePet(player);
		if (prestigePet != null) {
			prestigeAmount = PetManager.getPetLevel(prestigePet);
			Text.message(player, Prefix.PRESTIGE.getPrefix() + "&7Your " + PetType.PRESTIGE.getDisplayName() + "&7 has multiplied your prestiges by &c" + PetManager.getPetLevel(prestigePet) + "&7!");
		}

		Text.message(player, Prefix.PRESTIGE.getPrefix() + "&7You've prestiged &c" + prestigeAmount + " &7times for &c$" + Number.pretty(prestigeCost) + "&7!");

		user.addPrestige(prestigeAmount);
		user.takeMoney(prestigeCost);

	}

	public static void prestigeMax(Player player) {
		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());

		long currentPrestige = user.getPrestige();
		double playerBalance = user.getMoney();
		double prestigeCost = prestigeCost(currentPrestige);
		double totalPrestigeCost = prestigeCost;
		long totalTimesPrestiged = 0L;

		if (playerBalance < prestigeCost) {
			double moneyNeeded = prestigeCost - playerBalance;
			Text.message(player, Prefix.PRESTIGE.getPrefix() + "&cYou need $" + Number.pretty(moneyNeeded) + " in order to prestige!");
			return;
		}

		while (playerBalance > totalPrestigeCost) {
			if (playerBalance - (totalPrestigeCost + prestigeCost(currentPrestige + totalTimesPrestiged)) < 0) break;
			totalTimesPrestiged++;
			totalPrestigeCost += prestigeCost(currentPrestige + totalTimesPrestiged);
		}

		ItemStack prestigePet = PetManager.getPrestigePet(player);
		if (prestigePet != null && PetManager.getPetLevel(prestigePet) > 1) {
			totalTimesPrestiged = totalTimesPrestiged * PetManager.getPetLevel(prestigePet);
			if(totalTimesPrestiged == 0) totalTimesPrestiged = PetManager.getPetLevel(prestigePet);
			Text.message(player, Prefix.PRESTIGE.getPrefix() + "&7Your " + PetType.PRESTIGE.getDisplayName() + "&7 has multiplied your prestiges by &c" + PetManager.getPetLevel(prestigePet) + "&7!");
		}

		if(totalTimesPrestiged == 0) totalTimesPrestiged = 1;
		user.takeMoney(totalPrestigeCost);
		user.addPrestige(totalTimesPrestiged);
		Text.message(player, Prefix.PRESTIGE.getPrefix() + "&7You've prestiged a total of &c" + Number.pretty(totalTimesPrestiged) + " &7times for &c$" + Number.pretty(totalPrestigeCost) + "&7!");


	}

	public static double prestigeCost(double prestige) {
		return Math.pow(prestige, 2) * prestige;
	}

}
