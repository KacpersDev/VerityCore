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

public class PrivateMineResetCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "reset";
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
	public void perform(CommandSender sender, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			try {
				new MineManager().reset(player);
			} catch (DataException | IOException e){
				e.printStackTrace();
			}
			Text.message(player, Prefix.MINE.getPrefix() + "&7Your &cPrivate Mine &7has successfully reset.");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
