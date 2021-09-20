package xyz.cronu.veritycore.users;

import java.util.UUID;

public class OfflinePrisonPlayer extends PrisonPlayer{
	public OfflinePrisonPlayer(UUID uuid, double tokens, double money, long blocksMined, long prestige) {
		super(uuid, tokens, money, blocksMined, prestige);
	}
}
