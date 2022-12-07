package ru.school21.swingy.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HeroStatsDto {

	String name;
	String clazz;
	Integer level;
	Integer experience;
	Integer targetExperience;
	Integer attack;
	Integer defense;
	Integer hp;
	Integer maxHp;
	ArtefactDto helm;
	ArtefactDto weapon;
	ArtefactDto armor;

}
