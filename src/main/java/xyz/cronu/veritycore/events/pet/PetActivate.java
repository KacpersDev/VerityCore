package xyz.cronu.veritycore.events.pet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.pets.PetManager;

public class PetActivate implements Listener {

	@EventHandler
	public void onPetActive(PlayerInteractEvent e){
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();

		if(itemInHand.getType() == Material.AIR) return;
		if(!PetManager.isPetItem(itemInHand)) return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking()) {
			PetManager.activatePet(player, itemInHand);
			e.setCancelled(true);
		}

	}

}
