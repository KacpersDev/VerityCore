package xyz.cronu.veritycore.commands.commandlist.admin.add.sub;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.users.OfflinePrisonPlayer;
import xyz.cronu.veritycore.users.OfflinePrisonPlayerManager;
import xyz.cronu.veritycore.users.PrisonPlayer;
import xyz.cronu.veritycore.users.PrisonPlayerManager;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TakeSubCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "take";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return "/admin take <type> <player> <amount>";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if (sender.isOp() || sender.hasPermission("admin.take")) {
			if (args.length == 4) {
				String type = args[1];
				Player target = Bukkit.getPlayerExact(args[2]);
				double amount = Integer.parseInt(args[3]);

				if(target == null) { // Checks if the player entered is online or invalid.
					OfflinePrisonPlayer offlinePrisonPlayer = OfflinePrisonPlayerManager.loadOfflinePrisonPlayer(Bukkit.getOfflinePlayer(args[2]).getUniqueId());
					// Tries to load an OfflinePrisonPlayer by checking if the UUID is within the database. ^^
					if (offlinePrisonPlayer == null) { Text.message(sender, "&cThis user does not exist within our database.");return; }
					// If the OfflinePrisonPlayer is null this means they are not within the database.

					switch (type) {
						case "TOKENS":
							offlinePrisonPlayer.takeTokens(amount);
							break;
						case "MONEY":
							offlinePrisonPlayer.takeMoney(amount);
							break;
						case "BLOCKS":
							offlinePrisonPlayer.takeBlocksMined((long) amount);
							break;
						case "PRESTIGE":
							offlinePrisonPlayer.takePrestige((long) amount);
							break;
					}

					// Saves the data off the OfflinePrisonPlayer.
					OfflinePrisonPlayerManager.unloadPrisonPlayer(offlinePrisonPlayer);
				} else { // This means the player isn't invalid and is online.

					PrisonPlayer prisonPlayer = PrisonPlayerManager.getPrisonPlayer(target.getUniqueId()); // Tries to load a PrisonPlayer object
					if (prisonPlayer == null) { // If the player isn't loaded into the List<PrisonPlayer> it will do so.
						Text.message(sender, "&cPlayer wasn't loaded into our database, doing that now!");
						PrisonPlayerManager.loadPrisonPlayer(target.getUniqueId());
						return;
					}

					switch (type) {
						case "TOKENS":
							prisonPlayer.takeTokens(amount);
							break;
						case "MONEY":
							prisonPlayer.takeMoney(amount);
							break;
						case "BLOCKS":
							prisonPlayer.takeBlocksMined((long) amount);
							break;
						case "PRESTIGE":
							prisonPlayer.takePrestige((long) amount);
							break;
					}

				}
			} else {
				Text.message(sender, getSyntax());
			}
		} else {
			Text.message(sender, "&cYou do not have permission.");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		List<String> types = new ArrayList<>();
		types.add("MONEY");
		types.add("TOKENS");
		types.add("PRESTIGE");
		types.add("BLOCKS");
		return types;
	}
}
