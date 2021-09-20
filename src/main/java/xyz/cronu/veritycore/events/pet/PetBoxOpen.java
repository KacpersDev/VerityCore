package xyz.cronu.veritycore.events.pet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.utilities.Text;

public class PetBoxOpen implements Listener {

	@EventHandler
	public void onPetBoxOpen(PlayerInteractEvent e){
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();

		if(!PetManager.isPetBoxItem(itemInHand)) return;
		if(player.getInventory().firstEmpty() == -1) { Text.message(player, "&cYou must remove items from your inventory first.");return; }

		PetManager.generateRandomPet(player, PetManager.getPetBoxTier(itemInHand));
		itemInHand.setAmount(itemInHand.getAmount() - 1);
	}

}
