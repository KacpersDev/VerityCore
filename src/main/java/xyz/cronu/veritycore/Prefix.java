package xyz.cronu.veritycore;

import xyz.cronu.veritycore.utilities.Text;

public enum Prefix {

	SERVER(Text.color("&8【&4&lVERITY&8】")),
	AUTOSELL(Text.color("&8【&4&lAUTOSELL&8】")),
	PRESTIGE(Text.color("&8【&4&lPRESTIGE&8】")),
	MINE(Text.color("&8【&4&lMINE&8】")),
	PICKAXE(Text.color("&8【&4&lPICKAXE&8】")),
	PETS(Text.color("&8【&4&lPETS&8】")),
	CRATES(Text.color("&8【&4&lCRATES&8】")),
	GANG(Text.color("&8【&4&lGANGS&8】"));

	private String prefix;

	Prefix(String prefix){
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}
}
