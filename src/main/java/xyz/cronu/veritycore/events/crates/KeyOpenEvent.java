package xyz.cronu.veritycore.events.crates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.crates.CrateManager;

public class KeyOpenEvent implements Listener {

	@EventHandler
	public void onKeyOpen(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();

		if (itemInHand.getType() == Material.AIR) return;
		if (!new CrateManager().isCrateKey(itemInHand)) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR) return;


		if (player.isSneaking())
			new CrateManager().openKey(player, itemInHand, itemInHand.getAmount());
		new CrateManager().openKey(player, itemInHand, 1);
	}

}
