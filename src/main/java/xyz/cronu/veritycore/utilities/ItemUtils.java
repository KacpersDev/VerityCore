package xyz.cronu.veritycore.utilities;

import org.bukkit.Material;

public class ItemUtils {

	public static String getFormattedName(Material material) {
		if ( material == null ) {
			return null;
		}
		StringBuilder friendlyName = new StringBuilder();
		for ( String word : material.name().split( "_" ) ) {
			friendlyName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
		}
		return friendlyName.toString().trim();
	}

}
