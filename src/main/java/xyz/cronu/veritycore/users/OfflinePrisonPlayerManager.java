package xyz.cronu.veritycore.users;

import org.bukkit.configuration.ConfigurationSection;
import xyz.cronu.veritycore.utilities.ConfigManager;

import java.util.UUID;

public class OfflinePrisonPlayerManager {

	private static ConfigManager cm = ConfigManager.getInstance();


	public static OfflinePrisonPlayer loadOfflinePrisonPlayer(UUID uuid){
		ConfigurationSection section = cm.getConfig("players.yml").getConfigurationSection("players." + uuid.toString());
		if(prisonPlayerExists(uuid)) {
			OfflinePrisonPlayer prisonPlayer = new OfflinePrisonPlayer(
					uuid,
					section.getDouble("tokens"),
					section.getDouble("money"),
					section.getLong("blocks_mined"),
					section.getLong("prestige"));
			return prisonPlayer;
		}
		return null;
	}

	public static void unloadPrisonPlayer(OfflinePrisonPlayer offlinePrisonPlayer){
		cm.setData(cm.getConfig("players.yml"), "players." + offlinePrisonPlayer.getUuid().toString() + ".tokens", offlinePrisonPlayer.getTokens());
		cm.setData(cm.getConfig("players.yml"), "players." + offlinePrisonPlayer.getUuid().toString() + ".money", offlinePrisonPlayer.getMoney());
		cm.setData(cm.getConfig("players.yml"), "players." + offlinePrisonPlayer.getUuid().toString() + ".blocks_mined", offlinePrisonPlayer.getBlocksMined());
		cm.setData(cm.getConfig("players.yml"), "players." + offlinePrisonPlayer.getUuid().toString() + ".prestige", offlinePrisonPlayer.getPrestige());
	}

	public static boolean prisonPlayerExists(UUID uuid){
		return cm.getConfig("players.yml").get("players." + uuid.toString()) != null;
	}

}
