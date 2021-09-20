package xyz.cronu.veritycore.commands.commandlist.misc;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlyCMD extends CommandBase {
	@Override
	public String getName() {
		return "fly";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return new ArrayList<>();
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("fly.use")) {
				if (player.getAllowFlight()) {
					player.setFlying(false);
					player.setAllowFlight(false);
					Text.message(player, Prefix.SERVER.getPrefix() + "&cFlight has been disabled.");
				} else {
					player.setAllowFlight(true);
					player.setFlying(true);
					Text.message(player, Prefix.SERVER.getPrefix() + "&aFlight has been enabled.");
				}
			}
		}
	}
}
