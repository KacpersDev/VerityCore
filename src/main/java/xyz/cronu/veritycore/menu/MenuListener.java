package xyz.cronu.veritycore.menu;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.io.IOException;

public class MenuListener implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) throws IOException, DataException {
		InventoryHolder holder = e.getInventory().getHolder();
		if (holder instanceof Menu) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null) return;
			Menu menu = (Menu) holder;
			menu.handle(e);
		}
	}

}
