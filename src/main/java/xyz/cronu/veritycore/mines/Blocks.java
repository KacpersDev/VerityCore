package xyz.cronu.veritycore.mines;

public enum Blocks {

	STONE(10D, 100D),
	COAL_ORE(20D, 200D),
	COAL_BLOCK(30D, 300D),
	IRON_ORE(40D, 400D),
	IRON_BLOCK(50D, 500D),
	GOLD_ORE(60D, 600D),
	GOLD_BLOCK(70D, 700D),
	LAPIS_ORE(80D, 800D),
	LAPIS_BLOCK(90D, 900D),
	REDSTONE_ORE(100D, 1000D),
	REDSTONE_BLOCK(110D, 1100D),
	DIAMOND_ORE(120D, 1200D),
	DIAMOND_BLOCK(130D, 1300D),
	EMERALD_ORE(140D, 1400D),
	EMERALD_BLOCK(150D, 1500D),
	NETHERRACK(160D, 1600D),
	SANDSTONE(170D, 1700D),
	BRICKS(180D, 1800D),
	STONE_BRICKS(190D, 1900D),
	END_STONE(200D, 2000D),
	OBSIDIAN(210D, 2200D);

	private final double price;
	private final double position;

	Blocks(double price, double position){
		this.price = price;
		this.position = position;
	}

	public double getPosition() {
		return position;
	}

	public double getPrice() {
		return price;
	}
}
