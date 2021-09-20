package xyz.cronu.veritycore.events.consumable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.consumables.ConsumableType;
import xyz.cronu.veritycore.consumables.Consumables;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;

public class ConsumableConsumeEvent implements Listener {

	@EventHandler
	public void onConsumableClick(PlayerInteractEvent e){
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();
		if(!Consumables.isConsumable(itemInHand)) return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR) return;
		e.setCancelled(true);

		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());

		if (Consumables.getConsumableType(itemInHand) == ConsumableType.TOKEN) {
			double tokenAmount = Consumables.getTokenItemAmount(itemInHand);
			itemInHand.setAmount(itemInHand.getAmount() - 1);
			user.addTokens(tokenAmount);
		}

	}

}
