package xyz.cronu.veritycore.commands.commandlist.privatemines.sub;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.mines.MineManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateMineTpCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "tp";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"teleport", "go"};
	}

	@Override
	public String getSyntax() {
		return "";
	}

	@Override
	public void perform(CommandSender sender, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			try {
				new MineManager().teleport(player);
			} catch (DataException | IOException e){
				e.printStackTrace();
			}
			Text.message(player, Prefix.MINE.getPrefix() + "&7You're being teleported to your &cPrivate Mine&7.");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
