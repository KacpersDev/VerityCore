package xyz.cronu.veritycore.utilities;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PD {

	public static  void removeKeyPlayer(Player player, NamespacedKey key){
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.remove(key);
	}

	public static  void removeKeyItem(ItemStack item, NamespacedKey key){
		ItemMeta meta = item.getItemMeta();
		if(meta == null) return;
		PersistentDataContainer itemData = meta.getPersistentDataContainer();
		itemData.remove(key);
		item.setItemMeta(meta);
	}

	public static  boolean itemDataContainsKey(ItemStack item, NamespacedKey key){
		if(item == null) return false;
		ItemMeta meta = item.getItemMeta();
		if(meta == null) return false;
		PersistentDataContainer itemData = meta.getPersistentDataContainer();
		return itemData.getKeys().contains(key);
	}

	public static  boolean playerDataContainsKey(Player player, NamespacedKey key){
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		return playerData.getKeys().contains(key);
	}

	public static  void setPlayerDataInteger(Player player, NamespacedKey key, int value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.set(key, PersistentDataType.INTEGER, value);
	}

	public static  void setPlayerDataDouble(Player player, NamespacedKey key, double value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.set(key, PersistentDataType.DOUBLE, value);
	}

	public static  void setPlayerDataString(Player player, NamespacedKey key, String value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.set(key, PersistentDataType.STRING, value);
	}

	public static  void setPlayerDataLong(Player player, NamespacedKey key, long value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.set(key, PersistentDataType.LONG, value);
	}

	public static  void setPlayerDataFloat(Player player, NamespacedKey key, float value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		playerData.set(key, PersistentDataType.FLOAT, value);
	}

	public static  int getPlayerDataInteger(Player player, NamespacedKey key) {
		int value = 0;
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (playerData.has(key, PersistentDataType.INTEGER))
			value = playerData.get(key, PersistentDataType.INTEGER);
		return value;
	}

	public static  double getPlayerDataDouble(Player player, NamespacedKey key) {
		double value = 0D;
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (playerData.has(key, PersistentDataType.DOUBLE))
			value = playerData.get(key, PersistentDataType.DOUBLE);
		return value;
	}

	public static  String getPlayerDataString(Player player, NamespacedKey key) {
		String value = null;
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (playerData.has(key, PersistentDataType.STRING))
			value = playerData.get(key, PersistentDataType.STRING);
		return value;
	}

	public static  float getPlayerDataFloat(Player player, NamespacedKey key) {
		float value = 0f;
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (playerData.has(key, PersistentDataType.FLOAT))
			value = playerData.get(key, PersistentDataType.FLOAT);
		return value;
	}

	public static  long getPlayerDataLong(Player player, NamespacedKey key) {
		long value = 0L;
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (playerData.has(key, PersistentDataType.LONG))
			value = playerData.get(key, PersistentDataType.LONG);
		return value;
	}

	public static  void addPlayerDataInteger(Player player, NamespacedKey key, int value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (!playerData.has(key, PersistentDataType.INTEGER)){
			setPlayerDataInteger(player, key, 0);
		}
		int finalValue = playerData.get(key, PersistentDataType.INTEGER) + value;
		setPlayerDataInteger(player, key, finalValue);
	}

	public static  void addPlayerDataDouble(Player player, NamespacedKey key, double value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (!playerData.has(key, PersistentDataType.DOUBLE)){
			setPlayerDataDouble(player, key, 0D);
		}
		double finalValue = playerData.get(key, PersistentDataType.DOUBLE) + value;
		setPlayerDataDouble(player, key, finalValue);
	}

	public static  void addPlayerDataLong(Player player, NamespacedKey key, long value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (!playerData.has(key, PersistentDataType.LONG)){
			setPlayerDataLong(player, key, 0L);
		}
		long finalValue = playerData.get(key, PersistentDataType.LONG) + value;
		setPlayerDataLong(player, key, finalValue);
	}

	public static  void addPlayerDataFloat(Player player, NamespacedKey key, float value) {
		PersistentDataContainer playerData = player.getPersistentDataContainer();
		if (!playerData.has(key, PersistentDataType.FLOAT)){
			setPlayerDataFloat(player, key, 0F);
		}
		float finalValue = playerData.get(key, PersistentDataType.FLOAT) + value;
		setPlayerDataFloat(player, key, finalValue);
	}

	public static  void setItemDataInteger(ItemStack item, NamespacedKey key, int value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		ItemData.set(key, PersistentDataType.INTEGER, value);
		item.setItemMeta(meta);
	}

	public static  void setItemDataDouble(ItemStack item, NamespacedKey key, double value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		ItemData.set(key, PersistentDataType.DOUBLE, value);
		item.setItemMeta(meta);
	}

	public static  void setItemDataString(ItemStack item, NamespacedKey key, String value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		ItemData.set(key, PersistentDataType.STRING, value);
		item.setItemMeta(meta);
	}

	public static  void setItemDataLong(ItemStack item, NamespacedKey key, long value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		ItemData.set(key, PersistentDataType.LONG, value);
		item.setItemMeta(meta);
	}

	public static  void setItemDataFloat(ItemStack item, NamespacedKey key, float value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		ItemData.set(key, PersistentDataType.FLOAT, value);
		item.setItemMeta(meta);
	}

	public static  int getItemDataInteger(ItemStack item, NamespacedKey key) {
		int value = 0;
		if (!item.hasItemMeta()) return 0;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (ItemData.has(key, PersistentDataType.INTEGER))
			value = ItemData.get(key, PersistentDataType.INTEGER);
		return value;
	}

	public static  double getItemDataDouble(ItemStack item, NamespacedKey key) {
		double value = 0D;
		if (!item.hasItemMeta()) return 0D;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (ItemData.has(key, PersistentDataType.DOUBLE))
			value = ItemData.get(key, PersistentDataType.DOUBLE);
		return value;
	}

	public static  String getItemDataString(ItemStack item, NamespacedKey key) {
		String value = null;
		if (!item.hasItemMeta()) return null;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (ItemData.has(key, PersistentDataType.STRING))
			value = ItemData.get(key, PersistentDataType.STRING);
		return value;
	}

	public static  float getItemDataFloat(ItemStack item, NamespacedKey key) {
		float value = 0f;
		if (!item.hasItemMeta()) return 0f;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (ItemData.has(key, PersistentDataType.FLOAT))
			value = ItemData.get(key, PersistentDataType.FLOAT);
		return value;
	}

	public static  long getItemDataLong(ItemStack item, NamespacedKey key) {
		long value = 0L;
		if (!item.hasItemMeta()) return 0L;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (ItemData.has(key, PersistentDataType.LONG))
			value = ItemData.get(key, PersistentDataType.LONG);
		return value;
	}

	public static  void addItemDataInteger(ItemStack item, NamespacedKey key, int value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (!ItemData.has(key, PersistentDataType.INTEGER)) return;
		int finalValue = ItemData.get(key, PersistentDataType.INTEGER) + value;
		setItemDataInteger(item, key, finalValue);
		item.setItemMeta(meta);
	}

	public static  void addItemDataDouble(ItemStack item, NamespacedKey key, double value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (!ItemData.has(key, PersistentDataType.DOUBLE)) return;
		double finalValue = ItemData.get(key, PersistentDataType.DOUBLE) + value;
		setItemDataDouble(item, key, finalValue);
		item.setItemMeta(meta);
	}

	public static  void addItemDataLong(ItemStack item, NamespacedKey key, long value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (!ItemData.has(key, PersistentDataType.LONG)) return;
		long finalValue = ItemData.get(key, PersistentDataType.LONG) + value;
		setItemDataLong(item, key, finalValue);
		item.setItemMeta(meta);
	}

	public static  void addItemDataFloat(ItemStack item, NamespacedKey key, float value) {
		if (!item.hasItemMeta()) return;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer ItemData = meta.getPersistentDataContainer();
		if (!ItemData.has(key, PersistentDataType.FLOAT)) return;
		float finalValue = ItemData.get(key, PersistentDataType.FLOAT) + value;
		setItemDataFloat(item, key, finalValue);
		item.setItemMeta(meta);
	}

}

