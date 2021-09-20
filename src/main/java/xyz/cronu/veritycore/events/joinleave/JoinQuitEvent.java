package xyz.cronu.veritycore.events.joinleave;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.scoreboard.CScoreboard;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.ConfigManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;

public class JoinQuitEvent implements Listener {

	private ConfigManager cm = ConfigManager.getInstance();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException, DataException {
		Player player = e.getPlayer();

		if(!player.hasPlayedBefore()) {
			FileConfiguration config = cm.getConfig("server.yml");
			cm.setData(config, "players_joined", config.getInt("players_joined") + 1);
			e.setJoinMessage(Text.color("&7Welcome &c" + player.getName() + " &7to &c&lVerity&7! &8(&f#" + config.getInt("players_joined") + "&8)"));
			PickaxeManager.generateNewPickaxe(player);
		} else {
			e.setJoinMessage(Text.color("&7(&a&l+&7) &c" + player.getName()));
		}

		PrisonPlayerManager.loadPrisonPlayer(player.getUniqueId());
		player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000,  200));
		CScoreboard.scoreBoard(player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		e.setQuitMessage(Text.color("&7(&c&l-&7) &c" + player.getName()));
		PrisonPlayerManager.unloadPrisonPlayer(player.getUniqueId());
	}


}
