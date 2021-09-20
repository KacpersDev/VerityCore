package xyz.cronu.veritycore.events.pickaxe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.pickaxe.enchants.Speed;

public class EquipEvent implements Listener {

	@EventHandler
	public void pickaxeEquip(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		ItemStack newSlot = player.getInventory().getItem(e.getNewSlot());
		if (newSlot == null) return;
		if (newSlot.getType().equals(Material.DIAMOND_PICKAXE)) {
			int speed = PickaxeManager.getEnchantLevel(newSlot, new Speed());
			if (speed >= 1) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, speed));
		} else {
			for (PotionEffect effect : player.getActivePotionEffects()) {
				if (effect.getType() == PotionEffectType.SPEED) {
					player.removePotionEffect(effect.getType());
					break;
				}
			}
		}
	}

}
