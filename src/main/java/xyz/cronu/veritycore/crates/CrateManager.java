package xyz.cronu.veritycore.crates;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CrateManager {

	public static List<Crate> crates = new ArrayList<>();

	public void giveCrateKey(Player player, CrateType crateType, int amount){
		ItemStack keyItem = new ItemBuilder()
				.setName(crateType.getDisplayName() + " &7Key")
				.setMaterial(Material.TRIPWIRE_HOOK)
				.setAmount(amount)
				.setLore(Arrays.asList(
						Text.color("&7Right-Click while holding to open"),
						Text.color("&7and receive rewards!")
				))
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.create();
		PD.setItemDataString(keyItem, new NamespacedKey(VerityCore.getPlugin(), "KEY_ITEM"), crateType.name());
		player.getInventory().addItem(keyItem);
		Text.message(player, Prefix.CRATES.getPrefix() + "&7You've received x" + amount + " " + crateType.getDisplayName() + "&7!");
	}

	public void openKey(Player player, ItemStack keyItem, int amount){
		for(int i = 0; i < amount; i++) {
			keyItem.setAmount(keyItem.getAmount() - 1);
			Crate crate = getCrate(getCrateKeyType(keyItem).name());
			CrateReward reward = getRandomReward(crate);

			if (reward == null) player.sendMessage("Reward was null hmmm");

			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			for (String cmd : reward.getRewardCommands())
				Bukkit.dispatchCommand(console, cmd.replace("%player%", player.getName()));
			Text.message(player, Prefix.CRATES.getPrefix() + reward.getRewardMessage());
		}
	}

	public CrateReward getRandomReward(Crate crate) {
		CrateReward reward = crate.getCrateRewards().get(1);
		for(CrateReward crateReward : crate.getCrateRewards()){
			if(crateReward.getChance() >= new Random().nextInt(100)){
				reward = crateReward;
				break;
			}
		}
		return reward;
	}

	public boolean isValidType(String input){
		boolean value = false;
		for(CrateType type : CrateType.values()){
			if(type.name().equalsIgnoreCase(input)){
				value = true;
				break;
			}
		}
		return value;
	}

	public boolean isCrateKey(ItemStack item){
		return PD.itemDataContainsKey(item, new NamespacedKey(VerityCore.getPlugin(), "KEY_ITEM"));
	}

	public CrateType getCrateKeyType(ItemStack item){
		if(!isCrateKey(item))return null;
		return CrateType.valueOf(PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "KEY_ITEM")).toUpperCase());
	}

	public List<CrateReward> getCrateRewards(String crateType){
		Crate crate = getCrate(crateType);
		if(crate == null) return null;
		return crate.getCrateRewards();
	}

	public Crate getCrate(String crateType){
		if(crates == null || crates.isEmpty()) return null;
		Crate crate = null;
		for(Crate c : crates){
			if(c.getCrateType().equals(CrateType.valueOf(crateType.toUpperCase()))){
				crate = c;
				break;
			}
		}
		return crate;
	}

}
