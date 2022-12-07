package ru.school21.swingy.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "villain")
public class Villain extends AbstractCharacter {

	@Column(nullable = false)
	private Integer posX;

	@Column(nullable = false)
	private Integer posY;

	@Column(nullable = false)
	private Boolean defeated = Boolean.FALSE;

	@Column(length = COMMON_LENGTH)
	private String artefact;

}
