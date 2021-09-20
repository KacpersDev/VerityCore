package xyz.cronu.veritycore.utilities;

import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Text {

	public static String color(String input){
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static String getProgressBar(double current, double max, int totalBars, char symbol, ChatColor completedColor,
								 ChatColor notCompletedColor) {
		float percent = (float) ((float) current / max);
		int progressBars = (int) (totalBars * percent);

		return Strings.repeat("" + completedColor + symbol, progressBars)
				+ Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	}

	public static List<String> color(List<String> input){
		List<String> lore = new ArrayList<>();
		input.forEach(line -> lore.add(color(line)));
		return lore;
	}

	public static String formatArrayWithSpaces(final String name, final String splitter) {
		final String[] split = StringUtils.splitByWholeSeparator(name, splitter);
		final StringBuilder buff = new StringBuilder();
		for (final String str : split) {
			buff.append(StringUtils.capitalize(str.toLowerCase()) + " ");
		}
		return buff.toString().substring(0, buff.toString().length() - 1);
	}

	public static void broadcast(String input){
		Bukkit.getServer().broadcastMessage(color(input));
	}

	public static void message(Player target, String input){
		target.sendMessage(color(input));
	}

	public static void actionBar(Player target, String input){
		target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(input)));
	}

	public static void message(CommandSender target, String input){
		target.sendMessage(color(input));
	}

	private final static int CENTER_PX = 154;

	public static void sendCenteredMessage(Player player, String message){
		if(message == null || message.equals("")) player.sendMessage("");
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for(char c : message.toCharArray()){
			if(c == '§'){
				previousCode = true;
				continue;
			}else if(previousCode == true){
				previousCode = false;
				if(c == 'l' || c == 'L'){
					isBold = true;
					continue;
				}else isBold = false;
			}else{
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while(compensated < toCompensate){
			sb.append(" ");
			compensated += spaceLength;
		}
		message(player, sb.toString() + message);
	}

	public static void debug(String msg){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("debug.toggled") || p.isOp()){
				message(p, "&7[&d&lDEBUG&7] &d" + msg);
			}
		}
	}

}
