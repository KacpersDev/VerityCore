package xyz.cronu.veritycore.events.mining;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;

import java.io.IOException;

public class BlockBreak implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent e) throws IOException, DataException {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack pickaxeItem = player.getInventory().getItemInMainHand();
		if (pickaxeItem.getType() != Material.DIAMOND_PICKAXE) return;

		PrisonPlayer user = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());

		if (new MineManager().getMine(player) != null) {
			Mine mine = new MineManager().getMine(player);
			if (player.getWorld().getName().equalsIgnoreCase("mines")) {
				if (mine.getRegion().contains(block.getLocation())) {
					block.setType(Material.AIR);
					PickaxeManager.simulateBlockBreak(pickaxeItem, player, mine, user, e);
				} else {
					if (player.isOp()) return;
					e.setCancelled(true);
				}
			}
		}

	}
}
