package ru.school21.swingy.model.entity;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hero")
public class Hero extends AbstractCharacter {

	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime lastUseDate = LocalDateTime.now();

	@NotNull(message = "Class is not specified")
	@Column(nullable = false, updatable = false, length = COMMON_LENGTH)
	private String clazz;

	@NotNull(message = "Defense is not specified")
	@Column(nullable = false)
	private Integer defense;

	@NotNull(message = "Max hit points value is not specified")
	@Column(nullable = false)
	private Integer maxHp;

	@Nullable
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "game_map_id")
	private GameMap gameMap;

	@Nullable
	private String weapon;

	@Nullable
	private String armor;

	@Nullable
	private String helm;

}
