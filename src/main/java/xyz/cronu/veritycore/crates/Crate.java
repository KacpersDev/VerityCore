package xyz.cronu.veritycore.crates;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.cronu.veritycore.utilities.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class Crate {

//	Template for Crates
//	shadow:
//	crate_rewards:
//	reward_1:
//	name: "Awesome Reward"
//	material: "STONE"
//	commands:
//			- "test-1"
//			- "test-2"
//	reward_2:
//	name: "Awesome Reward 2"
//	material: "EMERALD_ORE"
//	commands:
//			- "test-3"
//			- "test-4"
//			- "test-5"

	private static ConfigManager cm = ConfigManager.getInstance();
	private CrateType crateType;
	private List<CrateReward> crateRewards;

	public Crate(CrateType crateType, List<CrateReward> crateRewards) {
		this.crateType = crateType;
		this.crateRewards = crateRewards;
	}

	public static void loadCrates() {
		FileConfiguration config = cm.getConfig("crates.yml");

		for (String crate : config.getKeys(false)) { // Looping through crate types
			List<CrateReward> crateRewards = new ArrayList<>();

			ConfigurationSection crateType = config.getConfigurationSection(crate);
			if (crateType == null) return;
			ConfigurationSection rewards = crateType.getConfigurationSection("crate_rewards");
			if (rewards == null) return;

			for (String reward : rewards.getKeys(false)) { // Looping through rewards

				ConfigurationSection r = rewards.getConfigurationSection(reward);
				List<String> commandsList = new ArrayList<>(r.getStringList("commands")); // Command list

				CrateReward crateReward = new CrateReward(commandsList,
						Material.valueOf(r.getString("material")),
						r.getString("name"),
						(float) r.getDouble("chance"),
						r.getString("reward_message"));

				crateRewards.add(crateReward);

			}
			CrateManager.crates.add(new Crate(CrateType.valueOf(crate.toUpperCase()), crateRewards));
		}
	}

	public CrateType getCrateType() {
		return crateType;
	}

	public void setCrateType(CrateType crateType) {
		this.crateType = crateType;
	}

	public List<CrateReward> getCrateRewards() {
		return crateRewards;
	}

	public void setCrateRewards(List<CrateReward> crateRewards) {
		this.crateRewards = crateRewards;
	}
}
