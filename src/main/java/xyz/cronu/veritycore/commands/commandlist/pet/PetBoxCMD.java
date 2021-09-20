package xyz.cronu.veritycore.commands.commandlist.pet;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.commands.SubcommandBase;
import xyz.cronu.veritycore.pets.PetManager;
import xyz.cronu.veritycore.pets.PetTier;
import xyz.cronu.veritycore.utilities.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PetBoxCMD extends SubcommandBase {
	@Override
	public String getName() {
		return "box";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getSyntax() {
		return "/pet box <tier> <player> <amount>";
	}

	@Override
	public void perform(CommandSender sender, String[] args) throws IOException, DataException {
		if(sender.isOp() || sender.hasPermission("pet.admin")){
			if(args.length == 4) {
				String tier = args[1];
				Player target = Bukkit.getPlayerExact(args[2]);
				int amount = Integer.parseInt(args[3]);

				if(!PetManager.isValidTier(tier)) { Text.message(sender, Prefix.SERVER.getPrefix() + "&cInvalid pet tier.");return; }
				if(target == null){ Text.message(sender, Prefix.SERVER.getPrefix() + "&cInvalid player input.");return; }
				if(amount <= 0){ Text.message(sender, Prefix.SERVER.getPrefix() + "&cPlease enter a value greater than 0!");return; }

				PetTier petTier = PetTier.valueOf(tier.toUpperCase());
				PetManager.givePetBox(target, amount, petTier);
				Text.message(target, Prefix.SERVER.getPrefix() + "&7You've been given a &cPet Box&7!");

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
		List<String> petTiers = new ArrayList<>();
		for(PetTier tier : PetTier.values()) petTiers.add(tier.name());
		return petTiers;
	}
}
