package xyz.cronu.veritycore.clubs;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.cronu.veritycore.utilities.ConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Club {

	private static ConfigManager cm = ConfigManager.getInstance();
	private String name;
	private UUID owner;
	private String ownerName;
	private double level;
	private double blocksMined;
	private HashMap<UUID, String> members;

	public Club(String name, UUID owner, String ownerName, double level, double blocksMined, HashMap<UUID, String> members) {
		this.name = name;
		this.owner = owner;
		this.ownerName = ownerName;
		this.level = level;
		this.blocksMined = blocksMined;
		this.members = members;
	}

	public void save() {
		FileConfiguration config = cm.getConfig( "clubs.yml");
		cm.setData(config, name +".owner_uuid", owner.toString());
		cm.setData(config, name + ".owner_name", ownerName);
		cm.setData(config, name + ".level", level);
		cm.setData(config, name + ".blocks_mined", blocksMined);

		if(!members.isEmpty()) {
			List<String> memberList = new ArrayList<>();
			for (UUID member : members.keySet()) {
				memberList.add(member.toString() + ";" + members.get(member));
			}
			cm.setData(config, name + ".members", memberList);
		} else {
			cm.setData(config, name + ".members", "");
		}
	}

	public static void loadGangs() {
		FileConfiguration config = cm.getConfig("clubs.yml");
		for (String clubName : config.getKeys(false)) {

			HashMap<UUID, String> memberList = new HashMap<>();

			for (String b : config.getStringList(clubName + ".members")) {
				String[] text = b.split(";");
				UUID memberUUID = UUID.fromString(text[0]);
				String rank = text[1];
				memberList.put(memberUUID, rank);
			}

			Club club = new Club(clubName,
					UUID.fromString(cm.getStringRaw(config, clubName + ".owner_uuid")),
					cm.getStringRaw(config, clubName + ".owner_name"),
					cm.getDouble(config, clubName + ".level"),
					cm.getDouble(config, clubName + ".blocks_mined"),
					memberList);

			ClubManager.ClubList.add(club);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	public double getBlocksMined() {
		return blocksMined;
	}

	public void setBlocksMined(double blocksMined) {
		this.blocksMined = blocksMined;
	}

	public HashMap<UUID, String> getMembers() {
		return members;
	}

	public void setMembers(HashMap<UUID, String> members) {
		this.members = members;
	}
}
