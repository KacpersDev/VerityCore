package xyz.cronu.veritycore.menu.menus;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;

public class MineUpgradeMenu extends Menu {
	
	public MineUpgradeMenu(Player player) {
		super(player);
	}

	@Override
	public int getSlots() {
		return 45;
	}

	@Override
	public String getTitle() {
		return "&cPrivate Mine Upgrade";
	}

	@Override
	public void setItems() throws IOException, DataException {
		if(!new MineManager().hasMine(player)) return;
		try {
			Mine mine = new MineManager().getMine(player);
			ItemStack item = new ItemBuilder()
					.setMaterial(Material.DIAMOND_PICKAXE)
					.setName("&aUpgrade Private Mine")
					.setLore(Text.color("&7Click here to upgrade your"),
							Text.color("&7mine using Mine Credits"),
							"",
							Text.color("&7Current Credits: &a" + Number.pretty(mine.getCredits())),
							Text.color("&7Upgrade Cost: &a" + Number.pretty(mine.worthUpgradeCost())),
							Text.color("&7Next Worth: &a$" + Number.pretty((mine.getBlocksWorth() + 0.2D))))
					.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
					.hideFlags()
					.create();

			getInventory().setItem(22, item);
		} catch (Exception ignored) {

		}
	}

	@Override
	public void handle(InventoryClickEvent e) throws IOException, DataException {
		if(!new MineManager().hasMine(player)) return;
		if(e.getSlot() == 22){
			try {
				Mine mine = new MineManager().getMine(player);
				if(mine.getCredits() >= mine.worthUpgradeCost()){
					mine.setCredits(mine.getCredits() - mine.worthUpgradeCost());
					mine.setBlocksWorth(mine.getBlocksWorth() + 0.2D);
					setItems();
					Text.message(player, Prefix.MINE.getPrefix() + "&cSuccessfully Upgraded The Block Worth Of Your Mine.");
					mine.save();
				} else {
					Text.message(player, Prefix.MINE.getPrefix() + "&cYou Do Not Have Enough Mine Credits.");
				}
			} catch (Exception ignored){

			}
		}
	}
}
