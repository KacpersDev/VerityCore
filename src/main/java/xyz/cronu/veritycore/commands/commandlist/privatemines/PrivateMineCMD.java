package xyz.cronu.veritycore.commands.commandlist.privatemines;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.commands.commandlist.privatemines.sub.*;
import xyz.cronu.veritycore.utilities.Text;

import java.util.Arrays;
import java.util.List;

public class PrivateMineCMD extends CommandBase {

	@Override
	public String getName() {
		return "privatemine";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new PrivateMineCreateCMD(), new PrivateMineResetCMD(), new PrivateMineTpCMD(), new PrivateMineInfoCMD(), new PrivateMineBlocksCMD(), new PrivateMineUpgradeCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			Text.message(player, Prefix.MINE.getPrefix() + "&c/pm &7<&ccreate&7, &ctp&7, &creset&7, &cupgrade&7, &cinfo&7, &cblock&7>");
		}
	}
}
