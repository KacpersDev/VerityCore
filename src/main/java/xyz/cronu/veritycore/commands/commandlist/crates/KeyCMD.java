package xyz.cronu.veritycore.commands.commandlist.crates;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class KeyCMD extends CommandBase {
	@Override
	public String getName() {
		return "key";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new GiveKeyCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(args.length == 0){
			Text.message(sender, Prefix.CRATES.getPrefix() + "&c/key give <type> <player> <amount>");
		}
	}
}
