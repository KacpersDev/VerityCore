package xyz.cronu.veritycore.menu.menus;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.menu.Menu;
import xyz.cronu.veritycore.mines.Blocks;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.utilities.ItemBuilder;
import xyz.cronu.veritycore.utilities.ItemUtils;
import xyz.cronu.veritycore.utilities.PD;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MineBlockMenu extends Menu {

	public MineBlockMenu(Player player) {
		super(player);
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
	public String getTitle() {
		return "&eChange Mine Composition";
	}

	@Override
	public void setItems() throws IOException, DataException {
		AtomicInteger index = new AtomicInteger(9);
		Arrays.stream(Blocks.values()).forEach(block -> {
			ItemStack type = new ItemBuilder()
					.setMaterial(Material.valueOf(block.name()))
					.setAmount(1)
					.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
					.setName(Text.color("&e&l" + ItemUtils.getFormattedName(Material.valueOf(block.name()))))
					.hideFlags()
					.setLore(Text.color("&7Click here to change your &eMine&7."),
							Text.color("&7By changing the &ecomposition&7, your mine will"),
							Text.color("&7change to the corresponding block."))
					.create();
			PD.setItemDataString(type, new NamespacedKey(VerityCore.getPlugin(), "BLOCKTYPE"), block.name());
			getInventory().setItem(index.get(), type);
			index.getAndIncrement();
		} );
	}

	@Override
	public void handle(InventoryClickEvent e) throws IOException, DataException {
		Player player = (Player) e.getWhoClicked();
		ItemStack item = Objects.requireNonNull(e.getClickedInventory()).getItem(e.getSlot());
		assert item != null;
		String block = PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "BLOCKTYPE"));
		try {
			new MineManager().getMine(player).setBlock(block);
			new MineManager().reset(player);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 10f);
		} catch (IOException | DataException exception){
			exception.printStackTrace();
		}
	}
}
