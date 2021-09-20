package xyz.cronu.veritycore.events.pickaxe;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.menu.menus.EnchantMenu;

import java.io.IOException;

public class PickaxeMenuEvent implements Listener {

	@EventHandler
	public void onPickaxeClick(PlayerInteractEvent e) throws IOException, DataException {
		Player player = e.getPlayer();
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
		if(itemInMainHand.getType() != Material.DIAMOND_PICKAXE) return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR) return;

		new EnchantMenu(player).open();
	}

}
