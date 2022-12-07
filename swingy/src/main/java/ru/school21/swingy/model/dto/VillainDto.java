package ru.school21.swingy.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VillainDto {

	String name;
	Integer level;
	Integer experience;
	Integer attack;
	Integer hp;

}
