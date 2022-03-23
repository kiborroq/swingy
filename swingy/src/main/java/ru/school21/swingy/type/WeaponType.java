package ru.school21.swingy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeaponType {
	CENTAUR_AXE("Centaur's Axe", "/images/weapon/Centaur's_Axe.gif", 1),
	BLACKSHARD_OF_DEAD("Blackshard_of_the_Dead_Knight.gif", "/images/weapon/Rib_Cage.gif", 2),
	GNOLL_FLAIL("Greater Gnoll's Flail", "/images/weapon/Greater_Gnoll's_Flail.gif", 3),
	ORG_CLUB("Ogre's Club of Havoc", "/images/weapon/Ogre's_Club_of_Havoc.gif", 4),
	DRAGON_FLAME("Red Dragon Flame Tongue", "/images/weapon/Red_Dragon_Flame_Tongue.gif", 5),
	TITAN_GLADIUS("Titan's Gladius", "/images/weapon/Titan's_Gladius.gif", 6),
	JUDGEMENT_SWORD("Sword of Judgement", "/images/weapon/Sword_of_Judgement.gif", 7),
	TITAN_THUNDER("Titan's Thunder", "/images/weapon/Titan's_Thunder.gif", 8),
	ANGELIC_ALLIANCE("Angelic Alliance", "/images/weapon/Angelic_Alliance.gif", 9);

	private final String name;
	private final String image;
	private final Integer attackIncrease;

	public final static int LENGTH = 25;
}
