package xyz.cronu.veritycore.leaderboards;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.cronu.veritycore.VerityCore;
import xyz.cronu.veritycore.utilities.ConfigManager;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager {

	private static Leaderboard leaderboard;

	public static void refreshLeaderboards(){
		Bukkit.getScheduler().runTaskTimer(VerityCore.getPlugin(), () -> {
			Bukkit.broadcastMessage(Text.color("&aRefreshing Leaderboards!"));

			 leaderboard = new Leaderboard(
					Number.sortByValue(getLeaderboardTop("prestige")),
					Number.sortByValue(getLeaderboardTop("blocks_mined")),
					Number.sortByValue(getLeaderboardTop("tokens")),
					Number.sortByValue(getLeaderboardTop("money"))
			);

			Bukkit.broadcastMessage(Text.color("&aSuccessfully Refreshed Leaderboards!"));
		}, 20, (20L * 60L) * 30L);
	}

	public static Map<String, Double> getLeaderboardTop(String topType){
		Map<String, Double> topData = new HashMap<>();

		FileConfiguration config = ConfigManager.getInstance().getConfig("players.yml");
		ConfigurationSection section = config.getConfigurationSection("players");

		for (String player : section.getKeys(false)) {
			topData.put(player, section.getDouble(player + "." + topType));
		}
		return topData;
	}

	public static Leaderboard getLeaderboard() {
		return leaderboard;
	}
}
