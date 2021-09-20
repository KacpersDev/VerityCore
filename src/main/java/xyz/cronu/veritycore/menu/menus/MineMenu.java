package xyz.cronu.veritycore.menu.menus;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.mines.Mine;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MineMenu extends Menu {
	public MineMenu(Player player) {
		super(player);
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
	public String getTitle() {
		return "&c" + player.getName() + "'s Personal Mine";
	}

	@Override
	public void setItems() throws IOException, DataException {

		Mine mine = new MineManager().getMine(player);
		if(mine == null) return;

		List<String> lore = new ArrayList<>();
		lore.add(Text.color("&7Below is the current information of"));
		lore.add(Text.color("&7your personal mine."));
		lore.add("");
		lore.add(Text.color("&7Current Mine Block: &c" + mine.getBlock()));
		lore.add(Text.color("&7Blocks Left: &c" + Number.pretty(mine.emptyBlocks())));
		lore.add(Text.color("&7Level: &c" + Number.pretty(mine.getLevel())));
		lore.add(Text.color("&7Block Worth: &c" + Number.pretty(mine.getBlocksWorth())));
		lore.add(Text.color("&7Progress:"));
		lore.add(Text.color("&8[" + Text.getProgressBar(mine.getBlocksMined(), mine.levelUpCost(), 35, ':', ChatColor.GREEN, ChatColor.GRAY) + "&8]"));

		ItemStack mineInformation = new ItemBuilder()
				.setName("&c&lMINE INFORMATION")
				.setMaterial(Material.DIAMOND_PICKAXE)
				.setAmount(1)
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.setLore(lore)
				.create();

		getInventory().setItem(22, mineInformation);
	}

	@Override
	public void handle(InventoryClickEvent e) {

	}
}
