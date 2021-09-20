package xyz.cronu.veritycore.commands.commandlist.crates;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.crates.CrateManager;
import xyz.cronu.veritycore.crates.CrateType;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiveKeyCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "give";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return Prefix.CRATES.getPrefix() + "&c/key give <type> <player> <amount>";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if (sender.isOp()) {
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("give")) {
					String type = args[1];
					Player target = Bukkit.getPlayerExact(args[2]);
					int amount = Integer.parseInt(args[3]);
					if (target == null) {
						Text.message(sender, Prefix.CRATES.getPrefix() + "&cInvalid player input!");
						return;
					}
					if (!new CrateManager().isValidType(type)) {
						Text.message(sender, Prefix.CRATES.getPrefix() + "&cInvalid crate type!");
						return;
					}
					if (amount <= 0) {
						Text.message(sender, Prefix.CRATES.getPrefix() + "&cPlease enter a value greater than 0!");
						return;
					}
					new CrateManager().giveCrateKey(target, CrateType.valueOf(type), amount);
				} else {
					Text.message(sender, getSyntax());
				}
			} else {
				Text.message(sender, getSyntax());
			}
		} else {
			Text.message(sender, Prefix.CRATES.getPrefix() + "&cYou do not have permission!");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		List<String> crateTypes = new ArrayList<>();
		for (CrateType type : CrateType.values()) {
			crateTypes.add(type.name());
		}
		return crateTypes;
	}
}
