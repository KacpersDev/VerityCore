package xyz.cronu.veritycore.commands.commandlist.misc;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedCMD extends CommandBase {
	@Override
	public String getName() {
		return "feed";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return new ArrayList<>();
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("feed.use")) {
				player.setFoodLevel(100);
				player.setHealth(20);
			}
		}
	}
}
