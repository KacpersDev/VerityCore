package xyz.cronu.veritycore.users;

import org.bukkit.configuration.ConfigurationSection;
import xyz.cronu.veritycore.utilities.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisonPlayerManager {

	private static ConfigManager cm = ConfigManager.getInstance();
	public static List<PrisonPlayer> onlinePrisonPlayers = new ArrayList<>();

	public static void loadPrisonPlayer(UUID uuid){
		ConfigurationSection section = cm.getConfig("players.yml").getConfigurationSection("players." + uuid.toString());
		if(prisonPlayerExists(uuid)) {
			PrisonPlayer prisonPlayer = new PrisonPlayer(
					uuid,
					section.getDouble("tokens"),
					section.getDouble("money"),
					section.getLong("blocks_mined"),
					section.getLong("prestige"));
			onlinePrisonPlayers.add(prisonPlayer);
		} else {
			cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".tokens", 0D);
			cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".money", 0D);
			cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".blocks_mined", 0L);
			cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".prestige", 0L);
			onlinePrisonPlayers.add(new PrisonPlayer(uuid, 0D, 0D, 0L, 0L));
		}
	}

	public static void unloadPrisonPlayer(UUID uuid){
		PrisonPlayer prisonPlayer = getPrisonPlayer(uuid);
		cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".tokens", prisonPlayer.getTokens());
		cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".money", prisonPlayer.getMoney());
		cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".blocks_mined", prisonPlayer.getBlocksMined());
		cm.setData(cm.getConfig("players.yml"), "players." + uuid.toString() + ".prestige", prisonPlayer.getPrestige());
	}

	public static PrisonPlayer getPrisonPlayer(UUID uuid){
		if(onlinePrisonPlayers == null || onlinePrisonPlayers.isEmpty()) return null;
		if(!prisonPlayerExists(uuid)) return null;
		PrisonPlayer prisonP = null;
		for(PrisonPlayer prisonPlayer : onlinePrisonPlayers){
			if(prisonPlayer.getUuid().equals(uuid)) {
				prisonP = prisonPlayer;
				break;
			}
		}
		return prisonP;
	}

	public static boolean prisonPlayerExists(UUID uuid){
		return cm.getConfig("players.yml").get("players." + uuid.toString()) != null;
	}

}
