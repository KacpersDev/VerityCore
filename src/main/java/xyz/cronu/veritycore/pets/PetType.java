package xyz.cronu.veritycore.pets;

import xyz.cronu.veritycore.utilities.Math;
import xyz.cronu.veritycore.utilities.Text;

import java.util.Arrays;
import java.util.List;

public enum  PetType {

	MONEY("&a&lMONEY PET",
			Arrays.asList(Text.color("&7A &aMoney Pet &7will increase the amount"),
					Text.color("&7of money you get from each autosell.")),
			1, 750),
	TOKEN("&e&lTOKEN PET",
			Arrays.asList(Text.color("&7A &eToken Pet &7will increase the amount"),
					Text.color("&7of tokens you get from Tokenator.")),
			1, 750),
	PRESTIGE("&c&lPRESTIGE PET",
			Arrays.asList(Text.color("&7A &cPrestige Pet &7will multiply"),
					 Text.color("&7your prestiges when prestiging.")),
			1, 750),
	BLOCK("&b&lBLOCK PET",
			 Arrays.asList(Text.color("&7A &bBlock Pet &7will multiply"),
					 Text.color("&7your blocks when mining.")),
			1, 750);

	private String displayName;
	private List<String> lore;
	private double min;
	private double max;

	PetType(String displayName, List<String> lore, double min, double max){
		this.displayName = displayName;
		this.lore = lore;
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public List<String> getLore() {
		return lore;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static PetType random(){
		List<PetType> types = Arrays.asList(PetType.values());
		return types.get(Math.random(0, types.size() - 1));
	}

}
