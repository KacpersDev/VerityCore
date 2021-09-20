package xyz.cronu.veritycore.crates;

import org.bukkit.Material;

import java.util.List;

public class CrateReward {

	private List<String> rewardCommands;
	private Material rewardMaterial;
	private String rewardName;
	private float chance;
	private String rewardMessage;

	public CrateReward(List<String> rewardCommands, Material rewardMaterial, String rewardName, float chance, String rewardMessage) {
		this.rewardCommands = rewardCommands;
		this.rewardMaterial = rewardMaterial;
		this.rewardName = rewardName;
		this.chance = chance;
		this.rewardMessage = rewardMessage;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}

	public void setRewardMessage(String rewardMessage) {
		this.rewardMessage = rewardMessage;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}

	public List<String> getRewardCommands() {
		return rewardCommands;
	}

	public void setRewardCommands(List<String> rewardCommands) {
		this.rewardCommands = rewardCommands;
	}

	public Material getRewardMaterial() {
		return rewardMaterial;
	}

	public void setRewardMaterial(Material rewardMaterial) {
		this.rewardMaterial = rewardMaterial;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
}
