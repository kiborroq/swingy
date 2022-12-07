package ru.school21.swingy.model.dto;

import lombok.Builder;
import lombok.Value;
import ru.school21.swingy.type.ArtefactType;

import javax.swing.ImageIcon;

@Value
@Builder
public class ArtefactDto {

	String name;
	Integer increase;
	ArtefactType type;

}
