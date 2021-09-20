package xyz.cronu.veritycore.commands.commandlist.leaderboards.money;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.Number;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MoneyCMD extends CommandBase {
	@Override
	public String getName() {
		return "bal";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new MoneyTopCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(args.length == 0){
				PrisonPlayer prisonPlayer = PrisonPlayerManager.getPrisonPlayer(player.getUniqueId());
				Text.message(player, Prefix.SERVER.getPrefix() + "&7You currently have &c$" + Number.pretty(prisonPlayer.getMoney()) + "&7!");
			}
		} else {
			Text.message(sender, Prefix.SERVER.getPrefix() + "Only players can execute this command.");
		}
	}
}
