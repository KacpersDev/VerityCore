package xyz.cronu.veritycore.commands.commandlist.pet;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.pets.PetType;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PetGiveCMD extends SubcommandBase {
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
		return "&c/pet give <type> <player> <level>";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if(sender.isOp() || sender.hasPermission("pet.admin")){
			if(args.length == 4) {
				String type = args[1];
				Player target = Bukkit.getPlayerExact(args[2]);
				int level = Integer.parseInt(args[3]);

				if(!PetManager.isValidType(type)) { Text.message(sender, Prefix.SERVER.getPrefix() + "&cInvalid pet type.");return; }
				if(target == null){ Text.message(sender, Prefix.SERVER.getPrefix() + "&cInvalid player input.");return; }
				if(level <= 0){ Text.message(sender, Prefix.SERVER.getPrefix() + "&cPlease enter a value greater than 0!");return; }

				PetType petType = PetType.valueOf(type.toUpperCase());
				PetManager.givePet(target, petType, level);
				Text.message(target, Prefix.SERVER.getPrefix() + "&7You've been given a " + petType.getDisplayName() + "&7!");

			} else {
				Text.message(sender, getSyntax());
				return;
			}
		} else {
			Text.message(sender, Prefix.SERVER.getPrefix() + "&cYou do not have permission.");
		}
	}

	@Override
	public List<String> getParameters(CommandSender sender, String[] args) {
		List<String> petTypes = new ArrayList<>();
		for(PetType type : PetType.values()){
			petTypes.add(type.name());
		}
		return petTypes;
	}
}
