package xyz.cronu.veritycore.commands.commandlist.admin.add;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.commands.commandlist.admin.add.sub.AddSubCMD;
import xyz.cronu.veritycore.commands.commandlist.admin.add.sub.SetSubCMD;
import xyz.cronu.veritycore.commands.commandlist.admin.add.sub.TakeSubCMD;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminCMD extends CommandBase {
	@Override
	public String getName() {
		return "admin";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new AddSubCMD(), new TakeSubCMD(), new SetSubCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		if(sender.isOp() || sender.hasPermission("admin.add")){
			Text.message(sender, "&c/admin <add, take, set> <type> <player> <amount>");
		}
	}
}
