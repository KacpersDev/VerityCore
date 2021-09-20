package xyz.cronu.veritycore.menu.menus;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.pickaxe.Enchant;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EnchantMenu extends Menu {
	public EnchantMenu(Player player) {
		super(player);
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
	public String getTitle() {
		return "Enchant Menu";
	}

	@Override
	public void setItems() {
		AtomicInteger index = new AtomicInteger(27);
		VerityCore.getEnchantments().forEach(enchant -> {
			int level = PickaxeManager.getEnchantLevel(player.getInventory().getItem(0), enchant);

			List<String> lore = enchant.getDescription();

			lore.add("");
			lore.add(Text.color("&c&lLEVEL: &7" + level));
			lore.add(Text.color("&c&lMAX: &7" + enchant.getMaxLevel()));
			lore.add(Text.color(""));
			lore.add(Text.color("&c&lLEFT CLICK: &7+1 &o(" + Number.pretty(PickaxeManager.getEnchantmentCost(enchant, level + 1)) + "&7)"));
			lore.add(Text.color("&c&lRIGHT CLICK: &7+10 &o(" + Number.pretty(PickaxeManager.getEnchantmentCost(enchant, level + 10) * 10) + "&7)"));
			lore.add(Text.color("&c&lSHIFT CLICK: &7+100 &o(" + Number.pretty(PickaxeManager.getEnchantmentCost(enchant, level + 100) * 100) + "&7)"));
			lore.add(Text.color("&c&lMIDDLE CLICK: &7+1000 &o(" + Number.pretty(PickaxeManager.getEnchantmentCost(enchant, level + 1000) * 1000) + "&7)"));

			ItemStack enchantBook = new ItemBuilder().
					setMaterial(Material.ENCHANTED_BOOK)
					.setAmount(1)
					.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
					.setName(Text.color(enchant.getEnchantName()))
					.setLore(lore)
					.hideFlags()
					.create();

			PD.setItemDataString(enchantBook, new NamespacedKey(VerityCore.getPlugin(), "ENCHANT"), enchant.getRawName());
			getInventory().setItem(index.get(), enchantBook);
			index.getAndIncrement();
		});
	}

	@Override
	public void handle(InventoryClickEvent e) {
		ItemStack item = e.getClickedInventory().getItem(e.getSlot());
		Player player = (Player) e.getWhoClicked();
		if (item == null) return;


		double cost = 0D;
		Enchant enchant = PickaxeManager.getEnchant(PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "ENCHANT")));

		if (e.getClick() == ClickType.LEFT) cost = PickaxeManager.upgradeEnchantment(player, enchant, 1);
		if (e.getClick() == ClickType.RIGHT) cost = PickaxeManager.upgradeEnchantment(player, enchant, 10);
		if (e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.SHIFT_LEFT)
			cost = PickaxeManager.upgradeEnchantment(player, enchant, 100);
		if(e.getClick() == ClickType.MIDDLE) cost = PickaxeManager.upgradeEnchantment(player, enchant, 1000);

		if (cost > 0) Text.message(player, Prefix.PICKAXE.getPrefix() + "&c" + Number.pretty(cost) + "&7 tokens have been deducted from your balance.");

		setItems();
	}
}
