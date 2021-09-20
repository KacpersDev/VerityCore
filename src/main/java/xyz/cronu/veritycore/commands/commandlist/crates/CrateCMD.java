package xyz.cronu.veritycore.commands.commandlist.crates;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.menu.menus.CrateSelectorMenu;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrateCMD extends CommandBase {
	@Override
	public String getName() {
		return "crates";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return new ArrayList<>();
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			new CrateSelectorMenu(player).open();
		} else {
			Text.message(sender, Prefix.CRATES.getPrefix() + "Only players can execute this command.");
		}
	}
}
