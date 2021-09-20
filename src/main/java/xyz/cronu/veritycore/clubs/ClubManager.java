package xyz.cronu.veritycore.clubs;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import xyz.cronu.veritycore.utilities.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ClubManager {

	public static List<Club> ClubList = new ArrayList<>();

	public static boolean createClub(Player player, String name){
		if(isInClub(player)) { Text.message(player, "&cYou must not be in a Club.");return false; }
		if(ClubExists(name)) { Text.message(player, "&cThis Club already exists.");return false; }
		if(!StringUtils.isAlpha(name)) { Text.message(player, "&cClub names can only contain letters A-Z.");return false; }

		HashMap<UUID, String> memberList = new HashMap<>();
		memberList.put(player.getUniqueId(), "OWNER");

		Club Club = new Club(name, player.getUniqueId(), player.getName(), 1, 0, memberList);
		Club.save();
		ClubList.add(Club);

		Text.broadcast("&a" + Club.getOwnerName() + " &7has founded the Club &a" + Club.getName() + "&7!");
		return true;
	}

	public static boolean ClubExists(String name){
		if(ClubList == null || ClubList.isEmpty()) return false;
		boolean value = false;
		for(Club g : ClubList){
			if(g.getName().equalsIgnoreCase(name)){
				value = true;
				break;
			}
		}
		return value;
	}

	public static boolean isInClub(Player player){
		if(ClubList == null || ClubList.isEmpty()) return false;
		boolean value = false;
		for(Club g : ClubList){
			if(g.getMembers().containsKey(player.getUniqueId())){
				value = true;
				break;
			}
		}
		return value;
	}

	public static Club getClub(String ClubName){
		if(ClubList == null || ClubList.isEmpty()) return null;
		Club Club = null;
		for(Club g : ClubList){
			if(g.getName().equalsIgnoreCase(ClubName)){
				Club = g;
				break;
			}
		}
		return Club;
	}

	public static void saveClubs(){
		if(ClubList.isEmpty()) return;
		ClubList.forEach(Club::save);
	}

}
