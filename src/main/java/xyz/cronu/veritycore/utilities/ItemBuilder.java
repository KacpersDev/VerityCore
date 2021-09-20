package xyz.cronu.veritycore.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {
	private Material mat;
	private int amount;
	private short damage;
	private String name;
	private List<String> lore;
	private Map<Enchantment, Integer> enchants;
	private boolean unbreakable;
	private boolean flagsHidden = true;
	private OfflinePlayer skullOwner;

	public ItemBuilder(ItemStack item) {
		this.mat = item.getType();
		this.amount = item.getAmount();
		this.damage = item.getDurability();
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			this.name = meta.hasDisplayName() ? meta.getDisplayName() : null;
			this.lore = meta.hasLore() ? meta.getLore() : null;
			this.enchants = meta.hasEnchants() ? meta.getEnchants() : null;
		}
	}

	public ItemBuilder() {
		this(Material.STONE);
	}

	public ItemBuilder(Material mat) {
		this(mat, 1);
	}

	public ItemBuilder(Material mat, int amount) {
		this(mat, amount, 0);
	}

	public ItemBuilder(Material mat, int amount, int damage) {
		this(mat, amount, (short)damage);
	}

	public ItemBuilder(Material mat, int amount, short damage) {
		this.mat = mat;
		this.amount = amount;
		this.damage = damage;
		this.lore = new ArrayList<>();
		this.enchants = new HashMap<>();
	}

	public ItemBuilder setMaterial(Material mat) {
		this.mat = mat;
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		return setLore(Arrays.asList(lore));
	}

	public ItemBuilder setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchant) {
		return addEnchantment(enchant, 1);
	}

	public ItemBuilder addEnchantment(Enchantment enchant, int level) {
		if (enchant != null)
			this.enchants.put(enchant, Integer.valueOf(level));
		return this;
	}

	public ItemBuilder setEnchantments(Map<Enchantment, Integer> ench) {
		this.enchants = ench;
		return this;
	}


	public ItemBuilder setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}

	public ItemBuilder hideFlags() {
		this.flagsHidden = true;
		return this;
	}

	public ItemBuilder showFlags() {
		this.flagsHidden = false;
		return this;
	}

	public ItemBuilder setSkullOwner(UUID id) {
		return setSkullOwner(Bukkit.getOfflinePlayer(id));
	}

	public ItemBuilder setSkullOwner(OfflinePlayer op) {
		this.skullOwner = op;
		return this;
	}

	public ItemStack create() {
		ItemStack item = new ItemStack(this.mat, this.amount);
		item.addUnsafeEnchantments(this.enchants);
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			if (this.name != null)
				meta.setDisplayName(Text.color(this.name));
			meta.setLore(Text.color(this.lore));
			meta.setUnbreakable(this.unbreakable);
			if (this.flagsHidden)
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
			item.setItemMeta(meta);
			if (item.getType().equals(Material.LEGACY_SKULL_ITEM)) {
				SkullMeta sm = (SkullMeta) item.getItemMeta();
				if (this.skullOwner != null)
					sm.setOwningPlayer(this.skullOwner);
				item.setItemMeta((ItemMeta) sm);
			}
		}
		return item;
	}
}