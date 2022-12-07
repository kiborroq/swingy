package ru.school21.swingy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArtefactType {
	HELM("Hit Points"),
	ARMOR("Defense"),
	WEAPON("Attack");

	private final String skill;

}
