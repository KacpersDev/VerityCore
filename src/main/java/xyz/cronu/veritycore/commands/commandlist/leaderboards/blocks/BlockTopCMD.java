package xyz.cronu.veritycore.commands.commandlist.leaderboards.blocks;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.leaderboards.LeaderboardManager;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BlockTopCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "top";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return "/blocks top";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {

		Map<String, Double> prestigeTop = LeaderboardManager.getLeaderboard().getBlockTop();
		int postion = 1;
		Text.message(sender, "&8&m--------------&r &4&lBLOCK TOP &8&m--------------");
		Text.message(sender, "");
		for (Map.Entry<String, Double> values : prestigeTop.entrySet()) {
			if(postion > 10) break;
			String name = Bukkit.getOfflinePlayer(UUID.fromString(values.getKey())).getName();
			double prestige = prestigeTop.get(values.getKey());
			Text.message(sender, "&8(&f&l#" + postion + "&8) &4&l" + name + " &8● &c" + Number.formatted(prestige));
			postion+=1;
		}
		Text.message(sender, "");
		Text.message(sender, "&8&m--------------&r &4&lBLOCK TOP &8&m--------------");

	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
