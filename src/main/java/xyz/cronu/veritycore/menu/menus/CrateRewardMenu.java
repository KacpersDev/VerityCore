package xyz.cronu.veritycore.menu.menus;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.crates.Crate;
import xyz.cronu.veritycore.crates.CrateReward;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrateRewardMenu extends Menu {

	private Crate crate;

	public CrateRewardMenu(Player player, Crate crate) {
		super(player);
		this.crate = crate;
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
	public String getTitle() {
		return crate.getCrateType().getDisplayName() + " &7Rewards";
	}

	@Override
	public void setItems() throws IOException, DataException {
		int slot = 0;

		for(CrateReward reward : crate.getCrateRewards()){

			List<String> lore = new ArrayList<>();
			for(String l : reward.getRewardCommands()) {
				lore.add(Text.color(l));
				lore.add(reward.getChance() + " Chance");
			}

			ItemStack rewardItem = new ItemBuilder()
					.setMaterial(reward.getRewardMaterial())
					.setName(reward.getRewardName())
					.setLore(lore)
					.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
					.hideFlags()
					.create();

			getInventory().setItem(slot, rewardItem);
			slot++;

		}
	}

	@Override
	public void handle(InventoryClickEvent e) {

	}

}
