package xyz.cronu.veritycore.consumables;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.util.ArrayList;
import java.util.List;

public class Consumables {

	public static ItemStack getTokenItem(double amount){

		List<String> lore = new ArrayList<>();
		lore.add(Text.color("&7Right-Click while holding to"));
		lore.add(Text.color("&7redeem these tokens."));
		lore.add("");
		lore.add(Text.color("&7Amount: &e" + Number.pretty(amount)));

		ItemStack tokenItem = new ItemBuilder()
				.setName("&e&lToken Item")
				.setMaterial(Material.SUNFLOWER)
				.setLore(lore)
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.create();

		PD.setItemDataString(tokenItem, new NamespacedKey(VerityCore.getPlugin(), "CONSUMABLE"), "TRUE");
		PD.setItemDataDouble(tokenItem, new NamespacedKey(VerityCore.getPlugin(), "TOKEN_ITEM"), amount);

		return tokenItem;
	}

	public static ConsumableType getConsumableType(ItemStack item){
		ConsumableType type = null;
		if(isTokenItem(item)) type = ConsumableType.TOKEN;
		return type;
	}

	public static double getTokenItemAmount(ItemStack item){
		if(!isTokenItem(item)) return -1;
		return PD.getItemDataDouble(item, new NamespacedKey(VerityCore.getPlugin(), "TOKEN_ITEM"));
	}

	public static boolean isTokenItem(ItemStack item){
		return PD.itemDataContainsKey(item, new NamespacedKey(VerityCore.getPlugin(), "TOKEN_ITEM"));
	}

	public static boolean isConsumable(ItemStack item){
		return PD.itemDataContainsKey(item, new NamespacedKey(VerityCore.getPlugin(), "CONSUMABLE"));
	}

}
