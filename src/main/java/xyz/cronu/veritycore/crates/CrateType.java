package xyz.cronu.veritycore.crates;

public enum CrateType {

	SHADOW("&d&lSHADOW &7Crate"),
	RADIANT("&f&lRADIANT &7Crate"),
	STELLAR("&e&lSTELLAR &7Crate"),
	CRYSTAL("&b&lCRYSTAL &7Crate");


	private String displayName;

	CrateType(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}


}
