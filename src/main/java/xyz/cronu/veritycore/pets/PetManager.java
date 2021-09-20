package xyz.cronu.veritycore.pets;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetManager {

	public static void generateRandomPet(Player player, PetTier tier) {
		givePet(player, PetType.random(), (long) randomPetLevel(PetType.random(), tier));
	}

	private static double randomPetLevel(PetType type, PetTier tier) {
		double pow = 0.0D;
		double max = 100.0D;
		switch (tier) {
			case CRYSTAL:
				return Math.round(10.0D * xyz.cronu.veritycore.utilities.Math.random(type.getMin(), type.getMax())) / 10.0D;
			case COMMON:
				pow = 8.0D;
				max = 25.0D;
				break;
			case RARE:
				pow = 5.0D;
				max = 50.0D;
				break;
			case LEGENDARY:
				pow = 4.0D;
				max = 75.0D;
				break;
			case STELLAR:
				pow = 3.0D;
				max = 99.0D;
				break;
		}
		double rco = Math.pow(Math.random(), pow);
		double min = type.getMin();
		max = type.getMax() * max / 100.0D;
		return Math.round(10.0D * ((max - min) * rco + min)) / 10.0D;
	}


	public static void givePetBox(Player player, int amount, PetTier tier){

		List<String> lore = new ArrayList<>();
		lore.add(Text.color("&7A &cPet Box &7will give you the"));
		lore.add(Text.color("&7chance to receive a &cPet &7which"));
		lore.add(Text.color("&7can be used to give you a boost towards"));
		lore.add(Text.color("&7a chosen &cStat&7."));
		lore.add("");
		lore.add("&7Pet Types: ");
		for (PetType value : PetType.values()) lore.add(Text.color("&7- " + value.getDisplayName()));

		ItemStack petBox = new ItemBuilder()
				.setAmount(amount)
				.setName("&8&m------------&r &4&lPET BOX &f(&c&l" + tier.name() + "&f) &8&m------------")
				.setLore(lore)
				.setMaterial(Material.HOPPER)
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.create();

		PD.setItemDataString(petBox, new NamespacedKey(VerityCore.getPlugin(), "PET_BOX_ITEM"), "TRUE");
		PD.setItemDataString(petBox, new NamespacedKey(VerityCore.getPlugin(), "PET_BOX_TIER"), tier.name());
		player.getInventory().addItem(petBox);
	}

	public static void givePet(Player player, PetType type, long level) {

		List<String> lore = new ArrayList<>(type.getLore());
		lore.add("");
		lore.add(Text.color("&f&lACTIVATED &8● &cNo"));
		lore.add(Text.color("&f&lPET LEVEL &8● &f" + Number.pretty(level)));
		lore.add("");

		ItemStack petItem = new ItemBuilder()
				.setMaterial(Material.BEACON)
				.setName(type.getDisplayName())
				.setAmount(1)
				.setLore(lore)
				.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
				.hideFlags()
				.create();

		setPetType(petItem, type);
		setPetLevel(petItem, level);
		setUniquePetID(petItem, UUID.randomUUID());
		PD.setItemDataString(petItem, new NamespacedKey(VerityCore.getPlugin(), "PET_ACTIVE"), "FALSE");
		player.getInventory().addItem(petItem);

	}

	public static void updatePet(ItemStack item) {
		if (!isPetItem(item)) return;
		PetType type = getPetType(item);
		String active = "&cNo";
		long petLevel = getPetLevel(item);

		if (isPetActive(item)) active = "&aYes";


		List<String> lore = new ArrayList<>(type.getLore());
		lore.add("");
		lore.add(Text.color("&f&lACTIVATED &8● " + active));
		lore.add(Text.color("&f&lPET LEVEL &8● &f" + Number.pretty(petLevel)));
		lore.add("");

		ItemMeta meta = item.getItemMeta();
		assert meta != null;
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public static ItemStack getBlockPet(Player player) {
		ItemStack item = null;
		for (ItemStack i : player.getInventory().getContents()) {
			if (!isPetItem(i)) continue;
			if (!isPetActive(i)) continue;
			if (getPetType(i) == (PetType.BLOCK)) {
				item = i;
				break;
			}
		}
		return item;
	}

	public static ItemStack getMoneyPet(Player player) {
		ItemStack item = null;
		for (ItemStack i : player.getInventory().getContents()) {
			if (!isPetItem(i)) continue;
			if (!isPetActive(i)) continue;
			if (getPetType(i) == (PetType.MONEY)) {
				item = i;
				break;
			}
		}
		return item;
	}

	public static ItemStack getPrestigePet(Player player) {
		ItemStack item = null;
		for (ItemStack i : player.getInventory().getContents()) {
			if (!isPetItem(i)) continue;
			if (!isPetActive(i)) continue;
			if (getPetType(i) == (PetType.PRESTIGE)) {
				item = i;
				break;
			}
		}
		return item;
	}

	public static void setUniquePetID(ItemStack item, UUID uuid) {
		PD.setItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ID"), uuid.toString());
	}

	public static void setPetLevel(ItemStack item, long amount) {
		PD.setItemDataLong(item, new NamespacedKey(VerityCore.getPlugin(), "PET_LEVEL"), amount);
	}

	public static void setPetType(ItemStack item, PetType type) {
		PD.setItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ITEM"), type.name());
	}

	public static ItemStack getCurrentActivePet(Player player) {
		ItemStack pet = null;
		for (ItemStack i : player.getInventory().getContents()) {
			if (!isPetItem(i)) continue;
			if (isPetActive(i)) {
				pet = i;
				break;
			}
		}
		return pet;
	}

	public static void activatePet(Player player, ItemStack item) {
		if (!isPetItem(item)) return;

		for (ItemStack i : player.getInventory().getContents()) {
			if (!isPetItem(i)) continue;
			if (getPetID(i).equals(getPetID(item))) continue;
			if (isPetActive(i)) {
				setPetUnActive(i);
				Text.message(player, Prefix.PETS.getPrefix() + "&cDeactivated &7your pet.");
				ParticleSpawner.spawn(player, new Particle.DustOptions(Color.fromRGB(127, 0, 0), 1.0F));
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 10, 10);
				updatePet(i);
			}
		}

		if (isPetActive(item)) {
			setPetUnActive(item);
			Text.message(player, Prefix.PETS.getPrefix() + "&cDeactivated &7your pet.");
			ParticleSpawner.spawn(player, new Particle.DustOptions(Color.fromRGB(127, 0, 0), 1.0F));
			player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 10, 10);
		} else {
			setPetActive(item);
			Text.message(player, Prefix.PETS.getPrefix() + "&aActivated &7your pet.");
			ParticleSpawner.spawn(player, new Particle.DustOptions(Color.fromRGB(0, 127, 0), 1.0F));
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
		}

		updatePet(item);
	}

	public static boolean isPetActive(ItemStack item) {
		if (!isPetItem(item)) return false;
		return PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ACTIVE")).equalsIgnoreCase("TRUE");
	}


	public static void setPetUnActive(ItemStack item) {
		if (!isPetItem(item)) return;
		PD.setItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ACTIVE"), "FALSE");
	}

	public static void setPetActive(ItemStack item) {
		if (!isPetItem(item)) return;
		PD.setItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ACTIVE"), "TRUE");
	}

	public static PetTier getPetBoxTier(ItemStack item){
		if(!isPetBoxItem(item)) return null;
		return PetTier.valueOf(PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_BOX_TIER")).toUpperCase());
	}

	public static String getPetID(ItemStack item) {
		if (!isPetItem(item)) return "";
		return PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ID"));
	}

	public static long getPetLevel(ItemStack item) {
		if (!isPetItem(item)) return -1;
		return PD.getItemDataLong(item, new NamespacedKey(VerityCore.getPlugin(), "PET_LEVEL"));
	}

	public static PetType getPetType(ItemStack item) {
		if (!isPetItem(item)) return null;
		return PetType.valueOf(PD.getItemDataString(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ITEM")));
	}

	public static boolean isPetItem(ItemStack item) {
		return PD.itemDataContainsKey(item, new NamespacedKey(VerityCore.getPlugin(), "PET_ITEM"));
	}

	public static boolean isPetBoxItem(ItemStack item){
		return PD.itemDataContainsKey(item, new NamespacedKey(VerityCore.getPlugin(), "PET_BOX_ITEM"));
	}

	public static boolean isValidTier(String input) {
		boolean value = false;
		for (PetTier tier : PetTier.values()) {
			if (tier.name().equalsIgnoreCase(input)) {
				value = true;
				break;
			}
		}
		return value;
	}

	public static boolean isValidType(String input) {
		boolean value = false;
		for (PetType type : PetType.values()) {
			if (type.name().equalsIgnoreCase(input)) {
				value = true;
				break;
			}
		}
		return value;
	}

}
