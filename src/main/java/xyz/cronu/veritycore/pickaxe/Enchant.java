package xyz.cronu.veritycore.pickaxe;

import com.sk89q.worldedit.world.DataException;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.util.List;

public abstract class Enchant {

	public abstract String getEnchantName();
	public abstract List<String> getDescription();
	public abstract NamespacedKey getKey();
	public abstract String getRawName();
	public abstract int getStartLevel();
	public abstract int getMaxLevel();
	public abstract int getAdminMaxLevel();
	public abstract double getBaseCost();
	public abstract void blockBreakEvent(BlockBreakEvent e) throws IOException, DataException;

}
