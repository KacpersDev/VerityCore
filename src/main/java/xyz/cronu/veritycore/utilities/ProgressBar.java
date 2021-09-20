package xyz.cronu.veritycore.utilities;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class ProgressBar {


	public static String get(int current, int max, int totalBars, char symbol, ChatColor completedColor,
								 ChatColor notCompletedColor) {
		float percent = (float) current / max;
		int progressBars = (int) (totalBars * percent);

		return Strings.repeat("" + completedColor + symbol, progressBars)
				+ Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	}

}
