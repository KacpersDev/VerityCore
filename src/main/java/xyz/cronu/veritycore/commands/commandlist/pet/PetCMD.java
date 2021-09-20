package xyz.cronu.veritycore.commands.commandlist.pet;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.CommandBase;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PetCMD extends CommandBase {
	@Override
	public String getName() {
		return "pet";
	}

	@Override
	public List<SubcommandBase> getSubcommands() {
		return Arrays.asList(new PetGiveCMD(), new PetBoxCMD());
	}

	@Override
	public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, DataException {
		sender.sendMessage(Text.color(Prefix.SERVER.getPrefix() + "&c/pet give <type> <player> <level>"));
	}
}
