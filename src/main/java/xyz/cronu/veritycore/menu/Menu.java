package xyz.cronu.veritycore.menu;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;

public abstract class Menu implements InventoryHolder {

	public Player player;
	public Inventory inv;
	public abstract int getSlots();
	public abstract String getTitle();
	public abstract void setItems() throws IOException, DataException;
	public abstract void handle(InventoryClickEvent e) throws IOException, DataException;

	public Menu(Player player){
		this.player = player;
	}

	public void open() throws IOException, DataException {
		int slots = getSlots();
		if(slots > 54) slots = 54;
		this.inv = Bukkit.createInventory(this, slots, Text.color(getTitle()));
		this.setItems();
		player.openInventory(this.inv);
	}

	@Override
	public Inventory getInventory() {
		return this.inv;
	}

}
