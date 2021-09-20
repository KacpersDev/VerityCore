package xyz.cronu.veritycore.leaderboards;

import java.util.Map;

public class Leaderboard {

	private Map<String, Double> prestigeTop;
	private Map<String, Double> blockTop;
	private Map<String, Double> tokenTop;
	private Map<String, Double> moneyTop;

	public Leaderboard(Map<String, Double> prestigeTop, Map<String, Double> blockTop, Map<String, Double> tokenTop, Map<String, Double> moneyTop) {
		this.prestigeTop = prestigeTop;
		this.blockTop = blockTop;
		this.tokenTop = tokenTop;
		this.moneyTop = moneyTop;
	}

	public Map<String, Double> getPrestigeTop() {
		return prestigeTop;
	}

	public void setPrestigeTop(Map<String, Double> prestigeTop) {
		this.prestigeTop = prestigeTop;
	}

	public Map<String, Double> getBlockTop() {
		return blockTop;
	}

	public void setBlockTop(Map<String, Double> blockTop) {
		this.blockTop = blockTop;
	}

	public Map<String, Double> getTokenTop() {
		return tokenTop;
	}

	public void setTokenTop(Map<String, Double> tokenTop) {
		this.tokenTop = tokenTop;
	}

	public Map<String, Double> getMoneyTop() {
		return moneyTop;
	}

	public void setMoneyTop(Map<String, Double> moneyTop) {
		this.moneyTop = moneyTop;
	}
}
