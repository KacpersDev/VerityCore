package xyz.cronu.veritycore.commands;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.List;

public abstract class SubcommandBase {
	public abstract String getName();
	public abstract String[] getAliases();
	public abstract String getSyntax();
	public abstract void perform(CommandSender sender, String[] args) throws IOException, DataException;
	public abstract List<String> getParameters(CommandSender sender, String[] args);
}
