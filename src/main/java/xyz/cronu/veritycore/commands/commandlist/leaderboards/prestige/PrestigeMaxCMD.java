package xyz.cronu.veritycore.commands.commandlist.leaderboards.prestige;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.prestige.PrestigeManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrestigeMaxCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "max";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return Prefix.SERVER.getPrefix() + "/prestige max";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			PrestigeManager.prestigeMax(player);
		} else {
			Text.message(sender, Prefix.SERVER.getPrefix() + "Only players can execute this command.");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
