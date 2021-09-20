package xyz.cronu.veritycore.mines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.cronu.veritycore.Prefix;
import xyz.cronu.veritycore.utilities.ConfigManager;
import xyz.cronu.veritycore.utilities.Cuboid;
import xyz.cronu.veritycore.utilities.LocationUtils;
import xyz.cronu.veritycore.utilities.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Mine {

	private static ConfigManager configManager = ConfigManager.getInstance();
	private static int lastLocation;
	private static double blocksMined;
	private UUID owner;
	private String name;
	private Cuboid region;
	private Location spawn;
	private List<UUID> accessors;
	private int level;
	private String block;
	private double blocksWorth;
	private double credits;

	public Mine(){}

	public Mine(UUID owner, String name, Cuboid region, Location spawn, List<UUID> accessors, int level, String block, double blocksWorth, double credits) {
		this.owner = owner;
		this.name = name;
		this.region = region;
		this.spawn = spawn;
		this.accessors = accessors;
		this.level = level;
		this.block = block;
		this.blocksWorth = blocksWorth;
		this.credits = credits;
	}

	public static void load(){
		try{
			setLastLocation(configManager.getConfig("mines.yml").getInt("lastlocation"));
			ConfigurationSection config = configManager.getConfig("mines.yml").getConfigurationSection("privatemines");
			if(config != null){
				config.getKeys(false).forEach(mine -> {
					Location point1 = LocationUtils.fromString(config.getString(mine + ".region.pos1")); assert point1 != null;
					Location point2 = LocationUtils.fromString(config.getString(mine + ".region.pos2")); assert point2 != null;
					List<UUID> accessors = new ArrayList<>();
					if(config.getStringList(mine + ".access").size() > 0){
						config.getStringList(mine + ".acesss").forEach(user -> accessors.add(UUID.fromString(user)));
					}
					Mine privateMine = new Mine(
							UUID.fromString(config.getString(mine + ".owner")),
							mine,
							new Cuboid(point1, point2),
							LocationUtils.fromString(config.getString(mine + ".spawn")),
							accessors,
							config.getInt(mine + ".level"),
							config.getString(mine + ".block"),
							config.getDouble(mine + ".blocksworth"),
							config.getDouble(mine + ".credits"));
					setBlocksMined(config.getDouble(mine + ".blocksmined"));
					MineManager.mines.add(privateMine);
				});
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void save(){
		try {
			FileConfiguration config = configManager.getConfig("mines.yml");
			configManager.setData(config,"lastlocation", this.lastLocation);
			configManager.setData(config,"privatemines." + this.getName() + ".owner", this.owner.toString());
			configManager.setData(config,"privatemines." + this.getName() + ".spawn", LocationUtils.toString(this.spawn));
			configManager.setData(config,"privatemines." + this.getName() + ".region.pos1", LocationUtils.toString(this.region.getPoint1()));
			configManager.setData(config,"privatemines." + this.getName() + ".region.pos2", LocationUtils.toString(this.region.getPoint2()));
			List<UUID> accessors = new ArrayList<>(this.getAccessors());
			configManager.setData(config,"privatemines." + this.getName() + ".access", accessors);
			configManager.setData(config,"privatemines." + this.getName() + ".level", this.level);
			configManager.setData(config,"privatemines." + this.getName() + ".blocksmined", blocksMined);
			configManager.setData(config,"privatemines." + this.getName() + ".block", block);
			configManager.setData(config,"privatemines." + this.getName() + ".blocksworth", blocksWorth);
			configManager.setData(config,"privatemines." + this.getName() + ".credits", credits);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public double worthUpgradeCost(){
		return (blocksWorth / 10000);
	}

	public double getCredits() {
		return credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	public double getBlocksWorth() {
		return blocksWorth;
	}

	public void setBlocksWorth(double blocksWorth) {
		this.blocksWorth = blocksWorth;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public void giveAccess(UUID user){
		List<UUID> access = getAccessors();
		access.add(user);
		setAccessors(access);
	}

	public void setAccessors(List<UUID> accessors) {
		this.accessors = accessors;
	}

	public void addLevel(int amount){
		int level = getLevel();
		setLevel(level + amount);
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double levelUpCost(){
		return Math.pow(level, 2) * 1000D;
	}

	public void addBlocksMined(double amount){
		double blocks = getBlocksMined();
		if(blocks + amount >= levelUpCost()){
			setLevel(getLevel() + 1);
			setCredits(getCredits() + 5);
			Text.message(Bukkit.getPlayer(owner), Prefix.MINE + "&7Your Mine Has Leveled Up!");
		}
		setBlocksMined(blocks + amount);
	}

	public double getBlocksMined() {
		return blocksMined;
	}

	public static void setBlocksMined(double blocksMined) {
		Mine.blocksMined = blocksMined;
	}

	public Location getSpawn() {
		return spawn;
	}

	public int getLastLocation() {
		return lastLocation;
	}

	public static void setLastLocation(int location){
		lastLocation = location;
	}

	public String getName() {
		return name;
	}

	public UUID getOwner() {
		return owner;
	}

	public Cuboid getRegion() {
		return region;
	}

	public List<UUID> getAccessors() {
		return accessors;
	}

	public int getLevel() {
		return level;
	}

	public double emptyBlocks(){
		ArrayList<Block> blocks = new ArrayList<>();
		final Iterator<Block> iterator = getRegion().blockList();
		while (iterator.hasNext()) {
			final Block block = iterator.next();
			if(block.getType() == Material.AIR) continue;
			blocks.add(block);
		}
		return blocks.size();
	}

	public double fullBlocks(){
		ArrayList<Block> blocks = new ArrayList<>();
		final Iterator<Block> iterator = getRegion().blockList();
		while (iterator.hasNext()) {
			final Block block = iterator.next();
			blocks.add(block);
		}
		return blocks.size();
	}

}
