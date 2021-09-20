package xyz.cronu.veritycore.commands.commandlist.admin;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.pickaxe.PickaxeManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewPickaxeCMD extends CommandBase {
	@Override
	public String getName() {
		return "newpickaxe";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return new ArrayList<>();
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp() || player.hasPermission("pickaxe.admin")) {
				PickaxeManager.generateNewPickaxe(player);
			}
		}
	}
}
