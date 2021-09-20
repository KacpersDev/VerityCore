package xyz.cronu.veritycore.commands.commandlist.admin;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetPickaxeCMD extends CommandBase {
	@Override
	public String getName() {
		return "getpickaxe";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return new ArrayList<>();
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp() || player.hasPermission("pickaxe.admin")) {
				if (args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if(target == null) { Text.message(player, "&cInvalid player input.");return; }
					player.getInventory().setItem(0, PickaxeManager.getPickaxe(target));
				} else {
					Text.message(sender, "&c/getpickaxe <player>");
				}
			}
		} else {
			Text.message(sender, "Only players can execute this command.");
		}
	}
}
