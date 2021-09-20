package xyz.cronu.veritycore.menu.menus;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.crates.CrateManager;
import xyz.cronu.veritycore.crates.CrateType;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;

public class CrateSelectorMenu extends Menu {
	public CrateSelectorMenu(Player player) {
		super(player);
	}

	@Override
	public int getSlots() {
		return 27;
	}

	@Override
	public String getTitle() {
		return "&aCrate Options";
	}

	@Override
	public void setItems() throws IOException, DataException {
 		int slot = 10;
 		for(CrateType type : CrateType.values()){

			ItemStack crateType = new ItemBuilder()
					.setLore(Arrays.asList(
							Text.color("&7Click here to view rewards"),
							Text.color("&7from this crate type.")))
					.setMaterial(Material.TRIPWIRE_HOOK)
					.setName(type.getDisplayName())
					.setAmount(1)
					.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
					.hideFlags()
					.create();

			PD.setItemDataString(crateType, new NamespacedKey(VerityCore.getPlugin(), "CRATE_TYPE"), type.name());
			getInventory().setItem(slot, crateType);

			slot+=2;
		}
	}

	@Override
	public void handle(InventoryClickEvent e) throws IOException, DataException {
		Player player = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getClickedInventory().getItem(e.getSlot());
		if(clickedItem == null) return;

		String crateType = PD.getItemDataString(clickedItem, new NamespacedKey(VerityCore.getPlugin(), "CRATE_TYPE"));
		new CrateRewardMenu(player, new CrateManager().getCrate(crateType)).open();
	}
}
