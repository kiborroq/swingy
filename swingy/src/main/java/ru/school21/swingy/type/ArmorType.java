package ru.school21.swingy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmorType {
	BREASTPLATE_OF_WOOD("Breastplate of Petrified Wood", "/images/armors/Breastplate_of_Petrified_Wood.gif", 1),
	RIB_CAGE("Rib Cage", "/images/armors/Rib_Cage.gif", 2),
	SCALE_OF_BASILISK("Scales of the Greater Basilisk", "/images/armors/Scales_of_the_Greater_Basilisk.gif", 3),
	TUNIC_OF_CYCLOP("Tunic of the Cyclops King", "/images/armors/Tunic_of_the_Cyclops_King.gif", 4),
	BREASTPLATE_OF_BRIMSTONE("Breastplate of Brimstone", "/images/armors/Breastplate_of_Brimstone.gif", 5),
	ARMOR_OF_WONDER("Armor of Wonder", "/images/armors/Armor_of_Wonder.gif", 6),
	DRAGON_SCALE("Dragon Scale Armor", "/images/armors/Dragon_Scale_Armor.gif", 7),
	TITAN_CUIRASS("Titan's Cuirass", "/images/armors/Titan's_Cuirass.gif", 8),
	POWER_OF_DRAGON("Power of the Dragon Father", "/images/armors/Power_of_the_Dragon_Father.gif", 9);

	private final String name;
	private final String image;
	private final Integer defenseIncrease;

	public final static int LENGTH = 25;
}
