package xyz.cronu.veritycore.commands.commandlist.privatemines.sub;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.menu.menus.MineMenu;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateMineInfoCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "info";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return Prefix.MINE.getPrefix() + "&c/pm &7<&cinfo&7>";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(!new MineManager().hasMine(player)){
				Text.message(player, Prefix.MINE.getPrefix() + "&7You do not have a private mine, /pm create.");
				return;
			}
			new MineMenu(player).open();
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
