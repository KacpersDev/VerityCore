package xyz.cronu.veritycore.utilities;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

public class Hover {

	public static void hover(Player p, String chat, String click, String hover) {
		p.spigot()
				.sendMessage(new ComponentBuilder(Text.color(chat))
						.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Text.color(hover)).create()))
						.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, click)).create());
	}

}
