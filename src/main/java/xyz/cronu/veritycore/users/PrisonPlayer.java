package xyz.cronu.veritycore.users;

import java.util.UUID;

public class PrisonPlayer {
	private UUID uuid;
	private double tokens;
	private double money;
	private long blocksMined;
	private long prestige;

	public PrisonPlayer(UUID uuid, double tokens, double money, long blocksMined, long prestige) {
		this.uuid = uuid;
		this.tokens = tokens;
		this.money = money;
		this.blocksMined = blocksMined;
		this.prestige = prestige;
	}

	public void takePrestige(long amount){
		if(getPrestige() - amount < 0) amount = getPrestige();
		setPrestige(getPrestige() - amount);
	}

	public void takeMoney(double amount){
		if(getMoney() - amount < 0) amount = getMoney();
		setMoney(getMoney() - amount);
	}

	public void takeTokens(double amount){
		if(getTokens() - amount < 0) amount = getTokens();
		setTokens(getTokens() - amount);
	}

	public void takeBlocksMined(long amount){
		if(getBlocksMined() - amount < 0) amount = getBlocksMined();
		setBlocksMined(getBlocksMined() - amount);
	}

	public void addPrestige(long amount){
		setPrestige(getPrestige() + amount);
	}

	public void addMoney(double amount){
		setMoney(getMoney() + amount);
	}

	public void addTokens(double amount){
		setTokens(getTokens() + amount);
	}

	public void addBlocksMined(long amount){
		setBlocksMined(getBlocksMined() + amount);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public double getTokens() {
		return tokens;
	}

	public void setTokens(double tokens) {
		this.tokens = tokens;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public long getBlocksMined() {
		return blocksMined;
	}

	public void setBlocksMined(long blocksMined) {
		this.blocksMined = blocksMined;
	}

	public long getPrestige() {
		return prestige;
	}

	public void setPrestige(long prestige) {
		this.prestige = prestige;
	}


}
