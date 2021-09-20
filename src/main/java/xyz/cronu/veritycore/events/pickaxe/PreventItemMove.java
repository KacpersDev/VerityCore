package xyz.cronu.veritycore.events.pickaxe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PreventItemMove implements Listener {

	@EventHandler
	public void inventoryInteract(InventoryClickEvent e) {
		if (e.getWhoClicked().isOp()) return;

		Player player = (Player) e.getWhoClicked();
		if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE))
			e.setCancelled(true);

		if(e.getClick() == ClickType.NUMBER_KEY){
			ItemStack item = e.getView().getBottomInventory().getItem(e.getHotbarButton());
			if(item != null && item.getType().equals(Material.DIAMOND_PICKAXE))
				e.setCancelled(true);
		}
	}


	@EventHandler
	public void dropItem(PlayerDropItemEvent e){
		ItemStack item = e.getItemDrop().getItemStack();
		if(e.getPlayer().isOp()) return;
		if(item.getType().equals(Material.DIAMOND_PICKAXE)) {
			e.setCancelled(true);
		}
	}

}
