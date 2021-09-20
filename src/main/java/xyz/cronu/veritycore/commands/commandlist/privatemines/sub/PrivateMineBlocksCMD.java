package xyz.cronu.veritycore.commands.commandlist.privatemines.sub;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.menu.menus.MineBlockMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateMineBlocksCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "block";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return "";
	}
	
	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			new MineBlockMenu(player).open();
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
