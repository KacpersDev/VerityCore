package xyz.cronu.veritycore.pets;

import xyz.cronu.veritycore.utilities.Math;

import java.util.Arrays;
import java.util.List;

public enum PetTier {

	COMMON, RARE, LEGENDARY, STELLAR, CRYSTAL;

	public static PetTier random(){
		List<PetTier> types = Arrays.asList(PetTier.values());
		return types.get(Math.random(0, types.size() - 1));
	}

}
