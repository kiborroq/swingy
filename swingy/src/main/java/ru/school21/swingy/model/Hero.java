package ru.school21.swingy.model;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ru.school21.swingy.type.ArmorType;
import ru.school21.swingy.type.HelmType;
import ru.school21.swingy.type.HeroType;
import ru.school21.swingy.type.WeaponType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "hero")
public class Hero extends AbstractModel {

	public final static int NAME_LENGTH = 25;

	@NotNull(message = "Name is not specified")
	@Range(min = 1, max = NAME_LENGTH, message = "Name length should be 1-25")
	@Column(nullable = false, updatable = false, length = NAME_LENGTH)
	private String name;

	@NotNull(message = "Type is not specified")
	@Column(nullable = false, updatable = false, length = HeroType.LENGTH)
	@Enumerated(EnumType.STRING)
	private HeroType type;

	@Column(nullable = false)
	private Integer level;

	@Column(nullable = false)
	private Integer experience;

	@Column(nullable = false)
	private Integer attack;

	@Column(nullable = false)
	private Integer defense;

	@Column(nullable = false)
	private Integer hp;

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

	@Nullable
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "game_id")
	private Game game;
}
