package ru.school21.swingy.model;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import ru.school21.swingy.type.ArmorType;
import ru.school21.swingy.type.HelmType;
import ru.school21.swingy.type.VillainType;
import ru.school21.swingy.type.WeaponType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "villain")
public class Villain extends AbstractModel {

	@Column(nullable = false, updatable = false, length = VillainType.LENGTH)
	@Enumerated(EnumType.STRING)
	private VillainType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id", nullable = false, updatable = false)
	private Game game;

	@Column(nullable = false)
	private Integer posX;

	@Column(nullable = false)
	private Integer posY;

	@Column(nullable = false)
	private Boolean defeated;

	@Nullable
	@Column(length = WeaponType.LENGTH)
	@Enumerated(EnumType.STRING)
	private WeaponType weapon;

	@Nullable
	@Column(length = ArmorType.LENGTH)
	@Enumerated(EnumType.STRING)
	private ArmorType armor;

	@Nullable
	@Column(length = HelmType.LENGTH)
	@Enumerated(EnumType.STRING)
	private HelmType helm;
}
