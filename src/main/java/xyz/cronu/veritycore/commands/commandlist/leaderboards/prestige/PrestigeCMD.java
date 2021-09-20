package xyz.cronu.veritycore.commands.commandlist.leaderboards.prestige;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.prestige.PrestigeManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PrestigeCMD extends CommandBase {
	@Override
	public String getName() {
		return "prestige";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new PrestigeMaxCMD(), new PrestigeTopCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			PrestigeManager.prestige(player);
		} else {
			Text.message(sender, Prefix.SERVER.getPrefix() + "Only players can execute this command.");
		}
	}
}
